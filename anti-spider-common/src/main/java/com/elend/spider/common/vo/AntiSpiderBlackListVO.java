package com.elend.spider.common.vo;

import com.elend.spider.common.model.AntiSpiderBlackListPO;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class AntiSpiderBlackListVO extends AntiSpiderBlackListPO {

    private static final long serialVersionUID = 1L;
    
    public AntiSpiderBlackListVO() {
        super();
    }

    public AntiSpiderBlackListVO(AntiSpiderBlackListPO po) {
        this.id = po.getId();
        this.ip = po.getIp();
        this.expireTime = po.getExpireTime();
        this.createTime = po.getCreateTime();
        this.updateTime = po.getUpdateTime();
        this.createAdmin = po.getCreateAdmin();
        this.updateAdmin = po.getUpdateAdmin();
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this,
                                                  ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
