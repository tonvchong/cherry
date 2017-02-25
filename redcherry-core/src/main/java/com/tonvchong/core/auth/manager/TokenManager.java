package com.tonvchong.core.auth.manager;

import com.tonvchong.core.auth.user.AuthUser;



/**
 * 
 * Copyright © 2016全球蜂.
 * 
 * @Title: TokenManager.java 
 * @Project: RBM
 * @date: 2016年3月3日 下午5:12:21
 * @author: tonvchong
 * @Description: token管理
 */
public interface TokenManager {
	/**
	 * 构造token
	 * @param uid
	 * @return
	 */
	public String generateToken(AuthUser user);
	
	/**
	 * 获取TOKEN
	 * @param uid
	 * @return
	 */
	public String getToken(String uid);
	
	/**
	 * 获取用户
	 * @param token
	 * @return 
	 */
	public AuthUser getUser(String token);
	
	/**
	 * 检查token是否有效
	 * @param token
	 * @return
	 */
	public boolean check(String token);
	
	
	/**
	 * 清除token
	 * @param token
	 */
	public void removeToken(String token);
	
	public void removeTokenByUid(String uid);
}
