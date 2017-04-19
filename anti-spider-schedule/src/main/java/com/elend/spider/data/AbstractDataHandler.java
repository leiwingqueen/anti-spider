package com.elend.spider.data;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.elend.log.vo.AccessLogVO;
import com.elend.p2p.Result;
import com.elend.p2p.annotation.Trace;
import com.elend.p2p.annotation.TracePolicy;
import com.elend.p2p.constant.ResultCode;
import com.elend.p2p.util.DateUtil;
import com.elend.p2p.util.redis.RedisDao;
import com.elend.spider.event.AbstractEvent;
import com.elend.spider.event.Event;
import com.elend.spider.event.EventSubscribeConfig;

/**
 * 日志处理虚基类
 * @author liyongquan 2016年8月9日
 *
 */
public abstract class AbstractDataHandler implements DataHandler{
    private final static Logger logger = LoggerFactory.getLogger(AbstractDataHandler.class);
    @Autowired
    @Qualifier("redisManager")
    protected RedisDao redisDao;
    
    @Autowired
    protected EventSubscribeConfig config;
    
    /**
     * 
     */
    /**
     * 数据处理
     * @param logList
     * access_log访问日志
     */
    @Override
    @Trace
    public void process(List<AccessLogVO> logList){
        /**
         * 1.日志数据校验
         */
        if(logList==null||logList.size()==0){
            logger.info("logList is empty...");
            return;
        }
        if(logger.isDebugEnabled()){
            logger.info("数据处理模块循环开始...dataHandler:{},logList size:{}",this.getClass().getName(),logList.size());
        }
        Date start=new Date();
        /**
         * 保存最后日志时间缓存,不需要每次从redis中获取，每次循环5K条数据，这里初始化500个IP
         */
        Map<String,Date> lastLogTimeMap=new HashMap<String,Date>(500);
        /**
         * 3.日志数据处理
         */
        for(AccessLogVO log:logList){
            //获取该ip的最后的日志时间
            Date lastLogTime=lastLogTimeMap.containsKey(log.getIp())?lastLogTimeMap.get(log.getIp()):
                DateUtil.strToTime(redisDao.get(getRedisKey(getTimeType(), log.getIp(), RecordType.LAST_LOG_TIME)),DateUtil.DATE_FORMAT_PATTEN);
            
            Date newLastLogTime=getCurrentProxy().process(log,lastLogTime);
            //更新日志最后处理时间
            if(lastLogTime==null||lastLogTime.getTime()!=newLastLogTime.getTime()){
                lastLogTimeMap.put(log.getIp(), newLastLogTime);
                updateLastLogTime(log.getIp(),newLastLogTime);
            }
        }
        //每次循环结束后需要更新最后处理时间
        //updateLastLogTime(DateUtil.timeToStr(logList.get(logList.size()-1).getCreateTime(), DateUtil.DATE_FORMAT_PATTEN));
        if(logger.isDebugEnabled()){
            logger.info("数据处理模块循环结束...dataHandler:{},logList size:{},耗时:{}ms",this.getClass().getName(),logList.size(),new Date().getTime()-start.getTime());
        }
    }
    
