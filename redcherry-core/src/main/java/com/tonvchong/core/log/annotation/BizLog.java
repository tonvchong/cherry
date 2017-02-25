package com.tonvchong.core.log.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 业务日志注解
 * 
 * Copyright © 2016全球蜂.
 * 
 * @Title: BizLog.java
 * @Project: RBM
 * @date: 2016年4月4日 上午12:41:36
 * @author: tonvchong
 * @Description: TODO
 */
@Target({ElementType. METHOD})  
@Retention(RetentionPolicy.RUNTIME )  
@Documented
public @interface BizLog {
	String moduleName(); //业务模块
	
	String actionName(); //业务动作
	
	String desc() default ""; //日志描述
}
