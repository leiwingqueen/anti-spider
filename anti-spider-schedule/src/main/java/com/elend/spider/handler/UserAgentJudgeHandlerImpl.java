package com.elend.spider.handler;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.elend.p2p.Result;
import com.elend.p2p.constant.ResultCode;

/**
 * UserAgent判断
 * @author mgt
 * @date 2016年8月8日
 *
 */
public class UserAgentJudgeHandlerImpl implements JudgeHandler {
    
    private final static Logger logger = LoggerFactory.getLogger(UserAgentJudgeHandlerImpl.class);

    /**
     * 限制数量，根据具体的用处注入
     */
    private Map<String, Integer> limitAgent;
    
    public void setLimitAgent(Map<String, Integer> limitAgent) {
        this.limitAgent = limitAgent;
    }
    

    @Override
    public Result<String> judge(SpiderJudgeParam param) {
        
        if(param.getUserAgentMap() == null) {
            logger.info(" UserAgent判断不检验，UserAgent为空, ip:{}, getUserAgentMap:{}", param.getIp(), param.getUserAgentMap());
            return new Result<>(ResultCode.SUCCESS);
        }
        
        logger.info("UserAgent判断，ip:{}, limitAgent:{}, agents:{}", param.getIp(), limitAgent, param.getUserAgentMap());

        for(String key : param.getUserAgentMap().keySet()) {
            
            //判断是否useragent是否可疑
            Result<Integer> result = isHas(key);
            if(!result.isSuccess()) {
                continue;
            }
            
            Integer limitNum = result.getObject();
            
            Long actualNum = param.getUserAgentMap().get(key);
            
            if(actualNum > limitNum) {
                logger.error("UserAgent判断不通过，ip:{}, userAgent:{}, limitNum:{}, actualNum:{}", param.getIp(), key, limitNum, actualNum);
                return new Result<>(ResultCode.FAILURE, null, "可疑的User-Agent：" + key);
            }
        }
        
        return new Result<>(ResultCode.SUCCESS);
    }
    
    /**
     * 是否含有指定的ua
     * @param ua
     * @return
     */
    public Result<Integer> isHas(String ua) {
        for(String regex : limitAgent.keySet()) {
            boolean has = ua.toLowerCase().matches(regex.toLowerCase());
            if(has) {
                return new Result<>(ResultCode.SUCCESS, limitAgent.get(regex));
            }
        }
        return new Result<>(ResultCode.FAILURE);
    }
}
