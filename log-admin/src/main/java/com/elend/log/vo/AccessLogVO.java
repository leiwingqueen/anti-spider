package com.elend.log.vo;
import com.elend.log.model.AccessLogPO;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class AccessLogVO  extends AccessLogPO{
    /**
     * 
     */
    private static final long serialVersionUID = 8262162746262847912L;

    public AccessLogVO(){}
	public AccessLogVO(AccessLogPO po){
		this.id=po.getId();
		this.serverName=po.getServerName();
		this.uri=po.getUri();
		this.sessionId=po.getSessionId();
		this.userId=po.getUserId();
		this.ip=po.getIp();
		this.useTime=po.getUseTime();
		this.referer=po.getReferer();
		this.userAgent=po.getUserAgent();
		this.terminal=po.getTerminal();
		this.operate=po.getOperate();
		this.createTime=po.getCreateTime();
		this.isp=po.getIsp();
		this.system=po.getSystem();
	}
	
	@Override 
    public String toString() { 
            return ReflectionToStringBuilder.toString(this,ToStringStyle.SHORT_PREFIX_STYLE); 
    }
}
