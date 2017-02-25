package com.tonvchong.core.view.support;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tonvchong.core.exception.BusinessException;
import com.tonvchong.core.exception.ExceptionMessageSource;
import com.tonvchong.core.exception.code.GlobalExceptionCode;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * Copyright © 2016全球蜂.
 * 
 * @Title: BaseController.java
 * @Project: RBM
 * @date: 2016年3月2日 上午9:10:45
 * @author: tonvchong
 * @Description: 控制基类
 */
@Slf4j
@Controller
public abstract class BaseController {

	@Resource(name = "exceptions")
	protected ExceptionMessageSource ems;

	@ExceptionHandler(BusinessException.class)
	@ResponseBody
	public Result handleBusinessException(HttpServletRequest request, Exception ex) {
		log.error("business exeption occured", ex);
		return Result.error(((BusinessException) ex).getCode(), ((BusinessException) ex).getMessage());
	}

	@ExceptionHandler(Exception.class)
	@ResponseBody
	public Result handleAllException(HttpServletRequest request, Exception ex) {
		log.error("exeption occured", ex);
		return Result.error(GlobalExceptionCode.INTERNAL_ERROR.getCode(),
				ems.getExceptionMsg(GlobalExceptionCode.INTERNAL_ERROR.getCode()+"") + ":" + ex.getMessage());
	}
}