    @Override
    @Trace(propagation=TracePolicy.REQUIRES_NEW)
    public Date process(AccessLogVO log,Date lastLogTime){
        logger.debug("accessLog日志数据处理...logId:{},logTime:{},lastLogTime:{},dataHandler:{},ip:{}",
                    log.getId(),log.getCreateTime(),lastLogTime,this.getClass().getName(),log.getIp());
        /**
         * 日志已经移动到下一个时间点，触发事件
         */
        Date newLastLogTime=lastLogTime;
        if(moveToNext(lastLogTime, log.getCreateTime(), getTimeType())){
            //生成事件
            Event event=generateEvent(log.getIp(),lastLogTime);
            logger.info("事件触发...事件类型:{},logId:{},logTime:{},event detail:{}, ip:{}",
                        event.getClass().getName(),log.getId(),log.getCreateTime(),event.toString(), log.getIp());
            //事件通知
            try {
                event.notifyListner();
            } catch (Exception e) {
                logger.error("事件通知异常...event:"+event.toString(),e);
            }
            //删除redis数据
            clearRedisData(log.getIp(),lastLogTime);
            //更新最后的日志时间
            newLastLogTime=log.getCreateTime();
        }
        //数据处理，记录到redis
        Result<String> handleResult=this.dataHandle(log);
        if(!handleResult.isSuccess()){
            logger.error("数据处理失败...ip:{},logId:{},原因:{}",log.getIp(),log.getId(),handleResult.getMessage());
        }
        //最后日志时间不需要更新
        return newLastLogTime==null?log.getCreateTime():newLastLogTime;
    }
   /**
    * 更新处理的最后日志时间
    */
    private void updateLastLogTime(String ip,Date lastLogTime){
        if(logger.isDebugEnabled()){
            logger.info("更新最后的日志时间...lastLogTime:{},ip:{}",lastLogTime,ip);
        }
        redisDao.set(getRedisKey(getTimeType(), ip, RecordType.LAST_LOG_TIME), DateUtil.timeToStr(lastLogTime,DateUtil.DATE_FORMAT_PATTEN));
    }
    
    /**
     * 清除redis的数据
     * @param ip
     * 访问ip
     * @param lastLogTime
     * 最后一条日志的时间(产生事件的log的时间)
     */
    protected void clearRedisData(String ip,Date lastLogTime) {
        for(RecordType type:RecordType.values()){
            redisDao.delKeys(getRedisKey(getTimeType(), ip, type));
        }
        if(logger.isDebugEnabled()){
            logger.info("删除redis数据...ip:{},timeType:{}",ip,getTimeType());
        }
    }
    
    /**
     * 获取当前实例的代理类,没有直接用this，日志跟踪需要
     * @return
     */
    protected abstract DataHandler getCurrentProxy();    
    /**
     * 是否需要到下一个时间点进行数据处理
     * 数据处理以分钟/小时/天为维度进行划分
     * @param lastLogTime
     * 最后的日志处理时间
     * @param currentLogTime
     * 当前日志时间
     * @param timeType
     * 时间类型
     * @return
     */
    public boolean moveToNext(Date lastLogTime,Date currentLogTime,TimeType timeType) {
        //该IP第一次访问的情况
        if(lastLogTime==null)return false;
        Calendar cal1=Calendar.getInstance();
        cal1.setTime(lastLogTime);
        Calendar cal2=Calendar.getInstance();
        cal2.setTime(currentLogTime);
        if(cal1.get(Calendar.YEAR)!=cal2.get(Calendar.YEAR)||
                cal1.get(Calendar.MONTH)!=cal2.get(Calendar.MONTH))return true;
        if(TimeType.MINUTE==timeType){
            if(cal1.get(Calendar.DATE)!=cal2.get(Calendar.DATE)||
                    cal1.get(Calendar.HOUR)!=cal2.get(Calendar.HOUR)||
                    cal1.get(Calendar.MINUTE)!=cal2.get(Calendar.MINUTE))
                return true;
        }else if(TimeType.HOUR==timeType){
            if(cal1.get(Calendar.DATE)!=cal2.get(Calendar.DATE)||
                    cal1.get(Calendar.HOUR)!=cal2.get(Calendar.HOUR))
                return true;
        }else if(TimeType.DAY==timeType){
            if(cal1.get(Calendar.DATE)!=cal2.get(Calendar.DATE))
                return true;
        }else{
            logger.error("时间类型错误...timeType:{}",timeType);
            return true;
        }
        return false;
    }
    
