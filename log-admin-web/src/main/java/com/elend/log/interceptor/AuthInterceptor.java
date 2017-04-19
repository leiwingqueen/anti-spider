package com.elend.log.interceptor;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.mybatis.caches.memcached.MemcachedCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UrlPathHelper;

import com.elend.p2p.ResponseUtils;
import com.elend.p2p.Result;
import com.elend.p2p.constant.ResultCode;
import com.elend.p2p.context.AdminInfoContext;
import com.elend.p2p.gson.JSONUtils;
import com.elend.p2p.sdk.logic.SucEsbHelper;
import com.elend.p2p.sdk.vo.User;
import com.elend.log.constant.CacheKeyConstant;
import com.elend.log.constant.CommonConstant;
import com.elend.log.service.RecMemcacheService;
import com.elend.p2p.util.Authencation;
import com.google.gson.reflect.TypeToken;

/**
 * 权限验证拦截器
 * 
 * @author liuxianyan
 */
public class AuthInterceptor implements HandlerInterceptor {

    private final static Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);
    
    private String[] excludeUrls;

    private String loginUrl;

    private String errorUrl;

    @Autowired
    private SucEsbHelper sucEsbHelper;

    @Autowired
    private RecMemcacheService recMemcacheService;

    public String[] getExcludeUrls() {
        return excludeUrls;
    }

    public void setExcludeUrls(String[] excludeUrls) {
        this.excludeUrls = excludeUrls;
    }

    public String getLoginUrl() {
        return loginUrl;
    }

    public void setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
    }

    public String getErrorUrl() {
        return errorUrl;
    }

    public void setErrorUrl(String errorUrl) {
        this.errorUrl = errorUrl;
    }

    public void afterCompletion(HttpServletRequest request,
            HttpServletResponse response, Object arg2, Exception e)
            throws Exception {
        // TODO Auto-generated method stub
    }

    public void postHandle(HttpServletRequest request,
            HttpServletResponse response, Object arg2, ModelAndView arg3)
            throws Exception {
        // TODO Auto-generated method stub
    }

    public boolean preHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler) throws Exception {
        StringBuilder buff = new StringBuilder();
     
        buff.append("[privilege-interceptor]");

        String uri = getURI(request);
        if (exclude(uri))
            return true;
        Result result = new Result();
        result.setCode(ResultCode.FAILURE);

        // 验证用户是否登录
        Authencation auth = new Authencation(request, null);
        buff.append(",auth.UserName()=<").append(auth.getUserName()).append(">");
        // 如果用户没有登录
        if (!auth.isValidUser()) {
            buff.append("用户没有登录");
            result.setMessage("用户没有登录.");
            logger.error(buff.toString());
            dealHandle(request, response, result, true);
            return false;
        }
        MemcachedCache cache = new MemcachedCache(
                                                  CacheKeyConstant.MEMCACHED_PRIVILEGE_ID);
        String key = CacheKeyConstant.PRIVILEGE_RESOURCE_KEY_PREFIX
                + auth.getUserName();
        // 如果是第一次登录，则把缓存的数据清理掉
        String firstRequest = Authencation.getFirstRequest(request, response);
        if (firstRequest == null) {
            //logger.info("Authencation.getFirstRequest(request, response) is null and clear cache");
            recMemcacheService.firstRequest(auth.getUserName());
            Authencation.setFirstRequest(request, response);
        } else {
            //logger.info("Authencation.getFirstRequest(request, response) is not null {}", firstRequest);
        }
        List<String> uris = (List<String>) cache.getObject(key);
        // 有时候可以取到KEY，但是取出来的数据为空的
        
        if (uris == null || uris.size() == 0) {
            uris = sucEsbHelper.listUserResource(CommonConstant.app_id,
                                                 CommonConstant.app_key,
                                                 auth.getUserName());
            cache.putObject(key, uris);
            buff.append("appId=<").append(CommonConstant.app_id).append(">");
            buff.append(",EsbConstant.app_key=<").append(CommonConstant.app_key).append(">");
            logger.info(buff.toString());
        }
        //logger.info(uris+"");
        
        if (uri.equals("/")||uris.contains(uri)) {//由于要增加restful的支持在web.xml里面增加一个'/'的url-mapping，因此"/"也会进入到拦截器里面，
            if (auth.getOriNickName() == null) {
                User user = sucEsbHelper.listOneUser(CommonConstant.app_id,
                                                     CommonConstant.app_key,
                                                     auth.getUserName());
                auth.saveUserName(user.getUser_id(), user.getUser_name(),
                                  user.getNick_name(),false);
                AdminInfoContext.clear();
                AdminInfoContext.setUserId(auth.getUserId());
                AdminInfoContext.setUserName(auth.getUserName());
                AdminInfoContext.setNickName(user.getNick_name());
            } else {
                AdminInfoContext.clear();
                AdminInfoContext.setUserId(auth.getUserId());
                AdminInfoContext.setUserName(auth.getUserName());
                AdminInfoContext.setNickName(auth.getNickName());
            }
            // log.info("&&&" + auth.getUserName() + "@" + uri+"&&&");
            return true;
        }

        result.setMessage("您没有" + uri + "的访问权限.");
        dealHandle(request, response, result, false);

        return false;
    }

    /**
     * 处理返回信息， 如果是JSPX页面的请求，则转到错误信息; 如果是AJAX请求，则返回JSON数据
     * 
     * @param request
     * @param response
     * @param result
     * @throws IOException
     * @throws ServletException
     */
    public void dealHandle(HttpServletRequest request,
            HttpServletResponse response, Result result, boolean login)
            throws ServletException, IOException {

        String uri = request.getRequestURI();
        // 页面请求
        if (uri.equals("/")||uri.lastIndexOf(".jspx") != -1) {
            request.setAttribute("result", result);
            if (login) {
                response.sendRedirect(getLoginUrl(request));
            } else {
                request.setAttribute("error", result.getMessage());
                request.getRequestDispatcher(errorUrl).forward(request,
                                                               response);
            }
        }
        // AJAX请求
        else {
            Type targetType = new TypeToken<Result>() {
            }.getType();
            String json = JSONUtils.toJson(result, targetType, false);
            ResponseUtils.renderXml(response, json);
        }
    }

    /**
     * 获取登录地址
     * 
     * @param request
     * @return
     */
    private String getLoginUrl(HttpServletRequest request) {
        StringBuilder buff = new StringBuilder();
        if (loginUrl.startsWith("/")) {
            String ctx = request.getContextPath();
            if (!StringUtils.isBlank(ctx)) {
                buff.append(ctx);
            }
        }
        String port = request.getServerPort() == 80 ? "" : ":"
                + request.getServerPort();
        String basePath = request.getScheme() + "://"
                + request.getServerName() + port;
        buff.append(loginUrl).append("?");

        String callback = basePath + request.getRequestURI();
        if (request.getQueryString() != null) {
            callback = callback + "?" + request.getQueryString();
        }
        try {
            callback = URLEncoder.encode(callback, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        buff.append("callback").append("=").append(callback);

        return buff.toString();
    }

    /**
     * 获取系统资源地址
     * 
     * @param request
     */
    private String getURI(HttpServletRequest request) {
        UrlPathHelper helper = new UrlPathHelper();
        String uri = helper.getOriginatingRequestUri(request);
        String ctxPath = helper.getOriginatingContextPath(request);
        return uri.replaceFirst(ctxPath, "");
    }

    /**
     * 不需要权限控制URL
     * 
     * @param uri
     * @return
     */
    private boolean exclude(String uri) {
        if (excludeUrls != null) {
            for (String exc : excludeUrls) {
                if (exc.equals(uri)) {
                    return true;
                }else{//增加支持正则表达式 add by liyongquan 2014/12/6
                    Pattern r = Pattern.compile(exc);
                    Matcher m = r.matcher(uri);
                    if(m.find())return true;
                }
            }
        }
        return false;
    }
}
