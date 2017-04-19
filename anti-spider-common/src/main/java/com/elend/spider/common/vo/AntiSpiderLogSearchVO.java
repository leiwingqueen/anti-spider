package com.elend.spider.common.vo;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.elend.p2p.util.vo.BaseSearchVO;

public class AntiSpiderLogSearchVO extends BaseSearchVO {

    /** 请求ip */
    private String ip;

    /** 操作的名单类型,1:白名单,2:黑名单 */
    private int listType;

    /** 操作类型,1:进,2:出 */
    private int optType;

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

    public int getListType() {
        return listType;
    }

    public void setListType(int listType) {
        this.listType = listType;
    }

    public int getOptType() {
        return optType;
    }

    public void setOptType(int optType) {
        this.optType = optType;
    }

    public String getCreateAdmin() {
        return createAdmin;
    }

    public void setCreateAdmin(String createAdmin) {
        this.createAdmin = createAdmin;
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

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this,
                                                  ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
