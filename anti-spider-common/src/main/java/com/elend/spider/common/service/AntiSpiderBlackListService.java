package com.elend.spider.common.service;

import com.elend.spider.common.vo.AntiSpiderBlackListVO;
import com.elend.spider.common.vo.AntiSpiderBlackListSearchVO;
import com.elend.p2p.Result;
import com.elend.p2p.PageInfo;

public interface AntiSpiderBlackListService {

    /**
     * 根据搜索条件返回列表
     * 
     * @param svo
     * @return
     */
    Result<PageInfo<AntiSpiderBlackListVO>> list(
            AntiSpiderBlackListSearchVO vo);

    /**
     * 根据主键id获取单条记录
     * 
     * @param id
     * @return
     */
    Result<AntiSpiderBlackListVO> get(int id);

    /**
     * 插入记录
     * 
     * @param vo
     */
    Result<AntiSpiderBlackListVO> save(AntiSpiderBlackListVO vo, String remark);

    /**
     * 根据主键id删除记录
     * 
     * @param id
     */
    Result<Integer> delete(int id);

    /**
     * 根据IP删除
     * @param ip
     * @param remark
     * @return
     */
    Result<String> deleteByIp(String ip, String remark, String  operator);
    
    /**
     * 名单中是否包含IP
     * @param ip
     * @return
     */
    boolean hasIp(String ip);

}
