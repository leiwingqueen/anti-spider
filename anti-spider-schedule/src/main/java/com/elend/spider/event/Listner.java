package com.elend.spider.event;

import com.elend.p2p.Result;

/**
 * 事件监听
 * @author liyongquan 2016年8月5日
 *
 */
public interface Listner {
    Result<String> execute(Event event);
}
