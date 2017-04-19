package com.elend.spider.data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.elend.p2p.Result;
import com.elend.p2p.constant.ResultCode;
import com.elend.p2p.spring.SpringContextUtil;
import com.elend.p2p.util.DateUtil;
import com.elend.spider.event.AbstractEvent;
import com.elend.spider.event.Event;
import com.elend.spider.event.HourEvent;
import com.elend.spider.event.Listner;
import com.elend.spider.event.MinuteEvent;

/**
 * 小时数据处理，同时也是Listner，监听着分钟事件
 * @author liyongquan 2016年8月11日
 *
 */
@Component("hourDataHandler")
public class HourDataHandler extends AbstractDataHandler implements Listner{
    private final static Logger logger = LoggerFactory.getLogger(HourDataHandler.class);
    
    /**
     * 时间格式(精确到小时)
     */
    public static final String TIME_FORMAT="yyyy-MM-dd-HH";

    @Override
    protected TimeType getTimeType() {
        return TimeType.HOUR;
    }

    @Override
    public Event generateEvent(String ip,Date lastLogTime) {
        AbstractEvent event=(AbstractEvent)super.generateEvent(ip,lastLogTime);
        //ip每分钟的访问频率数据
        String key=getAccessNumVarienceKey(ip, lastLogTime);
        List<String> accessNumList=redisDao.lrange(key, 0, -1);
        List<Long> accessNumLongList=new ArrayList<Long>(accessNumList.size());
        if(accessNumList!=null&&accessNumList.size()>0){
            for(String num:accessNumList){
                accessNumLongList.add(Long.parseLong(num));
            }
        }
        return new HourEvent(config, event,lastLogTime,accessNumLongList);
    }
    
    /**
     * 计算访问频率方差
     * 时间跨度(1小时)，每条记录为1分钟的访问数据
     * @param accessNumList
     * 访问次数列表
     * @return
     */
    @Deprecated
    public static BigDecimal calAccessNumVarience(List<Long> accessNumList){
        if(accessNumList==null||accessNumList.size()<=0){
            logger.error("访问频率数据为空，计算方差失败...");
            return new BigDecimal("-1");
        }
        if(accessNumList.size()>60){
            logger.error("访问频率数据大于60个，数据异常...size:{}",accessNumList.size());
            accessNumList=accessNumList.subList(0, 60);
        }else if(accessNumList.size()<60){//不足60条的数据补0
            for(int i=0;i<60-accessNumList.size();i++){
                accessNumList.add(0L);
            }
        }
        BigDecimal sum=BigDecimal.ZERO;
        for(long accessNum:accessNumList){
            sum=sum.add(new BigDecimal(accessNum));
        }
        BigDecimal avg=sum.divide(new BigDecimal(60),2, BigDecimal.ROUND_HALF_UP);
        double variance=0D;
        for(long accessNum:accessNumList){
            variance+=Math.pow((new BigDecimal(accessNum).subtract(avg)).doubleValue(),2);
        }
        return new BigDecimal(variance).setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    @Override
    protected DataHandler getCurrentProxy() {
        return (DataHandler)SpringContextUtil.getBean("hourDataHandler");
    }

    /**
     * 监听分钟事件，统计访问频率写入redis，后面用于处理
     */
    @Override
    public Result<String> execute(Event event) {
        if(!(event instanceof MinuteEvent)){
            logger.error("HourDataHandler监听事件类型错误...监听事件应该为MinuteEvent");
            return new Result<String>(ResultCode.FAILURE, null, "HourDataHandler监听事件类型错误...监听事件应该为MinuteEvent");
        }
        MinuteEvent minuteEvent=(MinuteEvent)event;
        String key=getAccessNumVarienceKey(minuteEvent.getIp(), minuteEvent.getLogTime());
        //将访问次数写入redis,redis的list操作
        Long count=redisDao.rpush(key, minuteEvent.getAccessNum()+"");
        logger.info("HourDataHandler监听分钟事件，更新ip:{}的访问次数列表，更新后的数量:{}",minuteEvent.getIp(),count);
        return new Result<String>(ResultCode.SUCCESS);
    }
    
    /**
     * 清理上一个小时的数据
     * 因为需要清理每分钟访问频率的数据，因此这里重载父类的方法
     */
    @Override
    protected void clearRedisData(String ip,Date lastLogTime){
        super.clearRedisData(ip,lastLogTime);
        String lastHour=DateUtil.timeToStr(lastLogTime, TIME_FORMAT);
        logger.info("清理上一个小时产生的访问频率数据...ip:{},lastHour:{}",ip,lastHour);
        redisDao.delKeys(getAccessNumVarienceKey(ip, lastLogTime));
    }
    
    /**
     * 获取访问次数统计的key
     * 记录类型_ip_时间
     * @param ip
     * ip
     * @param logTime
     * 时间，格式:yyyy-MM-dd-HH
     * @return
     */
    private String getAccessNumVarienceKey(String ip,Date logTime){
        return RecordType.ACCESS_NUM_VARIANCE+"_"+ip+"_"+DateUtil.timeToStr(logTime, TIME_FORMAT);
    }

}
