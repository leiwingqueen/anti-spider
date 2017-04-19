package com.elend.spider.handler;

import com.elend.p2p.Result;

/**
 * 处理累公共接口
 * @author mgt
 * @date 2016年8月8日
 *
 */
public interface JudgeHandler {

    /**
     * 判断是是否是爬虫
     * @param param
     * @return
     */
    Result<String> judge(SpiderJudgeParam param);
}
