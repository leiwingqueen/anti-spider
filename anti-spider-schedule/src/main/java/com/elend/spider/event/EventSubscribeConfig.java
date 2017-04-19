package com.elend.spider.event;

import java.util.List;
import java.util.Map;

/**
 * 事件注册监听配置
 * @author liyongquan 2016年8月5日
 *
 */
public class EventSubscribeConfig {
    private Map<String,List<Listner>> config;

    public Map<String, List<Listner>> getConfig() {
        return config;
    }

    public void setConfig(Map<String, List<Listner>> config) {
        this.config = config;
    }
}
