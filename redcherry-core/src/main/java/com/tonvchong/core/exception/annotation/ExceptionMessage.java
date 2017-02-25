package com.tonvchong.core.exception.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.stereotype.Component;

/**
 * 
 * Copyright © 2016全球蜂.
 * 
 * @Title: ExceptionMessage.java 
 * @Project: RBM
 * @date: 2016年3月2日 下午4:35:29
 * @author: tonvchong
 * @Description: 异常消息注解
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface ExceptionMessage {
	String value() default "";
}
