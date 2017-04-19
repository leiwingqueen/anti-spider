package com.elend.spider.api.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.elend.p2p.zk.conf.PropertiesHelper;

/**
 * 配置项
 * @author mgt
 * @date 2016年8月24日
 *
 */
@Component
public class PropHelper {
    
    @Autowired
    @Qualifier("antiSpiderZkPropertiesHelper")
    private PropertiesHelper zkPropertiesHelper;
    
    /**
     * 获取允许访问的IP
     * @return
     */
    public String[] getAccessIps(){
        String value = zkPropertiesHelper.getString("access_ips", "127.0.0.1,172.16.30.230,172.16.30.231,172.16.50.44,14.18.18.190");
        value = StringUtils.trimToEmpty(value);
        String[] ips = value.split(",");
        if(ips == null) {
            ips = new String[]{};
        }
        return ips;
    }
}
