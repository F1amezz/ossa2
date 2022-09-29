package com.ossjk.core.system.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * Copyright 2021 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.core.system.annotation
 * @ClassName: AreaModule
 * @Description: 地区字段-注解
 * @author: Jason
 * @date: 2021年3月11日 下午9:20:27
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(AreaMappers.class)
public @interface AreaMapper {

	/**
	 * 返回属性名称
	 *
	 * @return 返回类型： String
	 */
	String fieldName() default "";

	/**
	 * 返回地区表的字段名称
	 *
	 * @return 返回类型： String
	 */
	String columnName() default "name";

}
