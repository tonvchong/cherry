/*
 * Copyright 2002-2010 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.tonvchong.core.view.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Copyright © 2016全球蜂.
 * 
 * @Title: QueryModel.java
 * @Project: RBM
 * @date: 2016年3月5日 上午11:47:17
 * @author: tonvchong
 * @Description: 该注解用于绑定请求参数（JSON对象）
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface QueryModel {

	/**
	 * 用于绑定的请求参数名字
	 */
	String value() default "";
	
	/**
	 * 是否必须，默认是
	 */
	boolean required() default true;

}
