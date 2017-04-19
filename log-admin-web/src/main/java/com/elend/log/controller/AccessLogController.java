package com.elend.log.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.elend.log.facade.AccessLogFacade;
import com.elend.log.vo.AccessLogSearchVO;
import com.elend.log.vo.AccessLogVO;
import com.elend.p2p.BaseController;
import com.elend.p2p.PageInfo;
import com.elend.p2p.Result;
import com.elend.p2p.resource.ClassResourceDesc;
import com.elend.p2p.resource.MethodResourceDesc;

@ClassResourceDesc(firstCate = "系统日志后台管理")
@Controller
@Scope("prototype")
public class AccessLogController extends BaseController {
    
    @Autowired
    private AccessLogFacade accessLogFacade;
    
    @MethodResourceDesc(name = "查询系统日志")
    @RequestMapping(value = "/accessLog/list.do")
    @ResponseBody
    public Result<PageInfo<AccessLogVO>>  list(AccessLogSearchVO svo){
        return accessLogFacade.list(svo);
    }

    /*********************view*********************/
    
    @MethodResourceDesc(name = "系统日志后台页面")
    @RequestMapping(value = "/accessLog/accessLog.jspx")
    public ModelAndView index(){
        return new ModelAndView("forward:/WEB-INF/accessLog/accessLog.jsp");
    }
}
