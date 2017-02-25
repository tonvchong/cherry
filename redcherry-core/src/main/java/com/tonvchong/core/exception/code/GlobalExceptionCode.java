package com.tonvchong.core.exception.code;

import com.tonvchong.core.exception.code.ExceptionCode;

/**
 * 
 * Copyright © 2016全球蜂.
 * 
 * @Title: ResultStatus.java 
 * @Project: RBM
 * @date: 2016年3月2日 上午9:11:44
 * @author: tonvchong
 * @Description: 全局异常代码
 */
public enum GlobalExceptionCode implements ExceptionCode {
	INTERNAL_ERROR(-1L),
	SUCCESS(100L);
	
	private long code;
	
	GlobalExceptionCode(long code) {
        this.code = code;
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }
}
