package com.elend.spider.event;

import java.util.Date;
import java.util.Map;

/**
 * 每天统计事件
 * @author liyongquan 2016年8月8日
 *
 */
public class DayEvent extends AbstractEvent{
    public DayEvent(){}

    public DayEvent(EventSubscribeConfig config, String ip, long accessNum,
            long urlNum, long userNum,Map<String,Long> userAgentMap,Date lastLogTime) {
        super(config, ip, accessNum, urlNum, userNum,userAgentMap,lastLogTime);
    }
    
    public DayEvent(EventSubscribeConfig config,AbstractEvent event,Date lastLogTime){
        super(config,event.getIp(),event.getAccessNum(),event.getUrlNum(),event.getUserNum(),event.getUserAgentMap(),lastLogTime);
    }
}
