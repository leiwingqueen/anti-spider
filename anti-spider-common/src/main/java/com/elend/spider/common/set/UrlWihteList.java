package com.elend.spider.common.set;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 * url白名单
 * @author mgt
 * @date 2016年8月4日
 *
 */
public class UrlWihteList {
    /**
     * url集合
     */
    private List<String> urls;
    
    public List<String> getUrls() {
        return urls;
    }

    public void setUrls(List<String> urls) {
        this.urls = urls;
    }

    /**
     * 获取所有的url
     * @return
     */
    public List<String> listAll() {
        return urls;
    }
    
    /**
     * 判断是有白名单中有指定的的url
     * @param url
     * @return
     */
    public boolean isHas(String url) {
        if(StringUtils.isBlank(url)) {
            return false;
        }
        return urls.contains(url);
    }
}
