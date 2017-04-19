package com.elend.log.facade;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.elend.log.service.AccessLogService;
import com.elend.log.vo.AccessLogSearchVO;
import com.elend.log.vo.AccessLogVO;
import com.elend.p2p.PageInfo;
import com.elend.p2p.Result;

@Component
public class AccessLogFacade {

    @Autowired
    private AccessLogService accessLogService;
    
    public Result<PageInfo<AccessLogVO>> list(AccessLogSearchVO svo){
        return accessLogService.list(svo);
    }
    
    /**
     * 获取大于id的数据，最多拿limit条
     * @param id
     * 上一次最后得到的log_id
     * @param limit
     * 数量限制
     * @return
     */
    public List<AccessLogVO> listById(long id,int limit){
        return accessLogService.listById(id, limit);
    }
}
