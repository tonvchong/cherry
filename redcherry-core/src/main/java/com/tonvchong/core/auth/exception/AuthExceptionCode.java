package com.tonvchong.core.auth.exception;

import com.tonvchong.core.exception.code.ExceptionCode;

/**
 * 
 * Copyright © 2016全球蜂.
 * 
 * @Title: ResultStatus.java 
 * @Project: RBM
 * @date: 2016年3月2日 上午9:11:44
 * @author: tonvchong
 * @Description: 鉴权异常代码
 */
public enum AuthExceptionCode implements ExceptionCode {
	INVALID_DOMAIN_URL(-2L),
	INVALID_ACCESS_TOKEN(-3L),
	TOKEN_EXPIRED(-4L),
	NO_PERMISSION(-5L),
	REQUEST_TIME_OUT(-6L),
	SIGN_ERROR(-7L),
	INVALID_IP(-8L);
	
	private long code;
	
	AuthExceptionCode(long code) {
        this.code = code;
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }
}
