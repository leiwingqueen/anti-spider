package com.elend.spider.event;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.elend.p2p.Result;
import com.elend.p2p.constant.ResultCode;

/**
 * 事件公共属性
 * 
 * @author liyongquan 2016年8月8日
 */
public class AbstractEvent implements Event {
    private final static Logger logger = LoggerFactory.getLogger(AbstractEvent.class);

    /**
     * 事件对应的观察者
     */
    protected List<Listner> listnerList;

    /**
     * ip
     */
    protected String ip;

    /**
     * 访问次数
     */
    protected long accessNum;

    /**
     * URL数量（去重）
     */
    protected long urlNum;

    /**
     * 用户数量(去重)
     */
    protected long userNum;

    /**
     * Map<User-Agent， 访问次数>
     */
    protected Map<String, Long> userAgentMap;
    /**
     * 触发事件的最后一条log的时间
     */
    protected Date logTime;

    public AbstractEvent() {
    }

    /**
     * 构造函数
     * 
     * @param config
     *            事件注册配置
     * @param ip
     *            对应的IP
     * @param accessNum
     *            访问次数
     * @param urlNum
     *            URL数量(去重)
     * @param userNum
     *            用户数量(去重)
     * @param userAgentMap
     *            user-agent对应的访问次数
     * @param logTime
     * 触发事件的最后一条log的时间
     * 
     */
    public AbstractEvent(EventSubscribeConfig config, String ip,
            long accessNum, long urlNum, long userNum,
            Map<String, Long> userAgentMap,Date logTime) {
        this.ip = ip;
        this.accessNum = accessNum;
        this.urlNum = urlNum;
        this.userNum = userNum;
        this.userAgentMap = userAgentMap;
        this.logTime=logTime;
        // 事件注册
        for (String className : config.getConfig().keySet()) {
            if (className.equals(this.getClass().getName())) {
                for (Listner listner : config.getConfig().get(className)) {
                    this.subscribe(listner);
                }
                return;
            }
        }
    }

    @Override
    public synchronized Result<String> subscribe(Listner listner) {
        if (logger == null) {
            logger.error("注册观察者失败...listner不能为null");
            return new Result<String>(ResultCode.FAILURE, null,
                                      "注册观察者失败...listner不能为null");
        }
        if (listnerList == null) {
            listnerList = new ArrayList<Listner>();
        }
        listnerList.add(listner);
        logger.info("监听者{}订阅事件{}", listner.getClass().getName(),
                    this.getClass().getName());
        return new Result<String>(ResultCode.SUCCESS, null);
    }

    @Override
    public synchronized Result<String> unsubscribe(Listner listner) {
        if (listner == null) {
            logger.error("取消订阅观察者失败...listner不能为null");
            return new Result<String>(ResultCode.FAILURE, null,
                                      "取消订阅观察者失败...listner不能为null");
        }
        if (listnerList == null || listnerList.size() == 0)
            return new Result<String>(ResultCode.FAILURE,
                                      "取消订阅观察者失败...关注当前事件的观察者为空");
        for (Listner lis : listnerList) {
            if (lis.equals(listner)) {// 这个方法已经被重写，根据类名判断是否相等
                listnerList.remove(lis);
                return new Result<String>(ResultCode.SUCCESS, null);
            }
        }
        return new Result<String>(ResultCode.FAILURE,
                                  "取消订阅观察者失败...{}没有订阅当前事件",
                                  listner.getClass().getName());
    }

    @Override
    public Result<String> notifyListner() {
        if (listnerList == null || listnerList.size() == 0) {
            return new Result<String>(ResultCode.SUCCESS, null);
        }
        for (Listner listner : listnerList) {
            Result<String> result = listner.execute(this);
            if (!result.isSuccess()) {
                logger.error("事件通知失败...错误原因:{}", result.getMessage());
                return result;
            }
        }
        return new Result<String>(ResultCode.SUCCESS, null);
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public long getAccessNum() {
        return accessNum;
    }

    public void setAccessNum(long accessNum) {
        this.accessNum = accessNum;
    }

    public long getUrlNum() {
        return urlNum;
    }

    public void setUrlNum(long urlNum) {
        this.urlNum = urlNum;
    }

    public long getUserNum() {
        return userNum;
    }

    public void setUserNum(long userNum) {
        this.userNum = userNum;
    }

    public Map<String, Long> getUserAgentMap() {
        return userAgentMap;
    }

    public void setUserAgentMap(Map<String, Long> userAgentMap) {
        this.userAgentMap = userAgentMap;
    }

    public Date getLogTime() {
        return logTime;
    }

    public void setLogTime(Date logTime) {
        this.logTime = logTime;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this,
                                                  ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
