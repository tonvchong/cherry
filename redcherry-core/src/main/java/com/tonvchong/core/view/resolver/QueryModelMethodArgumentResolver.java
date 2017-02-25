package com.tonvchong.core.view.resolver;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import lombok.extern.slf4j.Slf4j;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.tonvchong.core.dao.support.ModelSetup;
import com.tonvchong.core.dao.support.mybatis.MyBatisModelSetup;
import com.tonvchong.core.view.annotation.QueryModel;

/**
 * 
 * 
 * Copyright © 2016全球蜂.
 * 
 * @Title: QueryModelMethodArgumentResolver.java
 * @Project: RBM
 * @date: 2016年3月5日 上午11:45:56
 * @author: tonvchong
 * @Description: 注入request参数到查询对象
 */
@Slf4j
public class QueryModelMethodArgumentResolver implements
		HandlerMethodArgumentResolver {
	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		if (parameter.hasParameterAnnotation(QueryModel.class)
				&& ModelSetup.class.isAssignableFrom(parameter
						.getParameterType())) {
			return true;
		}
		return false;
	}

	@Override
	public Object resolveArgument(MethodParameter parameter,
			ModelAndViewContainer mavContainer, NativeWebRequest webRequest,
			WebDataBinderFactory binderFactory) throws Exception {
		final Map<String, String[]> params = webRequest.getParameterMap();
		Map<String, Object> parameters = new HashMap<String, Object>();
		Set<String> keys = params.keySet();
		for (String key : keys) {
			String[] value = (String[]) params.get(key);
			if (value != null && value.length == 1) {
				parameters.put(key, value[0]);
//				parameters.put(key, new String(value[0].getBytes(),"UTF-8"));
			}
		}
		MyBatisModelSetup model = null;
		// 如果参数类型是mybatis
		if (parameter.getParameterType().isAssignableFrom(
				MyBatisModelSetup.class)) {
			model = new MyBatisModelSetup();
			model.setup(parameters);
		} else {
			return null;
		}
		return model;
	}

}