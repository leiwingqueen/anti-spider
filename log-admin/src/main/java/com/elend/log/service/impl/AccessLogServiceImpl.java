package com.elend.log.service.impl;

import com.elend.log.mapper.AccessLogMapper;
import com.elend.log.service.AccessLogService;
import com.elend.log.model.AccessLogPO;
import com.elend.log.vo.AccessLogVO;
import com.elend.log.vo.AccessLogSearchVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elend.p2p.Result;
import com.elend.p2p.constant.ResultCode;
import com.elend.p2p.PageInfo;

import java.util.*;

@Service
public class AccessLogServiceImpl implements AccessLogService {

    @Autowired
    private AccessLogMapper mapper;

    @Override
    public Result<PageInfo<AccessLogVO>> list(AccessLogSearchVO svo) {
        PageInfo<AccessLogVO> paginInfo = new PageInfo<AccessLogVO>();
        List<AccessLogPO> list = mapper.list(svo);
        List<AccessLogVO> volist = new ArrayList<AccessLogVO>();
        for (AccessLogPO po : list) {
            volist.add(new AccessLogVO(po));
        }
        paginInfo.setList(volist);

        if (list.size() > 0) {
            int totalNum = mapper.count(svo);
            int totalPage = totalNum % svo.getSize() == 0 ? totalNum
                    / svo.getSize() : totalNum / svo.getSize() + 1;

            paginInfo.setCount(totalNum);
            paginInfo.setPage(svo.getPage());
            paginInfo.setPageCount(totalPage);
        } else {
            paginInfo.setCount(0);
            paginInfo.setPage(svo.getPage());
            paginInfo.setPageCount(0);
        }
        return new Result<PageInfo<AccessLogVO>>(ResultCode.SUCCESS,
                                                 paginInfo);
    }

    @Override
    public Result<AccessLogVO> get(int id) {
        AccessLogPO po = mapper.get(id);
        if (po != null) {
            return new Result<AccessLogVO>(ResultCode.SUCCESS,
                                           new AccessLogVO(po));
        }
        return new Result<AccessLogVO>(ResultCode.FAILURE, null);
    }

    @Override
    public Result<AccessLogVO> save(AccessLogVO vo) {
        if (vo.getId() > 0) {
            mapper.update(vo);
        } else {
            mapper.insert(vo);
        }
        return new Result<AccessLogVO>(ResultCode.SUCCESS, vo);
    }

    @Override
    public Result<Integer> delete(int id) {
        mapper.delete(id);
        return new Result<Integer>(ResultCode.SUCCESS, id);
    }

    @Override
    public List<AccessLogVO> listById(long id, int limit) {
        List<AccessLogPO> list=mapper.listById(id, limit);
        List<AccessLogVO> volist=new ArrayList<AccessLogVO>(limit);
        for(AccessLogPO po:list){
            volist.add(new AccessLogVO(po));
        }
        return volist;
    }
}
