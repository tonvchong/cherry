package com.tonvchong.core.exception;

import com.tonvchong.core.exception.code.ExceptionCode;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * Copyright © 2016全球蜂.
 * 
 * @Title: BusinessException.java
 * @Project: RBM
 * @date: 2016年3月2日 上午9:11:24
 * @author: tonvchong
 * @Description: 业务异常
 */
@SuppressWarnings("serial")
@Data
@EqualsAndHashCode(callSuper = false)
public class BusinessException extends RuntimeException {
	private long code;

	private String message;

	public BusinessException(long code, String message) {
		super(message);
		this.code = code;
		this.message = message;
	}

	public BusinessException(long code) {
		super();
		this.code = code;
		this.message = ExceptionHandler.getExceptionMessage(code);
	}

	public BusinessException(ExceptionCode ec) {
		super();
		String msg = ExceptionHandler.getExceptionMessage(ec);
		this.code = ec.getCode();
		this.message = msg;
	}
	
	public BusinessException(ExceptionCode ec,Object[] args) {
		String msg = ExceptionHandler.getExceptionMessage(ec,args);
		this.code = ec.getCode();
		this.message = msg;
	}
}
