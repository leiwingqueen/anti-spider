package com.elend.spider.common.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.elend.p2p.PageInfo;
import com.elend.p2p.Result;
import com.elend.p2p.constant.ResultCode;
import com.elend.spider.common.constant.AntiSpiderLogListType;
import com.elend.spider.common.constant.AntiSpiderLogOptType;
import com.elend.spider.common.mapper.AntiSpiderBlackListMapper;
import com.elend.spider.common.mapper.AntiSpiderLogMapper;
import com.elend.spider.common.model.AntiSpiderBlackListPO;
import com.elend.spider.common.model.AntiSpiderLogPO;
import com.elend.spider.common.service.AntiSpiderBlackListService;
import com.elend.spider.common.vo.AntiSpiderBlackListSearchVO;
import com.elend.spider.common.vo.AntiSpiderBlackListVO;

@Transactional(readOnly = true)
@Service
public class AntiSpiderBlackListServiceImpl implements
        AntiSpiderBlackListService {

    @Autowired
    private AntiSpiderBlackListMapper mapper;
    
    @Autowired
    private AntiSpiderLogMapper antiSpiderLogMapper;

    @Override
    public Result<PageInfo<AntiSpiderBlackListVO>> list(
            AntiSpiderBlackListSearchVO svo) {
        PageInfo<AntiSpiderBlackListVO> paginInfo = new PageInfo<AntiSpiderBlackListVO>();
        List<AntiSpiderBlackListPO> list = mapper.list(svo);
        List<AntiSpiderBlackListVO> volist = new ArrayList<AntiSpiderBlackListVO>();
        for (AntiSpiderBlackListPO po : list) {
            AntiSpiderBlackListVO vo = new AntiSpiderBlackListVO(po);
            volist.add(vo);
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
        return new Result<PageInfo<AntiSpiderBlackListVO>>(
                                                           ResultCode.SUCCESS,
                                                           paginInfo);
    }

    @Override
    public Result<AntiSpiderBlackListVO> get(int id) {
        AntiSpiderBlackListPO po = mapper.get(id);
        if (po != null) {
            return new Result<AntiSpiderBlackListVO>(
                                                     ResultCode.SUCCESS,
                                                     new AntiSpiderBlackListVO(
                                                                               po));
        }
        return new Result<AntiSpiderBlackListVO>(ResultCode.FAILURE, null);
    }

    @Override
    @Transactional(propagation=Propagation.REQUIRED)
    public Result<AntiSpiderBlackListVO> save(AntiSpiderBlackListVO vo, String remark) {
        
        mapper.insert(vo);
        
        AntiSpiderLogPO po = new AntiSpiderLogPO();
        //记录流水
        po.setCreateAdmin(vo.getCreateAdmin());
        po.setCreateTime(vo.getCreateTime());
        po.setIp(vo.getIp());
        po.setListType(AntiSpiderLogListType.BLACK.getType());
        po.setOptType(AntiSpiderLogOptType.IN.getType());
        po.setRemark(remark);

        antiSpiderLogMapper.insert(po);
        
        return new Result<AntiSpiderBlackListVO>(ResultCode.SUCCESS, vo);
    }

    @Override
    public Result<Integer> delete(int id) {
        mapper.delete(id);
        return new Result<Integer>(ResultCode.SUCCESS, id);
    }

    @Override
    public Result<String> deleteByIp(String ip, String remark, String  operator) {
        
        mapper.deleteByIp(ip);
        
        AntiSpiderLogPO po = new AntiSpiderLogPO();
        //记录流水
        po.setCreateAdmin(operator);
        po.setCreateTime(new Date());
        po.setIp(ip);
        po.setListType(AntiSpiderLogListType.BLACK.getType());
        po.setOptType(AntiSpiderLogOptType.OUT.getType());
        po.setRemark(remark);

        antiSpiderLogMapper.insert(po);
        
        return new Result<String>(ResultCode.SUCCESS, ip);
    }

    @Override
    public boolean hasIp(String ip) {
        AntiSpiderBlackListSearchVO svo = new AntiSpiderBlackListSearchVO();
        svo.setIp(ip);
        svo.setExpireTime(new Date());
        List<AntiSpiderBlackListPO> list = mapper.list(svo );
        if(list.size() <= 0) {
            return false;
        }
        
        return true;
    }
}
