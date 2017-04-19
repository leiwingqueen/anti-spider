package com.elend.spider.event;

import java.util.Date;
import java.util.Map;

/**
 * 分钟统计事件
 * @author liyongquan 2016年8月5日
 *
 */
public class MinuteEvent extends AbstractEvent{
    public MinuteEvent(){}
    public MinuteEvent(EventSubscribeConfig config, String ip, long accessNum,
            long urlNum, long userNum,Map<String,Long> userAgentMap,Date logTime) {
        super(config, ip, accessNum, urlNum, userNum,userAgentMap,logTime);
    }
    public MinuteEvent(EventSubscribeConfig config,AbstractEvent event,Date logTime){
        super(config,event.getIp(),event.getAccessNum(),event.getUrlNum(),event.getUserNum(),event.getUserAgentMap(),logTime);
    }
}
