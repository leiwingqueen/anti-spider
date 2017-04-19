package com.elend.spider.common.service;

import com.elend.spider.common.vo.AntiSpiderWhiteListVO;
import com.elend.spider.common.vo.AntiSpiderWhiteListSearchVO;
import com.elend.p2p.Result;
import com.elend.p2p.PageInfo;

public interface AntiSpiderWhiteListService {

    /**
     * 根据搜索条件返回列表
     * 
     * @param svo
     * @return
     */
    Result<PageInfo<AntiSpiderWhiteListVO>> list(
            AntiSpiderWhiteListSearchVO vo);

    /**
     * 根据主键id获取单条记录
     * 
     * @param id
     * @return
     */
    Result<AntiSpiderWhiteListVO> get(int id);

    /**
     * 插入记录
     * 
     * @param vo
     */
    Result<AntiSpiderWhiteListVO> save(AntiSpiderWhiteListVO vo, String remark);

    /**
     * 根据主键id删除记录
     * 
     * @param id
     */
    Result<Integer> delete(int id);

    /**
     * 根据ip删除
     * @param ip
     * @param remark
     * @param userName
     * @return
     */
    Result<String> deleteByIp(String ip,
            String remark, String userName);
    
    /**
     * 是否包含该IP
     * @param ip
     * @return
     */
    boolean hasIp(String ip);

}
