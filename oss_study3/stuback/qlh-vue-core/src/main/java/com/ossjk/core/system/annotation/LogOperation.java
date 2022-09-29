package com.ossjk.core.system.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * Copyright © 2021 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.core.system.annotation
 * @ClassName: LogOperation 
 * @Description: 日志操作注解
 * @author: chair
 * @date: 2021年2月28日 下午9:07:03
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LogOperation {
	String value();
}
