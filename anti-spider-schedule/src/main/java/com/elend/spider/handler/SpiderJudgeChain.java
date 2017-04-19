package com.elend.spider.handler;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.elend.p2p.Result;
import com.elend.p2p.constant.ResultCode;

/**
 * 爬虫鉴定链
 * @author mgt
 * @date 2016年8月8日
 *
 */
public class SpiderJudgeChain {
    
    private final static Logger logger = LoggerFactory.getLogger(SpiderJudgeChain.class);
    
    /**
     * 处理类集合
     */
    private List<JudgeHandler> handlerList;
    
    public List<JudgeHandler> getHandlerList() {
        return handlerList;
    }

    public void setHandlerList(List<JudgeHandler> handlerList) {
        this.handlerList = handlerList;
    }

    /**
     * 
     * @param param
     * @return
     */
    public Result<String> judge(SpiderJudgeParam param) {
        
        logger.info("judge param :{}", param);
        
        for(JudgeHandler handler : handlerList) {
            
            Result<String> result = handler.judge(param);
            logger.info("ip:{}, judgeHandler:{}, result:{}", param.getIp(), handler.getClass().getName(), result);
            
            if(!result.isSuccess()) {
                logger.error("ip:{}, 鉴定可疑的爬虫, judgeHandler:{}, result:{}， param:{}", param.getIp(), handler.getClass().getName(), result, param);
                return result;
            }
        }
        
        logger.info("ip:{}, 鉴定为非爬虫", param.getIp());
        
        return new Result<>(ResultCode.SUCCESS);
    }

}
