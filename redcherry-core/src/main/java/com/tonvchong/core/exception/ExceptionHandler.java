package com.tonvchong.core.exception;

import com.tonvchong.core.exception.code.ExceptionCode;
import com.tonvchong.core.util.context.ServiceLocator;

public class ExceptionHandler {
	private static ExceptionMessageSource ems;

	public static ExceptionMessageSource getEms() {
		if (ems == null)
			ems = (ExceptionMessageSource) ServiceLocator.getBean("exceptions");
		return ems;
	}

	public static String getExceptionMessage(long key) {
		return getEms().getExceptionMsg(key + "");
	}

	public static String getExceptionMessage(ExceptionCode ec) {
		return getExceptionMessage(ec.getCode());
	}

	public static String getExceptionMessage(ExceptionCode ec, Object[] args) {
		return getEms().getExceptionMsg(ec.getCode() + "", args, "");
	}
}
