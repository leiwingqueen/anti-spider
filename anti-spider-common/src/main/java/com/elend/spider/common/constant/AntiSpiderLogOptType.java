package com.elend.spider.common.constant;

/**
 * 操作类型,1:进,2:出
 * @author mgt
 * @date 2016年8月5日
 * 
 */
public enum AntiSpiderLogOptType {
    
    IN(1, "进"),
    OUT(2, "出"),
    ;
    
    /**
     * 类型
     */
    private int type;

    /**
     * 描述
     */
    private String desc;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    private AntiSpiderLogOptType(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }
    
    public static AntiSpiderLogOptType from(int type) {
        for(AntiSpiderLogOptType one : AntiSpiderLogOptType.values()) {
            if(one.getType() == type) {
                return one;
            }
        }
        throw new IllegalArgumentException("illegal type ：" + type);
    }
}
