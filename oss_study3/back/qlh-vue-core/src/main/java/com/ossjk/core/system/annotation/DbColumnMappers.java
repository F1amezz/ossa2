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
 * @ClassName: DbColumnMappers 
 * @Description: 数据库字段映射-注解容器
 * @author: chair
 * @date: 2021年8月20日 上午10:44:25
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DbColumnMappers {
	DbColumnMapper[] value();
}
