package com.tonvchong.core.util.context;

import org.springframework.context.ApplicationContext;

/**
 * 
 * Copyright © 2016全球蜂.
 * 
 * @Title: ServiceLocator 
 * @Project: RBM
 * @date: 2016年3月1日 下午3:38:22
 * @author: tonvchong
 * @Description: 配合自定义ContextLoaderListener使用的工具类
 */
public class ServiceLocator {

	/**
	 * spring ApplicationContext
	 */
	private static ApplicationContext context;
	
	protected ServiceLocator() { // 不允许实例化，全部使用static函数。
		
	}

	public static Object getBean(String beanName){
		return context.getBean(beanName);
	}
	
	/**
	 * @return the context
	 */
	public static ApplicationContext getContext() {
		return context;
	}
	/**
	 * @param context the context to set
	 */
	public static void setContext(ApplicationContext context) {
		ServiceLocator.context = context;
	}
}

