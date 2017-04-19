package com.elend.log.controller;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.elend.p2p.BaseController;
import com.elend.p2p.PageInfo;
import com.elend.p2p.Result;
import com.elend.p2p.constant.ResultCode;
import com.elend.p2p.context.AdminInfoContext;
import com.elend.p2p.resource.ClassResourceDesc;
import com.elend.p2p.resource.MethodResourceDesc;
import com.elend.p2p.util.DateUtil;
import com.elend.spider.common.service.AntiSpiderBlackListService;
import com.elend.spider.common.service.AntiSpiderLogService;
import com.elend.spider.common.service.AntiSpiderWhiteListService;
import com.elend.spider.common.vo.AntiSpiderBlackListSearchVO;
import com.elend.spider.common.vo.AntiSpiderBlackListVO;
import com.elend.spider.common.vo.AntiSpiderLogSearchVO;
import com.elend.spider.common.vo.AntiSpiderLogVO;
import com.elend.spider.common.vo.AntiSpiderWhiteListSearchVO;
import com.elend.spider.common.vo.AntiSpiderWhiteListVO;

@ClassResourceDesc(firstCate = "防爬虫相关")
@Controller
public class SpiderController extends BaseController {
    
    @Autowired
    private AntiSpiderWhiteListService antiSpiderWhiteListService;
    
    @Autowired
    private AntiSpiderBlackListService antiSpiderBlackListService;
    
    @Autowired
    private AntiSpiderLogService antiSpiderLogService;
    
    @MethodResourceDesc(name = "黑名单查询")
    @RequestMapping(value = "/spider/blackList.do")
    @ResponseBody
    public Result<PageInfo<AntiSpiderBlackListVO>>  blackList(AntiSpiderBlackListSearchVO svo){
        return antiSpiderBlackListService.list(svo);
    }

    /*********************view*********************/
    
    @MethodResourceDesc(name = "黑名单管理页面")
    @RequestMapping(value = "/spider/blackList.jspx")
    public ModelAndView blackListPage(){
        return new ModelAndView("forward:/WEB-INF/spider/blackList.jsp");
    }
    
    @MethodResourceDesc(name = "删除黑名单")
    @RequestMapping(value = "/spider/blackDel.do")
    @ResponseBody
    public Result<String>  blackDel(String ip, String remark){
        
        if(StringUtils.isBlank(ip) || StringUtils.isBlank(remark)) {
            return new Result<>(ResultCode.FAILURE, null, "请输入IP和备注");
        }
        
        String userName = AdminInfoContext.getUserName();
        
        return antiSpiderBlackListService.deleteByIp(ip, remark, userName);
    }
    
    @MethodResourceDesc(name = "添加黑名单")
    @RequestMapping(value = "/spider/blackAdd.do")
    @ResponseBody
    public Result<AntiSpiderBlackListVO>  blackAdd(String ip, String remark){
        
        if(StringUtils.isBlank(ip) || StringUtils.isBlank(remark)) {
            return new Result<>(ResultCode.FAILURE, null, "请输入IP和备注");
        }
        
        String userName = AdminInfoContext.getUserName();
        AntiSpiderBlackListVO vo = new AntiSpiderBlackListVO();
        vo.setCreateAdmin(userName);
        vo.setCreateTime(new Date());
        vo.setExpireTime(DateUtil.getDate(new Date(), 2, 0, 0, 0));
        vo.setIp(ip);
        vo.setUpdateAdmin(userName);
        vo.setUpdateTime(new Date());
        
        return antiSpiderBlackListService.save(vo, remark);
    }
    
    
    @MethodResourceDesc(name = "白名单查询")
    @RequestMapping(value = "/spider/whiteList.do")
    @ResponseBody
    public Result<PageInfo<AntiSpiderWhiteListVO>>  whiteList(AntiSpiderWhiteListSearchVO svo){
        return antiSpiderWhiteListService.list(svo);
    }

    
    @MethodResourceDesc(name = "白名单管理页面")
    @RequestMapping(value = "/spider/whiteList.jspx")
    public ModelAndView whiteListPage(){
        return new ModelAndView("forward:/WEB-INF/spider/whiteList.jsp");
    }
    
    @MethodResourceDesc(name = "删除白名单")
    @RequestMapping(value = "/spider/whiteDel.do")
    @ResponseBody
    public Result<String>  whiteDel(String ip, String remark){
        
        if(StringUtils.isBlank(ip) || StringUtils.isBlank(remark)) {
            return new Result<>(ResultCode.FAILURE, null, "请输入IP和备注");
        }
        
        String userName = AdminInfoContext.getUserName();
        
        return antiSpiderWhiteListService.deleteByIp(ip, remark, userName);
    }
    
    @MethodResourceDesc(name = "添加白名单")
    @RequestMapping(value = "/spider/whiteAdd.do")
    @ResponseBody
    public Result<AntiSpiderWhiteListVO>  whiteAdd(String ip, String remark){
        
        if(StringUtils.isBlank(ip) || StringUtils.isBlank(remark)) {
            return new Result<>(ResultCode.FAILURE, null, "请输入IP和备注");
        }
        
        String userName = AdminInfoContext.getUserName();
        AntiSpiderWhiteListVO vo = new AntiSpiderWhiteListVO();
        vo.setCreateAdmin(userName);
        vo.setCreateTime(new Date());
        vo.setIp(ip);
        
        return antiSpiderWhiteListService.save(vo, remark);
    }
    
    

    @MethodResourceDesc(name = "黑白名单日志查询")
    @RequestMapping(value = "/spider/logList.jspx")
    public ModelAndView logListPage(){
        return new ModelAndView("forward:/WEB-INF/spider/logList.jsp");
    }
    
    @MethodResourceDesc(name = "黑白名单日志数据")
    @RequestMapping(value = "/spider/logList.do")
    @ResponseBody
    public Result<PageInfo<AntiSpiderLogVO>>  logList(AntiSpiderLogSearchVO svo){
        return antiSpiderLogService.list(svo);
    }
}
