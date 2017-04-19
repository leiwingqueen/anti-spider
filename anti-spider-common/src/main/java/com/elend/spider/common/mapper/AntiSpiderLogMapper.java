package com.elend.spider.common.mapper;

import java.util.List;
import com.elend.p2p.mapper.SqlMapper;
import com.elend.spider.common.vo.AntiSpiderLogSearchVO;
import com.elend.spider.common.model.AntiSpiderLogPO;

public interface AntiSpiderLogMapper extends SqlMapper {

    /**
     * 根据搜索条件返回列表
     * 
     * @param svo
     * @return
     */
    List<AntiSpiderLogPO> list(AntiSpiderLogSearchVO svo);

    /**
     * 根据搜索条件返回列表总数
     * 
     * @param svo
     * @return
     */
    int count(AntiSpiderLogSearchVO svo);

    /**
     * 根据主键id获取单条记录
     * 
     * @param id
     * @return
     */
    AntiSpiderLogPO get(int id);

    /**
     * 插入记录
     * 
     * @param vo
     */
    void insert(AntiSpiderLogPO po);

    /**
     * 更新记录
     * 
     * @param vo
     */
    void update(AntiSpiderLogPO po);

    /**
     * 根据主键id删除记录
     * 
     * @param id
     */
    void delete(int id);

}
