package com.elend.spider.common.service;

import com.elend.spider.common.vo.AntiSpiderLogVO;
import com.elend.spider.common.vo.AntiSpiderLogSearchVO;
import com.elend.p2p.Result;
import com.elend.p2p.PageInfo;

public interface AntiSpiderLogService {

    /**
     * 根据搜索条件返回列表
     * 
     * @param svo
     * @return
     */
    Result<PageInfo<AntiSpiderLogVO>> list(AntiSpiderLogSearchVO vo);

    /**
     * 根据主键id获取单条记录
     * 
     * @param id
     * @return
     */
    Result<AntiSpiderLogVO> get(int id);

    /**
     * 插入记录
     * 
     * @param vo
     */
    Result<AntiSpiderLogVO> save(AntiSpiderLogVO vo);

    /**
     * 根据主键id删除记录
     * 
     * @param id
     */
    Result<Integer> delete(int id);

}
