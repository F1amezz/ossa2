package com.ossjk.core.system.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Copyright © 2021 QLH. Tech Ltd. All rights reserved.
 *
 * @Package: com.ossjk.core.system.annotation
 * @ClassName: SqlMapper
 * @Description:Sql映射-注解
 * @author: chair
 * @date: 2021/7/21 10:47 下午
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(SqlMappers.class)
public @interface SqlMapper {
    /**
     * 查询语句
     */
    String querySql();

    /**
     * 多字段内容连接符("-")
     */
    String conjunction() default "-";

    /**
     * 返回属性名称
     */
    String fieldName() default "";

}
