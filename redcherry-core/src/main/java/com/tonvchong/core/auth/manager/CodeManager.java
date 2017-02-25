package com.tonvchong.core.auth.manager;

/**
 * 
 * Copyright © 2016全球蜂.
 * 
 * @Title: CodeManager.java 
 * @Project: RBM
 * @date: 2016年4月2日 下午1:12:21
 * @author: tonvchong
 * @Description: 图形验证码管理
 */
public interface CodeManager {
	
	/**
	 * 缓存Code
	 * @return
	 */
	public String setCode(String key, String code);
	
	/**
	 * 获取Code
	 * @return
	 */
	public String getCode(String key);
	
	/**
	 * 清除Code
	 */
	public void removeCode(String key);
	
}
