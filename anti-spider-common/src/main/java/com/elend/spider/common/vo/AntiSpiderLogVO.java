package com.elend.spider.common.vo;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.elend.spider.common.constant.AntiSpiderLogListType;
import com.elend.spider.common.constant.AntiSpiderLogOptType;
import com.elend.spider.common.model.AntiSpiderLogPO;

public class AntiSpiderLogVO extends AntiSpiderLogPO {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 名单类型枚举
     */
    private AntiSpiderLogListType listTypeE;
    
    /**
     * 名单类性描述
     */
    private String listTypeS;
    
    /**
     * 操作类型枚举
     */
    private AntiSpiderLogOptType optTypeE;
    
    /**
     * 操作类型描述
     */
    private String optTypeS;
    
    public AntiSpiderLogVO(AntiSpiderLogPO po) {
        this.id = po.getId();
        this.ip = po.getIp();
        this.listType = po.getListType();
        this.optType = po.getOptType();
        this.remark = po.getRemark();
        this.createTime = po.getCreateTime();
        this.createAdmin = po.getCreateAdmin();
        if(po.getListType() > 0) {
            AntiSpiderLogListType from = AntiSpiderLogListType.from(po.getListType());
            this.listTypeE = from;
            this.listTypeS = from.getDesc();
        }
        if(po.getOptType() > 0) {
            AntiSpiderLogOptType from = AntiSpiderLogOptType.from(po.getOptType());
            this.optTypeE = from;
            this.optTypeS = from.getDesc();
        }
    }
    
    public AntiSpiderLogListType getListTypeE() {
        return listTypeE;
    }

    public void setListTypeE(AntiSpiderLogListType listTypeE) {
        this.listTypeE = listTypeE;
    }

    public String getListTypeS() {
        return listTypeS;
    }

    public void setListTypeS(String listTypeS) {
        this.listTypeS = listTypeS;
    }

    public String getOptTypeS() {
        return optTypeS;
    }

    public void setOptTypeS(String optTypeS) {
        this.optTypeS = optTypeS;
    }


    public AntiSpiderLogOptType getOptTypeE() {
        return optTypeE;
    }

    public void setOptTypeE(AntiSpiderLogOptType optTypeE) {
        this.optTypeE = optTypeE;
    }


    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this,
                                                  ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
