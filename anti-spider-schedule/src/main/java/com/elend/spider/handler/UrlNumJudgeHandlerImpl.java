package com.elend.spider.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.elend.p2p.Result;
import com.elend.p2p.constant.ResultCode;

/**
 * 单url访问频率
 * @author mgt
 * @date 2016年8月8日
 *
 */
public class UrlNumJudgeHandlerImpl implements JudgeHandler {
    
    private final static Logger logger = LoggerFactory.getLogger(UrlNumJudgeHandlerImpl.class);

    /**
     * 限制数量，根据具体的用处注入
     */
    private int limitNum;
    
    public void setLimitNum(int limitNum) {
        this.limitNum = limitNum;
    }

    @Override
    public Result<String> judge(SpiderJudgeParam param) {
        
        if(param.getUrlNum() <= 0) {
            logger.info("单url访问频率不检验，url数量小于1, ip:{}, getUrlNum:{}", param.getIp(), param.getUrlNum());
            return new Result<>(ResultCode.SUCCESS);
        }
        
        long actualNum = param.getAccessNum() / param.getUrlNum();
        
        logger.info("url数量校验，ip:{}, limitNum:{}, actualNum:{}", param.getIp(), limitNum, actualNum);
        if(actualNum > limitNum) {
            logger.error("单url访问频率校验不通过，ip:{}, limitNum:{}, actualNum:{}", param.getIp(), limitNum, actualNum);
            return new Result<>(ResultCode.FAILURE, null, "单url平均访问次数高于限制次数，限制次数：" + limitNum + "，真实次数：" + actualNum);
        }
        return new Result<>(ResultCode.SUCCESS);
    }

}
