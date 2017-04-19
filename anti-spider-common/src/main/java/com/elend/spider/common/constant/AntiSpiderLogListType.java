package com.elend.spider.common.constant;

/**
 * 操作的名单类型,1:白名单,2:黑名单
 * @author mgt
 * @date 2016年8月5日
 *
 */
public enum AntiSpiderLogListType {
    
    BLACK(1, "黑名单"),
    WHITE(2, "白名单"),
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

    private AntiSpiderLogListType(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }
    
    public static AntiSpiderLogListType from(int type) {
        for(AntiSpiderLogListType one : AntiSpiderLogListType.values()) {
            if(one.getType() == type) {
                return one;
            }
        }
        throw new IllegalArgumentException("illegal type ：" + type);
    }
}
