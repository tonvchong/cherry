package com.tonvchong.core.exception;

import java.util.Locale;

import org.springframework.context.support.ResourceBundleMessageSource;

import com.tonvchong.core.exception.annotation.ExceptionMessage;

@ExceptionMessage("exceptions")
public class ExceptionMessageSource extends ResourceBundleMessageSource {
	public ExceptionMessageSource() {
		this.setBasename("exceptions");
		this.setDefaultEncoding("UTF-8");
	}

	public String getExceptionMsg(String code, Object[] args,
			String defaultMessage) {
		return this.getMessage(code, args, defaultMessage, Locale.CHINA);
	}
	
	public String getExceptionMsg(String code) {
		return this.getExceptionMsg(code, null, "");
	}
}
