package com.tonvchong.core.auth.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.tonvchong.core.auth.exception.AuthExceptionCode;
import com.tonvchong.core.exception.BusinessException;
import com.tonvchong.core.util.config.CommonConfig;

/**
 * 
 * Copyright © 2016全球蜂.
 * 
 * @Title: UrlDomainInterceptor.java
 * @Project: RBM
 * @date: 2016年3月4日 下午4:11:29
 * @author: tonvchong
 * @Description: Domain拦截器
 */
@Slf4j
public class UrlDomainInterceptor extends HandlerInterceptorAdapter {
	private static final String allowedUrls = CommonConfig
			.getString("ALLOWED_URL_LIST"); // 放行的URL列表
	private static final boolean allowedCrossDomain = CommonConfig
			.getBoolean("ALLOWED_CROSS_DOMAIN");

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		// 允许跨域和ACCESS_TOKEN,API_VERSION头部信息
		if (allowedCrossDomain) {
			response.addHeader("Access-Control-Allow-Origin", "*");
			response.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE, HEAD");
			response.addHeader(
					"Access-Control-Allow-Headers",
					"API_VERSION,ACCESS_TOKEN,X-PINGOTHER, Origin, X-Requested-With, Content-Type, Accept");
			response.addHeader("Access-Control-Max-Age", "1728000");
		}
		
		// 如果不是映射到方法直接通过
		if (!(handler instanceof HandlerMethod)) {
			return true;
		}

		log.debug("allowed urls:" + allowedUrls);
		String domainUrl = getDomainUrl(request);
		log.debug("domain url:" + domainUrl);
		// 如果域名不允许访问，则返回
		if (!checked(domainUrl)) {
			log.info("无效的访问域名：" + domainUrl);
			throw new BusinessException(AuthExceptionCode.INVALID_DOMAIN_URL);
		}

		return super.preHandle(request, response, handler);
	}

	private String getDomainUrl(HttpServletRequest request) {
		StringBuffer requestUrl = request.getRequestURL();

		// String domainUrl = request.getScheme() + "://" +
		// request.getServerName() + ":" + request.getServerPort() + "/";

		String domainUrl = requestUrl
				.delete(requestUrl.length() - request.getRequestURI().length(),
						requestUrl.length()).append("/").toString();
		return domainUrl;
	}

	private boolean checked(String domainUrl) {
		if ("*".equals(allowedUrls)) {
			return true;
		}

		String[] urlAry = allowedUrls.split(",");
		for (String tmpUrl : urlAry) {
			if (domainUrl.equals(tmpUrl)) {
				return true;
			}
		}
		return false;
	}
}
