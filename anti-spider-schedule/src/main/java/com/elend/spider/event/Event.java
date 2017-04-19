package com.elend.spider.event;

import com.elend.p2p.Result;

/**
 * 事件接口
 * @author liyongquan 2016年8月5日
 *
 */
public interface Event {
    /**
     * 订阅
     * @param listner
     * 订阅事件的观察者
     * @return
     * 是否订阅成功
     */
    Result<String> subscribe(Listner listner);
    /**
     * 取消订阅
     * @param listner
     * 订阅事件的观察者
     * @return
     * 是否取消订阅成功
     */
    Result<String> unsubscribe(Listner listner);
    /**
     * 通知所有观察者
     * @return
     * 通知结果
     */
    Result<String> notifyListner();
}
