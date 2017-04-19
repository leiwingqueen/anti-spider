package com.elend.log.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.elend.p2p.BaseController;
import com.elend.p2p.Result;
import com.elend.p2p.constant.ResultCode;
import com.elend.p2p.resource.ClassResourceDesc;
import com.elend.p2p.resource.MethodResourceDesc;
import com.elend.p2p.sdk.logic.CommonHelper;
import com.elend.p2p.sdk.logic.SucEsbHelper;
import com.elend.p2p.sdk.vo.AppVO;
import com.elend.p2p.sdk.vo.MenuVO;
import com.elend.p2p.sdk.vo.User;
import com.elend.log.constant.CommonConstant;
import com.elend.p2p.util.Authencation;

@ClassResourceDesc(firstCate = "公共接口")
@Controller
@Scope("prototype")
public class CommonController extends BaseController {
    @Autowired
    private SucEsbHelper sucEsbHelper;

    @Autowired
    private CommonHelper commonHelper;

    // @Autowired
    // private KeyValueSettingService keyValueSettingService;

    @MethodResourceDesc(name = "获取用户信息")
    @RequestMapping(value = "/common/loadUserInfo.do", method = RequestMethod.GET)
    @ResponseBody
    public Result<User> loadUserInfo(HttpServletRequest request,
            HttpServletResponse response) {
        // 从cookie获取用户信息
        Authencation auth = new Authencation(request, response);
        User user = sucEsbHelper.listOneUser(CommonConstant.app_id,
                                             CommonConstant.app_key,
                                             auth.getUserName());
        Result<User> result = new Result<User>();
        result.setCode(ResultCode.SUCCESS);
        result.setObject(user);
        return result;
    }

    @MethodResourceDesc(name = "获取用户App列表信息")
    @RequestMapping(value = "/common/listUserApp.do", method = RequestMethod.GET)
    @ResponseBody
    public Result<List<AppVO>> listUserApp(HttpServletRequest request,
            HttpServletResponse response) {
        // 从cookie获取用户信息
        Authencation auth = new Authencation(request, response);
        List<AppVO> apps = sucEsbHelper.listUserApp(CommonConstant.app_id,
                                                    CommonConstant.app_key,
                                                    auth.getUserName());
        sucEsbHelper.filter(apps, auth.getUserName(), getRealIp(request));
        Result<List<AppVO>> result = new Result<List<AppVO>>();
        result.setCode(ResultCode.SUCCESS);
        result.setObject(apps);
        return result;
    }

    @MethodResourceDesc(name = "获取服务器IP地址")
    @RequestMapping(value = "/common/loadServerIp.do", method = RequestMethod.GET)
    @ResponseBody
    public Result<List<String>> loadServerIp() {
        List<String> ips = commonHelper.getCurServerIps();
        Result<List<String>> result = new Result<List<String>>();
        result.setCode(ResultCode.SUCCESS);
        result.setObject(ips);
        return result;
    }

    @MethodResourceDesc(name = "获取用户菜单权限")
    @RequestMapping(value = "/common/menu.do", method = RequestMethod.GET)
    @ResponseBody
    public Result<List<MenuVO>> menu(HttpServletRequest request,
            HttpServletResponse response) {
        // 设置cookie
        Authencation auth = new Authencation(request, response);
        List<MenuVO> menus = sucEsbHelper.listUserMenu(CommonConstant.app_id,
                                                       CommonConstant.app_key,
                                                       auth.getUserName());

        Result<List<MenuVO>> result = new Result<List<MenuVO>>();
        result.setCode(ResultCode.SUCCESS);
        result.setObject(menus);
        return result;
    }

    // @MethodResourceDesc(name = "获取系统参数配置")
    // @RequestMapping(value = "/common/getKeyValueSetting.do", method =
    // RequestMethod.GET)
    // @ResponseBody
    // public Result getKeyValueSetting(HttpServletRequest
    // request,HttpServletResponse response,String dic_key){
    // KeyValueSetting kv = keyValueSettingService.get(dic_key);
    // Result result = new Result();
    // result.setCode(ResultCode.SUCCESS);
    // result.setObject(kv);
    // return result;
    // }

    @MethodResourceDesc(name = "用户退出权限")
    @RequestMapping(value = "/admin/logout.do", method = RequestMethod.GET)
    public ModelAndView logout(HttpServletRequest request,
            HttpServletResponse response) {
        // deleteCookie(request,response);
        return new ModelAndView(new RedirectView("/welcome.jspx"));
    }

    @MethodResourceDesc(name = "获取用户App列表信息")
    @RequestMapping(value = "/common/listAppUser.do", method = RequestMethod.GET)
    @ResponseBody
    public Result<List<User>> listAppUser(HttpServletRequest request,
            HttpServletResponse response) {
        // 从cookie获取用户信息
        Result<List<User>> result = new Result<List<User>>();
        result.setCode(ResultCode.FAILURE);
        List<User> user = sucEsbHelper.listAppUser(CommonConstant.app_id,
                                                   CommonConstant.app_key);
        if (user == null) {
            result.setMessage("获取用户信息出错");
            return result;
        }
        result.setCode(ResultCode.SUCCESS);
        result.setObject(user);
        return result;
    }
}
