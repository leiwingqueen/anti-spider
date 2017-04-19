package com.elend.spider.api.interceptor;

import java.lang.reflect.Type;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.elend.p2p.BaseController;
import com.elend.p2p.ResponseUtils;
import com.elend.p2p.Result;
import com.elend.p2p.constant.ResultCode;
import com.elend.p2p.gson.JSONUtils;
import com.elend.spider.api.util.PropHelper;
import com.google.gson.reflect.TypeToken;

/**
 * ip白名单访问
 * @author mgt
 * @date 2016年8月24日
 *
 */
@Component("ipAccessInterceptor")
public class IpAccessInterceptor implements HandlerInterceptor {


    private final static Logger logger = LoggerFactory.getLogger(IpAccessInterceptor.class);

    @Autowired
    private PropHelper propHelper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String[] accessIps = propHelper.getAccessIps();
        String ip = BaseController.getRealIp(request);
        for(String aip : accessIps) {
            if(StringUtils.trimToEmpty(aip).equals(ip)) {
                return true;
            }
        }
        
        logger.error("ip:{}, 不在白名单当中，尝试访问url:{}", ip, request.getRequestURI());
        
        Type targetType = new TypeToken<Result<String>>() { }.getType();
        Result<String> result = new Result<>(ResultCode.FAILURE, null, "禁止访问");
        String json = JSONUtils.toJson(result, targetType, false);
        ResponseUtils.renderJson(response, json);
        
        return false;
    }


    @Override
    public void postHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
    }


    @Override
    public void afterCompletion(HttpServletRequest request,
            HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
    }
}
