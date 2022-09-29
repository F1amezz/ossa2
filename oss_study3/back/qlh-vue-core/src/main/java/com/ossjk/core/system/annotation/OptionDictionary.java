package com.ossjk.core.system.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * Copyright © 2021 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.core.system.annotation
 * @ClassName: OptionDictionary
 * @Description: 选项字段-注解
 * @author: chair
 * @date: 2021年3月3日 下午9:29:17
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(OptionDictionarys.class)
public @interface OptionDictionary {
	/**
	 * 数据字典key
	 *
	 * @return 返回类型： String
	 */
	String key();

	/**
	 * 返回属性名称
	 *
	 * @return 返回类型： String
	 */
	String fieldName() default "";

}
