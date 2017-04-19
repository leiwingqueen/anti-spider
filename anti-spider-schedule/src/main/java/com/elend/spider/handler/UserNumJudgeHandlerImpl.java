package com.elend.spider.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.elend.p2p.Result;
import com.elend.p2p.constant.ResultCode;

/**
 * 用户访问次数判断
 * @author mgt
 * @date 2016年8月8日
 *
 */
public class UserNumJudgeHandlerImpl implements JudgeHandler {
    
    private final static Logger logger = LoggerFactory.getLogger(UserNumJudgeHandlerImpl.class);

    /**
     * 限制数量，根据具体的用处注入
     */
    private int limitNum;
    
    public void setLimitNum(int limitNum) {
        this.limitNum = limitNum;
    }

    @Override
    public Result<String> judge(SpiderJudgeParam param) {
        
        if(param.getUserNum() <= 0) {
            logger.info("平均用户访问次数不检验，用户个数小于0, ip:{}, userNum:{}", param.getIp(), param.getUserNum());
            return new Result<>(ResultCode.SUCCESS);
        }
        
        //求出每个用户的访问次数
        long actualNum = param.getAccessNum() / param.getUserNum();
        
        logger.info("用户数量校验，ip:{}, limitNum:{}, actualNum:{}", param.getIp(), limitNum, actualNum);
        if(actualNum > limitNum) {
            logger.error("平均用户访问次数检验不通过，ip:{}, limitNum:{}, actualNum:{}", param.getIp(), limitNum, actualNum);
            return new Result<>(ResultCode.FAILURE, null, "平均每个用户访问次数高于限制值， 限制次数：" + limitNum + "，访问次数:" + actualNum);
        }
        return new Result<>(ResultCode.SUCCESS);
    }

}
