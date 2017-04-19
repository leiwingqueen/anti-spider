package com.elend.spider.data;

import java.util.Date;
import java.util.List;

import com.elend.log.vo.AccessLogVO;

/**
 * 数据处理模块
 * @author liyongquan 2016年8月9日
 *
 */
public interface DataHandler {
    /**
     * 数据处理(实际外部调用使用这个方法)
     * @param logList
     * access_log访问日志
     */
    void process(List<AccessLogVO> logList);
    /**
     * 数据处理，针对单条日志(由于traceId需要对这个方法特别关注，因此单独抽取出来)
     * @param log
     * @param lastLogTime
     * 上一条处理日志的最后时间
     * @return
     * 最后的处理时间
     */
    Date process(AccessLogVO log,Date lastLogTime);
    
}
