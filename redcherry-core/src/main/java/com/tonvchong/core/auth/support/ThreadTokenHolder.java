package com.tonvchong.core.auth.support;

import org.springframework.util.Assert;

/**
 * 
 * Copyright © 2016全球蜂.
 * 
 * @Title: ThreadTokenHolder.java 
 * @Project: RBM
 * @date: 2016年3月4日 下午6:17:10
 * @author: tonvchong
 * @Description: 保存当前token到线程中
 */
public class ThreadTokenHolder {
	// 拦截器是单例，线程不安全，使用ThreadLocal存储token
	private static ThreadLocal<String> contextHolder = new ThreadLocal<String>();

	public static void clearContext() {
		contextHolder.set(null);
	}

	public static String getToken() {
		return (String) contextHolder.get();
	}

	public static void setToken(String token) {
		Assert.notNull(token,
				"Only non-null token instances are permitted");
		contextHolder.set(token);
	}
}
