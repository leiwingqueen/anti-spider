package com.elend.spider.common.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.elend.p2p.PageInfo;
import com.elend.p2p.Result;
import com.elend.p2p.constant.ResultCode;
import com.elend.spider.common.constant.AntiSpiderLogListType;
import com.elend.spider.common.constant.AntiSpiderLogOptType;
import com.elend.spider.common.mapper.AntiSpiderLogMapper;
import com.elend.spider.common.mapper.AntiSpiderWhiteListMapper;
import com.elend.spider.common.model.AntiSpiderLogPO;
import com.elend.spider.common.model.AntiSpiderWhiteListPO;
import com.elend.spider.common.service.AntiSpiderWhiteListService;
import com.elend.spider.common.vo.AntiSpiderWhiteListSearchVO;
import com.elend.spider.common.vo.AntiSpiderWhiteListVO;

@Transactional(readOnly = true)
@Service
public class AntiSpiderWhiteListServiceImpl implements
        AntiSpiderWhiteListService {

    @Autowired
    private AntiSpiderWhiteListMapper mapper;
    
    @Autowired
    private AntiSpiderLogMapper antiSpiderLogMapper;

    @Override
    public Result<PageInfo<AntiSpiderWhiteListVO>> list(
            AntiSpiderWhiteListSearchVO svo) {
        PageInfo<AntiSpiderWhiteListVO> paginInfo = new PageInfo<AntiSpiderWhiteListVO>();
        List<AntiSpiderWhiteListPO> list = mapper.list(svo);
        List<AntiSpiderWhiteListVO> volist = new ArrayList<AntiSpiderWhiteListVO>();
        for (AntiSpiderWhiteListPO po : list) {
            volist.add(new AntiSpiderWhiteListVO(po));
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
        return new Result<PageInfo<AntiSpiderWhiteListVO>>(
                                                           ResultCode.SUCCESS,
                                                           paginInfo);
    }

    @Override
    public Result<AntiSpiderWhiteListVO> get(int id) {
        AntiSpiderWhiteListPO po = mapper.get(id);
        if (po != null) {
            return new Result<AntiSpiderWhiteListVO>(
                                                     ResultCode.SUCCESS,
                                                     new AntiSpiderWhiteListVO(
                                                                               po));
        }
        return new Result<AntiSpiderWhiteListVO>(ResultCode.FAILURE, null);
    }

    @Override
    public Result<AntiSpiderWhiteListVO> save(AntiSpiderWhiteListVO vo, String remark) {
        
        AntiSpiderWhiteListSearchVO svo = new AntiSpiderWhiteListSearchVO();
        svo.setIp(vo.getIp());
        List<AntiSpiderWhiteListPO> list = mapper.list(svo);
        if(list.size() > 0) {
            return new Result<>(ResultCode.FAILURE, null, "ip已经存在，请勿重复添加");
        }
        
        mapper.insert(vo);
        
        AntiSpiderLogPO po = new AntiSpiderLogPO();
        //记录流水
        po.setCreateAdmin(vo.getCreateAdmin());
        po.setCreateTime(vo.getCreateTime());
        po.setIp(vo.getIp());
        po.setListType(AntiSpiderLogListType.WHITE.getType());
        po.setOptType(AntiSpiderLogOptType.IN.getType());
        po.setRemark(remark);

        antiSpiderLogMapper.insert(po);
        
        return new Result<AntiSpiderWhiteListVO>(ResultCode.SUCCESS, vo);
    }

    @Override
    public Result<Integer> delete(int id) {
        mapper.delete(id);
        return new Result<Integer>(ResultCode.SUCCESS, id);
    }

    @Override
    public Result<String> deleteByIp(String ip,
            String remark, String operator) {
        
        mapper.deleteByIp(ip);
        
        AntiSpiderLogPO po = new AntiSpiderLogPO();
        //记录流水
        po.setCreateAdmin(operator);
        po.setCreateTime(new Date());
        po.setIp(ip);
        po.setListType(AntiSpiderLogListType.WHITE.getType());
        po.setOptType(AntiSpiderLogOptType.OUT.getType());
        po.setRemark(remark);

        antiSpiderLogMapper.insert(po);
        
        return new Result<String>(ResultCode.SUCCESS, ip);
    }

    @Override
    public boolean hasIp(String ip) {
        AntiSpiderWhiteListSearchVO svo = new AntiSpiderWhiteListSearchVO();
        svo.setIp(ip);
        List<AntiSpiderWhiteListPO> list = mapper.list(svo);
        if(list.size() <= 0) {
            return false;
        }
        return true;
    }
}
