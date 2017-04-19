package com.elend.spider.common.vo;

import com.elend.spider.common.model.AntiSpiderWhiteListPO;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class AntiSpiderWhiteListVO extends AntiSpiderWhiteListPO {

    private static final long serialVersionUID = 1L;

    public AntiSpiderWhiteListVO() {
        super();
    }

    public AntiSpiderWhiteListVO(AntiSpiderWhiteListPO po) {
        this.id = po.getId();
        this.ip = po.getIp();
        this.createTime = po.getCreateTime();
        this.createAdmin = po.getCreateAdmin();
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this,
                                                  ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
