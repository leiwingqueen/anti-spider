package com.elend.spider.api.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.elend.p2p.BaseController;
import com.elend.p2p.PageInfo;
import com.elend.p2p.Result;
import com.elend.p2p.constant.ResultCode;
import com.elend.p2p.resource.ClassResourceDesc;
import com.elend.p2p.resource.MethodResourceDesc;
import com.elend.spider.common.service.AntiSpiderBlackListService;
import com.elend.spider.common.service.AntiSpiderWhiteListService;
import com.elend.spider.common.set.UrlWihteList;
import com.elend.spider.common.vo.AntiSpiderBlackListSearchVO;
import com.elend.spider.common.vo.AntiSpiderBlackListVO;
import com.elend.spider.common.vo.AntiSpiderWhiteListSearchVO;
import com.elend.spider.common.vo.AntiSpiderWhiteListVO;

@ClassResourceDesc(firstCate = "api")
@Controller
public class ApiController extends BaseController {
    
    @Autowired
    private AntiSpiderBlackListService antiSpiderBlackListService;
    
    @Autowired
    private AntiSpiderWhiteListService antiSpiderWhiteListService;
    
    @Autowired
    private UrlWihteList urlWihteList;

    @MethodResourceDesc(name = "检查IP是否在黑名单")
    @RequestMapping(value = "/checkBlack.do")
    @ResponseBody
    public Result<Boolean> checkBlack(HttpServletRequest request, HttpServletResponse response, @RequestParam(required = true) String ip) {
        
        if(antiSpiderWhiteListService.hasIp(ip)) {
            return new Result<>(ResultCode.SUCCESS, false);
        }
       
        if(antiSpiderBlackListService.hasIp(ip)) {
            return new Result<>(ResultCode.SUCCESS, true);
        }
        
        return new Result<>(ResultCode.SUCCESS, false);
    }
    
    @MethodResourceDesc(name = "获取URL白名单")
    @RequestMapping(value = "/urlWihteList.do")
    @ResponseBody
    public Result<List<String>> urlWihteList(HttpServletRequest request, HttpServletResponse response) {
        return new Result<>(ResultCode.SUCCESS, urlWihteList.getUrls());
    }
    
    @MethodResourceDesc(name = "获取IP黑名单")
    @RequestMapping(value = "/ipBlackList.do")
    @ResponseBody
    public Result<List<String>> ipBlackList(HttpServletRequest request, HttpServletResponse response) {
        
        AntiSpiderBlackListSearchVO vo = new AntiSpiderBlackListSearchVO();
        vo.setSize(Integer.MAX_VALUE);
        Result<PageInfo<AntiSpiderBlackListVO>> result = antiSpiderBlackListService.list(vo);
        
        List<String> list = new ArrayList<>();
        for(AntiSpiderBlackListVO lvo : result.getObject().getList()) {
            list.add(lvo.getIp());
        }
        
        return new Result<>(ResultCode.SUCCESS, list);
    }
    
    @MethodResourceDesc(name = "获取IP白名单")
    @RequestMapping(value = "/ipWhiteList.do")
    @ResponseBody
    public Result<List<String>> ipWhiteList(HttpServletRequest request, HttpServletResponse response) {
        
        AntiSpiderWhiteListSearchVO vo = new AntiSpiderWhiteListSearchVO();
        vo.setSize(Integer.MAX_VALUE);
        Result<PageInfo<AntiSpiderWhiteListVO>> result = antiSpiderWhiteListService.list(vo);
        
        List<String> list = new ArrayList<>();
        for(AntiSpiderWhiteListVO lvo : result.getObject().getList()) {
            list.add(lvo.getIp());
        }
        
        return new Result<>(ResultCode.SUCCESS, list);
    }
}
