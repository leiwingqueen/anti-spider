package com.elend.log.service;

import java.util.List;

import com.elend.log.model.AccessLogPO;
import com.elend.log.vo.AccessLogSearchVO;
import com.elend.log.vo.AccessLogVO;
import com.elend.p2p.PageInfo;
import com.elend.p2p.Result;

public interface AccessLogService {

    /**
     * 根据搜索条件返回列表
     * 
     * @param svo
     * @return
     */
    Result<PageInfo<AccessLogVO>> list(AccessLogSearchVO vo);

    /**
     * 根据主键id获取单条记录
     * 
     * @param id
     * @return
     */
    Result<AccessLogVO> get(int id);

    /**
     * 插入记录
     * 
     * @param vo
     */
    Result<AccessLogVO> save(AccessLogVO vo);

    /**
     * 根据主键id删除记录
     * 
     * @param id
     */
    Result<Integer> delete(int id);
    
    /**
     * 获取大于id的数据，最多拿limit条
     * @param id
     * 上一次最后得到的log_id
     * @param limit
     * 数量限制
     * @return
     */
    List<AccessLogVO> listById(long id,int limit);
}
