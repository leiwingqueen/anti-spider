package com.elend.spider.common.vo;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.elend.p2p.util.vo.BaseSearchVO;

public class AntiSpiderWhiteListSearchVO extends BaseSearchVO {

    /** 请求ip */
    private String ip;

    /** 创建时间 */
    private String startTime;
    
    /** 创建时间 */
    private String endTime;

    /** 创建人 */
    private String createAdmin;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getCreateAdmin() {
        return createAdmin;
    }

    public void setCreateAdmin(String createAdmin) {
        this.createAdmin = createAdmin;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this,
                                                  ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
