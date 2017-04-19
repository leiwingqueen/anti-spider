package com.elend.log.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.elend.log.model.AccessLogPO;
import com.elend.log.vo.AccessLogSearchVO;
import com.elend.p2p.mapper.SqlMapper;

public interface AccessLogMapper extends SqlMapper {

    /**
     * 根据搜索条件返回列表
     * 
     * @param svo
     * @return
     */
    List<AccessLogPO> list(AccessLogSearchVO svo);

    /**
     * 根据搜索条件返回列表总数
     * 
     * @param svo
     * @return
     */
    int count(AccessLogSearchVO svo);

    /**
     * 根据主键id获取单条记录
     * 
     * @param id
     * @return
     */
    AccessLogPO get(int id);

    /**
     * 插入记录
     * 
     * @param vo
     */
    void insert(AccessLogPO po);

    /**
     * 更新记录
     * 
     * @param vo
     */
    void update(AccessLogPO po);

    /**
     * 根据主键id删除记录
     * 
     * @param id
     */
    void delete(int id);
    
    /**
     * 获取大于id的数据，最多拿limit条
     * @param id
     * 上一次最后得到的log_id
     * @param limit
     * 数量限制
     * @return
     */
    List<AccessLogPO> listById(@Param("id")long id,@Param("limit")int limit);
}
