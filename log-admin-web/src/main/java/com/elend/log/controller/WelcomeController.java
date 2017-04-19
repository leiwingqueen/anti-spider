package com.elend.log.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.elend.p2p.BaseController;
import com.elend.p2p.resource.ClassResourceDesc;
import com.elend.p2p.resource.MethodResourceDesc;
import com.elend.p2p.sdk.logic.SucEsbHelper;
import com.elend.log.constant.CommonConstant;
import com.elend.log.helper.MemcacheHelper;
import com.elend.p2p.util.Authencation;
/**
 * 管理平台入口
 * @author liuxianyan
 *
 */
@ClassResourceDesc(firstCate="管理平台入口")
@Controller
@Scope("prototype")
public class WelcomeController extends BaseController  {
	
	private final static Logger logger = LoggerFactory.getLogger(WelcomeController.class);
	
	@Autowired
	private SucEsbHelper sucEsbHelper;

	@Autowired
	private MemcacheHelper memcacheHelper;
	
	@MethodResourceDesc(name = "默认跳转页面")
	@RequestMapping(value = "/welcome.jspx")
	public ModelAndView welcome(HttpServletRequest request,HttpServletResponse response) {
		// 从cookie获取用户信息
		Authencation auth = new Authencation(request, response);
		String url = sucEsbHelper.getWelcomeUrl(CommonConstant.app_id, CommonConstant.app_key, auth.getUserName());
		if (StringUtils.isBlank(url)) {
		    Map<String, String> map = new HashMap<String, String>();
                    map.put("error", "您还没有该系统权限");
                    return new ModelAndView("forward:/error.jsp", map);
		}
		//清除缓存数据
		memcacheHelper.welcome(auth.getUserName());		
		//获取权重最大的跳转地址
		String port = request.getServerPort() == 80 ? "" : ":"+ request.getServerPort();
		String basePath = request.getScheme() + "://" + request.getServerName() + port;
//		List<Domain>  domains = sucEsbHelper.listDomain(ReconciliationConstant.app_id, ReconciliationConstant.app_key);
//		basePath = sucEsbHelper.getDomainsWelcomeUrl(domains, basePath, port);
		String redirect = basePath+url;
		return new ModelAndView(new RedirectView(redirect));
	}
	@MethodResourceDesc(name = "默认首页")
	@RequestMapping(value = "/initTest.jspx")
	public ModelAndView index(HttpServletRequest request,HttpServletResponse response) {
		return new ModelAndView("forward:/WEB-INF/init_test.jsp");
	}
}
