package com.elend.spider.handler;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 爬虫判断参数
 * @author mgt
 * @date 2016年8月8日
 *
 */
public class SpiderJudgeParam {
    
    /**
     * ip
     */
    private String ip; 
    
    /**
     * 访问次数
     */
    private long accessNum; 
    
    /**
     * URL数量（去重）
     */
    private long urlNum; 
    
    /**
     * 用户数量(去重)
     */
    private long userNum; 
    
    /**
     * 访问次数方差
     */
    //private BigDecimal accessNumVariance; 
    /**
     * 访问次数列表(每分钟一条数据)
     */
    private List<Long> accessNumList; 
    
    /**
     * Map<User-Agent， 访问次数>
     */
    private Map<String, Long> userAgentMap;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public long getAccessNum() {
        return accessNum;
    }

    public void setAccessNum(long accessNum) {
        this.accessNum = accessNum;
    }

    public long getUrlNum() {
        return urlNum;
    }

    public void setUrlNum(long urlNum) {
        this.urlNum = urlNum;
    }

    public long getUserNum() {
        return userNum;
    }

    public void setUserNum(long userNum) {
        this.userNum = userNum;
    }

    public Map<String, Long> getUserAgentMap() {
        return userAgentMap;
    }

    public void setUserAgentMap(Map<String, Long> userAgentMap) {
        this.userAgentMap = userAgentMap;
    }
    
    public List<Long> getAccessNumList() {
        return accessNumList;
    }

    public void setAccessNumList(List<Long> accessNumList) {
        this.accessNumList = accessNumList;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
