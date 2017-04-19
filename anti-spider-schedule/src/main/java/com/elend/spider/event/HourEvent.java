package com.elend.spider.event;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 小时统计事件
 * @author liyongquan 2016年8月8日
 *
 */
public class HourEvent extends AbstractEvent {
    public HourEvent(){}
    public HourEvent(EventSubscribeConfig config, String ip, long accessNum,
            long urlNum, long userNum,Map<String,Long> userAgentMap,Date lastLogTime,List<Long> accessNumList) {
        super(config, ip, accessNum, urlNum, userNum,userAgentMap,lastLogTime);
        this.accessNumList=accessNumList;
    }
    public HourEvent(EventSubscribeConfig config,AbstractEvent event,Date lastLogTime,List<Long> accessNumList){
        super(config,event.getIp(),event.getAccessNum(),event.getUrlNum(),event.getUserNum(),event.getUserAgentMap(),lastLogTime);
        this.accessNumList=accessNumList;
    }
    /**
     * 访问次数方差
     */
    //protected BigDecimal accessNumVariance;
    /**
     * 访问次数列表(每分钟一条数据)
     */
    protected List<Long> accessNumList;
    /**
    public BigDecimal getAccessNumVariance() {
        return accessNumVariance;
    }

    public void setAccessNumVariance(BigDecimal accessNumVariance) {
        this.accessNumVariance = accessNumVariance;
    }*/
    public List<Long> getAccessNumList() {
        return accessNumList;
    }
    public void setAccessNumList(List<Long> accessNumList) {
        this.accessNumList = accessNumList;
    }
}
