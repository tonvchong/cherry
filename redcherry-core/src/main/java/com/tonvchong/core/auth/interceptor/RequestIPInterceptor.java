package com.tonvchong.core.auth.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.tonvchong.core.auth.annotation.AuthIP;
import com.tonvchong.core.auth.exception.AuthExceptionCode;
import com.tonvchong.core.exception.BusinessException;
import com.tonvchong.core.util.config.CommonConfig;
import com.tonvchong.core.util.http.CusAccessObjectUtil;

/**
 * 
 * Copyright © 2016全球蜂.
 * 
 * @Title: RequestIPInterceptor.java
 * @Project: RBM
 * @date: 2016年3月4日 下午4:11:29
 * @author: tonvchong
 * @Description: IP拦截器
 */
@Slf4j
public class RequestIPInterceptor extends HandlerInterceptorAdapter {
    private static final String allowedIPs = CommonConfig.getString("ALLOWED_IP_LIST"); // 放行的IP列表

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        // 如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        if (handlerMethod.getMethodAnnotation(AuthIP.class) == null
                && handlerMethod.getBeanType().getAnnotation(AuthIP.class) == null) {
            return true;
        }

//        log.info("allowedIPs:" + allowedIPs);
        String ipAddr = CusAccessObjectUtil.getIpAddress(request);
        log.info("ipAddr:" + ipAddr);
        // 如果域名不允许访问，则返回
        if (!checked(ipAddr)) {
            log.info("无效的访问IP：" + ipAddr);
            throw new BusinessException(AuthExceptionCode.INVALID_IP);
        }

        return super.preHandle(request, response, handler);
    }

    private boolean checked(String ipAddr) {
        if ("*".equals(allowedIPs)) {
            return true;
        }

        String[] urlAry = allowedIPs.split(",");
        for (String tmpUrl : urlAry) {
            if (ipAddr.equals(tmpUrl)) {
                return true;
            }
        }
        return false;
    }
}
