package com.elend.log.model;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class AccessLogPO implements Serializable {

    /**  */
    protected int id;

    /** 访问服务：www.gzdai.com, m.gzdai.com, app.gzdai.com */
    protected String serverName;

    /** 访问地址 */
    protected String uri;

    /** session Id，md5加密，用于跟踪用户的访问轨迹 */
    protected String sessionId;

    /** 用户uid，未登陆时为0 */
    protected long userId;

    /** 访问ip */
    protected String ip;

    /** 访问耗时 */
    protected int useTime;

    /** 来源记录 */
    protected String referer;

    /** ua */
    protected String userAgent;

    /** 终端：pc，wap，Andirod APP，IOS APP */
    protected int terminal;

    protected int operate;

    /** 创建时间 */
    protected Date createTime;

    protected int isp;

    protected int system;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getUseTime() {
        return useTime;
    }

    public void setUseTime(int useTime) {
        this.useTime = useTime;
    }

    public String getReferer() {
        return referer;
    }

    public void setReferer(String referer) {
        this.referer = referer;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public int getTerminal() {
        return terminal;
    }

    public void setTerminal(int terminal) {
        this.terminal = terminal;
    }

    public int getOperate() {
        return operate;
    }

    public void setOperate(int operate) {
        this.operate = operate;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getIsp() {
        return isp;
    }

    public void setIsp(int isp) {
        this.isp = isp;
    }

    public int getSystem() {
        return system;
    }

    public void setSystem(int system) {
        this.system = system;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this,
                                                  ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