    /**
     * 生成事件
     * @return
     */
    public Event generateEvent(String ip,Date lastLogTime){
        String accessNumStr = redisDao.get(getRedisKey(getTimeType(), ip,
                                                       RecordType.VISIT_NUM));
        long accessNum = StringUtils.isBlank(accessNumStr) ? 0L
            : Long.parseLong(accessNumStr);
        Long urlNum = redisDao.scard(getRedisKey(getTimeType(), ip,
                                                 RecordType.URI_SET));
        Long userNum = redisDao.scard(getRedisKey(getTimeType(), ip,
                                                  RecordType.USER_SET));
        Map<String,String> uaMap=redisDao.hgetAll(getRedisKey(getTimeType(), ip,
                                     RecordType.USER_AGENT_MAP));
        Map<String,Long> userAgentMap=new HashMap<String,Long>(uaMap==null?16:uaMap.keySet().size());
        if(uaMap!=null&&uaMap.keySet().size()>0){
            for(String ua:uaMap.keySet()){
                userAgentMap.put(ua, Long.parseLong(uaMap.get(ua)));
            }
        }
        Event event = new AbstractEvent(config, ip, accessNum,
                                            urlNum == null ? 0L : urlNum,
                                            userNum == null ? 0L : userNum,userAgentMap,lastLogTime);
        return event;
    }
    
    /**
     * 数据处理
     * @param log
     * 日志
     * @return
     * 操作是否成功
     */
    public Result<String> dataHandle(AccessLogVO log) {
        Long accessNum = redisDao.incrBy(getRedisKey(getTimeType(),
                                                     log.getIp(),
                                                     RecordType.VISIT_NUM),
                                         1);
        Long uriNum = redisDao.sadd(getRedisKey(getTimeType(), log.getIp(),
                                                RecordType.URI_SET),
                                    classifyUri(log.getUri()));
        Long userNum = redisDao.sadd(getRedisKey(getTimeType(), log.getIp(),
                                                 RecordType.USER_SET),
                                     log.getUserId() + "");
        Long uaNum = redisDao.hincrBy(getRedisKey(getTimeType(), log.getIp(),
                                                  RecordType.USER_AGENT_MAP),
                                      log.getUserAgent(), 1);
        logger.debug("log:{},ip:{}更新redis数据完成...accessNum:{},uri数量是否增加:{},用户数量是否增加:{},uaNum:{}",log.getId(),log.getIp(),accessNum,0L<uriNum,0L<userNum,uaNum);
        return new Result<String>(ResultCode.SUCCESS, null);
    }
    
    /**
     * 用户uri分类,我们认为去掉数字后的uri如果相同的话则为同一类的uri
     * 例：/loan/loanHandler/1608121018560956和/loan/loanHandler/1608121018560213为同一种资源，因为去掉数字uri相同
     * @param url
     * @return
     */
    protected String classifyUri(String url) {
        return url.replaceAll("[0-9]", "");
    }
    
    /**
     * 返回处理类的时间类型
     * @return
     */
    protected abstract TimeType getTimeType();
        
    
    /**
     * redis的key的格式为 
     * 时间类型(分钟/小时/天)_ip_记录类型
     * @param timeType
     * 时间类型
     * @param ip
     * ip
     * @param recordType
     * 记录类型
     * @return
     * redis对应的key
     */
    protected String getRedisKey(TimeType timeType,String ip,RecordType recordType) {
        return timeType.name()+"_"+ip+"_"+recordType.name();
    }
    
    
    /**
     * 时间类型
     * @author liyongquan 2016年8月9日
     *
     */
    public enum TimeType{
        /**
         * 分钟
         */
        MINUTE,
        /**
         * 小时
         */
        HOUR,
        /**
         * 天
         */
        DAY;
    }
    /**
     * 记录类型
     * @author liyongquan 2016年8月9日
     *
     */
    public enum RecordType{
        /**
         * 访问次数
         */
        VISIT_NUM,
        /**
         * URI的集合
         */
        URI_SET,
        /**
         * 访问用户集合
         */
        USER_SET,
        /**
         * 访问次数方差
         */
        ACCESS_NUM_VARIANCE,
        /**
         * UA的访问次数
         */
        USER_AGENT_MAP,
        /**
         * 上一次访问的日志时间（但这里做一些优化，只有事件触发才会更新这个时间，不需要频繁更新这个时间）
         */
        LAST_LOG_TIME;
    }
}
