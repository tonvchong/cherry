package com.tonvchong.core.util.config;

import java.util.ResourceBundle;

/**
 * 
 * Copyright © 2016全球蜂.
 * 
 * @Title: CommonConfig
 * @Project: RBM
 * @date: 2016年3月2日 上午9:10:04
 * @author: tonvchong
 * @Description: common.properties配置文件
 */
public class CommonConfig {

	private final static String _NAME = "common";

	private static ResourceBundle _BUNDLE = ResourceBundle.getBundle(_NAME);

	public static String getString(String key) {
		if (key != null) {
			return _BUNDLE.getString(key);
		}
		return null;
	}
	
	public static boolean getBoolean(String key) {
		String s = getString(key);
		if(key==null || key.trim().length()==0) {
			return false;
		}
		return Boolean.parseBoolean(s);
	}
	
	public static int getInt(String key) {
		if (key != null) {
			return Integer.valueOf(getString(key));
		}
		return 0;
	}

	public static void main(String args[]) {
		System.out.println(CommonConfig.getBoolean("ALLOWED_CROSS_DOMAIN"));
	}
}
