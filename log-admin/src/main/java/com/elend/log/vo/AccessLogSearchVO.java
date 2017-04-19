package com.elend.log.vo;

import com.elend.p2p.util.vo.BaseSearchVO;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class AccessLogSearchVO extends BaseSearchVO {

    /** 访问服务：www.gzdai.com, app.gzdai.com */
    private String serverName;

    /** 访问地址 */
    private String uri;

    /** session Id，md5加密，用于跟踪用户的访问轨迹 */
    private String sessionId;

    /** 用户uid，未登陆时为0 */
    private long userId;

    /** 创建开始时间 */
    private String startTime;

    /** 创建结束时间 */
    private String endTime;
    
    /** ip地址*/
    private String ip;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
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
