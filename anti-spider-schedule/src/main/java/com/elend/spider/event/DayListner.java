package com.elend.spider.event;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.elend.p2p.Result;
import com.elend.p2p.constant.ResultCode;
import com.elend.p2p.util.DateUtil;
import com.elend.spider.common.service.AntiSpiderBlackListService;
import com.elend.spider.common.service.AntiSpiderWhiteListService;
import com.elend.spider.common.vo.AntiSpiderBlackListVO;
import com.elend.spider.handler.SpiderJudgeChain;
import com.elend.spider.handler.SpiderJudgeParam;

@Component
public class DayListner implements Listner{

    private final static Logger logger = LoggerFactory.getLogger(DayListner.class);
    
    @Autowired
    @Qualifier("daySpiderJudgeChain")
    private SpiderJudgeChain chain;
    
    @Autowired
    private AntiSpiderBlackListService antiSpiderBlackListService;
    
    @Autowired
    private AntiSpiderWhiteListService antiSpiderWhiteListService;

    @Override
    public Result<String> execute(Event event) {
        
        if(!(event instanceof DayEvent)){
            logger.error("小时事件错误， event:{}", event.getClass().getName());
            return new Result<>(ResultCode.FAILURE, null, "错误的事件类型:" + event.getClass().getName());
        }
        
        DayEvent dayEvent = (DayEvent) event;

        logger.info("DayListner execute, ip:{}, event:{}", dayEvent.getIp(), dayEvent);
        
        //判断IP是否在白名单当中，如果在，直接返回，不做判断
        if(antiSpiderWhiteListService.hasIp(dayEvent.getIp())) {
            logger.info("DayListner execute, ip:{}, 在白名单当中，不做判断", dayEvent.getIp());
            return new Result<>(ResultCode.SUCCESS);
        }
        
        //封装判断参数
        SpiderJudgeParam param = new SpiderJudgeParam();
        param.setAccessNum(dayEvent.getAccessNum());
        param.setIp(dayEvent.getIp());
        param.setUrlNum(dayEvent.getUrlNum());
        param.setUserAgentMap(dayEvent.getUserAgentMap());
        param.setUserNum(dayEvent.getUserNum());

        Result<String> result = chain.judge(param);
        logger.info("DayListner execute, ip:{}, result:{}", dayEvent.getIp(), result);
        
        if(result.isSuccess()) {
            return result;
        }
        
        logger.error("ip：{}， 鉴定为可疑爬虫, result:{}", dayEvent.getIp(), result);
        
        AntiSpiderBlackListVO vo = new AntiSpiderBlackListVO();
        vo.setCreateAdmin("Program");
        vo.setCreateTime(new Date());
        vo.setExpireTime(DateUtil.getDate(new Date(), 0, 1, 0, 0));
        vo.setIp(dayEvent.getIp());
        vo.setUpdateAdmin("Program");
        vo.setUpdateTime(new Date());
        //插入黑名单
        antiSpiderBlackListService.save(vo, result.getMessage());
        
        return new Result<>(ResultCode.SUCCESS);
    }

}
