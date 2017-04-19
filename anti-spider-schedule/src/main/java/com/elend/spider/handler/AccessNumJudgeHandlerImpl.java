package com.elend.spider.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.elend.p2p.Result;
import com.elend.p2p.constant.ResultCode;

/**
 * ip访问次数判断
 * @author mgt
 * @date 2016年8月8日
 *
 */
public class AccessNumJudgeHandlerImpl implements JudgeHandler {
    
    private final static Logger logger = LoggerFactory.getLogger(AccessNumJudgeHandlerImpl.class);

    /**
     * 限制的访问次数，根据具体的用处注入
     */
    private int limitNum;
    
    public void setLimitNum(int limitNum) {
        this.limitNum = limitNum;
    }

    @Override
    public Result<String> judge(SpiderJudgeParam param) {
        
        logger.info("ip访问次数校验，ip:{}, limitNum:{}, actualNum:{}", param.getIp(), limitNum, param.getAccessNum());
        if(param.getAccessNum() > limitNum) {
            logger.error("ip访问次数校验不通过，ip:{}, limitNum:{}, actualNum:{}", param.getIp(), limitNum, param.getAccessNum());
            return new Result<>(ResultCode.FAILURE, null, "ip访问次数超过限制次数，限制次数：" + limitNum + "，实际次数：" + param.getAccessNum());
        }
        return new Result<>(ResultCode.SUCCESS);
    }

}
