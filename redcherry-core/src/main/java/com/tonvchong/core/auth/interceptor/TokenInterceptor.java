package com.tonvchong.core.auth.interceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.tonvchong.core.auth.annotation.Authorization;
import com.tonvchong.core.auth.exception.AuthExceptionCode;
import com.tonvchong.core.auth.manager.TokenManager;
import com.tonvchong.core.auth.support.ThreadTokenHolder;
import com.tonvchong.core.auth.support.ThreadUserHolder;
import com.tonvchong.core.auth.user.AuthUser;
import com.tonvchong.core.constant.GlobalConstant;
import com.tonvchong.core.exception.BusinessException;

/**
 * 
 * Copyright © 2016全球蜂.
 * 
 * @Title: TokenInterceptor.java
 * @Project: RBM
 * @date: 2016年3月4日 下午4:11:29
 * @author: tonvchong
 * @Description: token拦截器
 */
@Slf4j
public class TokenInterceptor extends HandlerInterceptorAdapter {
	@Resource(name="tokenManager")
	private TokenManager tokenManager;

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		// 如果不是映射到方法直接通过
		if (!(handler instanceof HandlerMethod)) {
			return true;
		}		
		//如果方法或控制器上没有注解Authorization，则直接通过
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		if (handlerMethod.getMethodAnnotation(Authorization.class) == null
				&& handlerMethod.getBeanType().getAnnotation(Authorization.class) == null) {
			return true;
		}
		
		//验证token
		String token = this.getTokenFromRequest(request);
		//log.info("token from request:" + token);
		if (StringUtils.isEmpty(token)) {			
			log.info("无效的token");
			throw new BusinessException(AuthExceptionCode.INVALID_ACCESS_TOKEN);
		}
		if(!tokenManager.check(token)) {
			log.info("token已失效");
			throw new BusinessException(AuthExceptionCode.TOKEN_EXPIRED);
		}
		
		ThreadTokenHolder.setToken(token);
		AuthUser user = tokenManager.getUser(token);
		ThreadUserHolder.setUser(user);
		return super.preHandle(request, response, handler);
	}

	private String getTokenFromRequest(HttpServletRequest request) {
		// 默认从header里获取token值
		String token = request.getHeader(GlobalConstant.ACCESS_TOKEN);
		if (StringUtils.isEmpty(token)) {
			// 从请求信息中获取token值
			token = request.getParameter(GlobalConstant.ACCESS_TOKEN);
		}
		return token;
	}
}
