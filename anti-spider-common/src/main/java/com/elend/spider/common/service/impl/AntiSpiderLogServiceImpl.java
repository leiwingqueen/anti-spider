package com.elend.spider.common.service.impl;

import com.elend.spider.common.mapper.AntiSpiderLogMapper;
import com.elend.spider.common.service.AntiSpiderLogService;
import com.elend.spider.common.model.AntiSpiderLogPO;
import com.elend.spider.common.vo.AntiSpiderLogVO;
import com.elend.spider.common.vo.AntiSpiderLogSearchVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.elend.p2p.Result;
import com.elend.p2p.constant.ResultCode;
import com.elend.p2p.PageInfo;

import java.util.*;

@Transactional(readOnly = true)
@Service
public class AntiSpiderLogServiceImpl implements AntiSpiderLogService {

    @Autowired
    private AntiSpiderLogMapper mapper;

    @Override
    public Result<PageInfo<AntiSpiderLogVO>> list(AntiSpiderLogSearchVO svo) {
        PageInfo<AntiSpiderLogVO> paginInfo = new PageInfo<AntiSpiderLogVO>();
        List<AntiSpiderLogPO> list = mapper.list(svo);
        List<AntiSpiderLogVO> volist = new ArrayList<AntiSpiderLogVO>();
        for (AntiSpiderLogPO po : list) {
            volist.add(new AntiSpiderLogVO(po));
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
        return new Result<PageInfo<AntiSpiderLogVO>>(ResultCode.SUCCESS,
                                                     paginInfo);
    }

    @Override
    public Result<AntiSpiderLogVO> get(int id) {
        AntiSpiderLogPO po = mapper.get(id);
        if (po != null) {
            return new Result<AntiSpiderLogVO>(ResultCode.SUCCESS,
                                               new AntiSpiderLogVO(po));
        }
        return new Result<AntiSpiderLogVO>(ResultCode.FAILURE, null);
    }

    @Override
    public Result<AntiSpiderLogVO> save(AntiSpiderLogVO vo) {
        if (vo.getId() > 0) {
            mapper.update(vo);
        } else {
            mapper.insert(vo);
        }
        return new Result<AntiSpiderLogVO>(ResultCode.SUCCESS, vo);
    }

    @Override
    public Result<Integer> delete(int id) {
        mapper.delete(id);
        return new Result<Integer>(ResultCode.SUCCESS, id);
    }
}
