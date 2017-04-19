package com.elend.spider.data;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.elend.p2p.spring.SpringContextUtil;
import com.elend.spider.event.AbstractEvent;
import com.elend.spider.event.Event;
import com.elend.spider.event.MinuteEvent;

/**
 * 分钟数据处理模块
 * @author liyongquan 2016年8月11日
 *
 */
@Component("minuteDataHandler")
public class MinuteDataHandler extends AbstractDataHandler {
    private final static Logger logger = LoggerFactory.getLogger(MinuteDataHandler.class);

    @Override
    protected TimeType getTimeType() {
        return TimeType.MINUTE;
    }

    @Override
    public Event generateEvent(String ip,Date lastLogTime) {
        AbstractEvent event=(AbstractEvent)super.generateEvent(ip,lastLogTime);
        MinuteEvent minuteEvent = new MinuteEvent(config, event,lastLogTime);
        if(logger.isDebugEnabled()){
            logger.info("生成MinuteEvent,event:{}",minuteEvent.toString());
        }
        return minuteEvent;
    }

    @Override
    protected DataHandler getCurrentProxy() {
        return (DataHandler)SpringContextUtil.getBean("minuteDataHandler");
    }

}
