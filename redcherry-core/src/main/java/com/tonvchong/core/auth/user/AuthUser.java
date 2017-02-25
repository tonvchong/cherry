package com.tonvchong.core.auth.user;

import com.tonvchong.core.domain.support.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * Copyright © 2016全球蜂.
 * 
 * @Title: AuthUser.java
 * @Project: RBM
 * @date: 2016年3月6日 上午11:26:48
 * @author: tonvchong
 * @Description: 鉴权用户
 */
@SuppressWarnings("serial")
@Data
@EqualsAndHashCode(callSuper = false)
public class AuthUser extends Entity<Integer> {
	private String userid;
	private String userName;
	private String password;
	private String timestamp; // 时间戳
	private String party; // 第三方
	private String token;
}
