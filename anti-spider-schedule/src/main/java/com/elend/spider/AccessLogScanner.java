package com.elend.spider;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.elend.log.facade.AccessLogFacade;
import com.elend.log.vo.AccessLogVO;
import com.elend.p2p.util.redis.RedisDao;
import com.elend.spider.data.DataHandler;

/**
 * access_log扫描处理类.
 * 负责扫描access_log并将数据交付到DataHandler进行处理
 * @author liyongquan 2016年8月11日
 *
 */
public class AccessLogScanner {
    private final static Logger logger = LoggerFactory.getLogger(AccessLogScanner.class);
    /**
     * 数据处理类
     */
    private List<DataHandler> dataHandlerList;
    /**
     * 处理的最后的log_id,记录到redis
     */
    public final static String LAST_LOG_ID_KEY="LAST_LOG_ID";
    
    @Autowired
    private AccessLogFacade accessLogFacade;
    @Autowired
    private RedisDao redisDao;
    
    /**
     * 扫描一定数量的access_log并交付到DataHandler
     * @param limit
     * 扫描的数量
     * @return
     * 处理的数据数量(<=limit)
     */
    public int scan(int limit){
        String lastLogId=redisDao.get(LAST_LOG_ID_KEY);
        lastLogId=StringUtils.isBlank(lastLogId)?"0":lastLogId;
        if(logger.isDebugEnabled()){
            logger.info("get lastLogId:{}",lastLogId);
        }
        List<AccessLogVO> list=accessLogFacade.listById(Long.parseLong(lastLogId), limit);
        if(list==null||list.size()<=0){
            return 0;
        }
        /**
         * 数据递交给每个数据处理模块进行处理
         */
        for(DataHandler dataHandler:dataHandlerList){
            dataHandler.process(list);
        }
        /**
         * 更新最后日志ID
         */
        redisDao.set(LAST_LOG_ID_KEY, list.get(list.size()-1).getId()+"");
        if(logger.isDebugEnabled()){
            logger.info("update lastLogId:{},原来的lastLogId:{}",list.get(list.size()-1).getId(),lastLogId);
        }
        return list.size();
    }

    public List<DataHandler> getDataHandlerList() {
        return dataHandlerList;
    }

    public void setDataHandlerList(List<DataHandler> dataHandlerList) {
        this.dataHandlerList = dataHandlerList;
    }
}
