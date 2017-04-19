package com.elend.spider.data;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.elend.p2p.spring.SpringContextUtil;
import com.elend.spider.event.AbstractEvent;
import com.elend.spider.event.DayEvent;
import com.elend.spider.event.Event;

/**
 * 天数据处理模块
 * @author liyongquan 2016年8月11日
 *
 */
@Component("dayDataHandler")
public class DayDataHandler extends AbstractDataHandler {
    private final static Logger logger = LoggerFactory.getLogger(DayDataHandler.class);

    @Override
    protected TimeType getTimeType() {
        return TimeType.DAY;
    }

    @Override
    public Event generateEvent(String ip,Date lastLogTime) {
        AbstractEvent event=(AbstractEvent)super.generateEvent(ip,lastLogTime);
        DayEvent dayEvent = new DayEvent(config, event,lastLogTime);
        logger.info("生成DayEvent,event:{}",dayEvent.toString());
        return dayEvent;
    }

    @Override
    protected DataHandler getCurrentProxy() {
        return (DataHandler)SpringContextUtil.getBean("dayDataHandler");
    }

}
