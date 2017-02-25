package com.tonvchong.core.auth.support;

import org.springframework.util.Assert;

import com.tonvchong.core.auth.user.AuthUser;

/**
 * 
 * Copyright © 2016全球蜂.
 * 
 * @Title: ThreadTokenHolder.java
 * @Project: RBM
 * @date: 2016年3月4日 下午6:17:10
 * @author: tonvchong
 * @Description: 保存当前用户到线程中
 */
public class ThreadUserHolder {
	// 拦截器是单例，线程不安全，使用ThreadLocal存储token
	private static ThreadLocal<AuthUser> contextHolder = new ThreadLocal<AuthUser>();

	public static void clearContext() {
		contextHolder.set(null);
		contextHolder.remove();
	}

	public static AuthUser getUser() {
		return (AuthUser) contextHolder.get();
	}

	public static void setUser(AuthUser user) {
		Assert.notNull(user, "Only non-null user instances are permitted");
		contextHolder.set(user);
	}
}
