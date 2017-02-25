package com.tonvchong.core.auth.interceptor;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.tonvchong.core.auth.annotation.Authorization;
import com.tonvchong.core.auth.exception.AuthExceptionCode;
import com.tonvchong.core.exception.BusinessException;
import com.tonvchong.core.util.security.SignUtils;

/**
 * Copyright © 2016全球蜂.
 * 
 * @Title: SigInterceptor.java
 * @Project: RBM
 * @date: 2016年3月4日 下午4:11:43
 * @author: tonvchong
 * @Description: sig签名拦截器
 */
@Slf4j
public class SigInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        log.info("handler:" + handler);
        // 如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        // 如果方法或控制器上没有注解Authorization，则直接通过
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        if (handlerMethod.getMethodAnnotation(Authorization.class) == null
                && handlerMethod.getBeanType().getAnnotation(Authorization.class) == null) {
            return true;
        }

        HandlerMethod method = (HandlerMethod) handler;
        Object controller = method.getBean();
        log.info("controller:" + controller);

        String reqMethod = request.getMethod();
        log.info("reqMethod:" + reqMethod);

        String contextPath = request.getContextPath();
        log.info("contextPath:" + contextPath);

        String reqUri = request.getRequestURI();
        reqUri = reqUri.replaceFirst(contextPath, "");
        log.info("reqUri:" + reqUri);

        SortedMap<Object, Object> parameters = new TreeMap<Object, Object>();
        parameters.put("method", reqUri);
        String mSign = null;
        String mTimeStamp = null;

        Map<String, String[]> map = request.getParameterMap();
        Set<Entry<String, String[]>> set = map.entrySet();
        Iterator<Entry<String, String[]>> it = set.iterator();
        while (it.hasNext()) {
            Entry<String, String[]> entry = it.next();
            String mkey = entry.getKey();
            String[] vals = entry.getValue();
            if (null != vals && vals.length > 0) {
                String val = entry.getValue()[0];
                parameters.put(mkey, val);
                if ("sign".equals(mkey)) {
                    mSign = val;
                }
                if ("timestamp".equals(mkey)) {
                    mTimeStamp = val;
                }
            }
        }

        // 相同请求5分钟内有效
        if (!String.valueOf(System.currentTimeMillis() / 1000 / (60 * 5)).equals(mTimeStamp)) {
            throw new BusinessException(AuthExceptionCode.REQUEST_TIME_OUT);
        }

        if (!SignUtils.checkResultSign(parameters, mSign)) {
            throw new BusinessException(AuthExceptionCode.SIGN_ERROR);
        }

        return super.preHandle(request, response, handler);
    }

}
