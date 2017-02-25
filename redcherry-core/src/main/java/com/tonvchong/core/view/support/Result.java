package com.tonvchong.core.view.support;

import com.tonvchong.core.exception.code.GlobalExceptionCode;

import lombok.Data;

/**
 * 
 * Copyright © 2016全球蜂.
 * 
 * @Title: Result
 * @Project: RBM
 * @date: 2016年3月1日 下午5:10:20
 * @author: tonvchong
 * @Description: 返回对象
 */

@Data
public class Result {
	private long code;

	private String message;

	private boolean success;

	private Object data;

	public Result() {
		
	}
	
	private Result(boolean success, long code, String message, Object content) {
		super();
		this.success = success;
		this.code = code;
		this.message = message;
		this.data = content;
	}

	public static Result ok(Object content) {
		return new Result(true, GlobalExceptionCode.SUCCESS.getCode(), "success", content);
	}

	public static Result ok(String message,Object content) {
		return new Result(true, GlobalExceptionCode.SUCCESS.getCode(), message, content);
	}

	public static Result ok() {
		return new Result(true, GlobalExceptionCode.SUCCESS.getCode(), "success", null);
	}

	public static Result error(long code, String message) {
		return new Result(false, code, message, null);
	}

	public static Result error(String message) {
		return new Result(false, GlobalExceptionCode.INTERNAL_ERROR.getCode(), message, null);
	}
	
	public static Result error(long code, String message,Object content) {
		return new Result(false, GlobalExceptionCode.INTERNAL_ERROR.getCode(), message, content);
	}
	
}
