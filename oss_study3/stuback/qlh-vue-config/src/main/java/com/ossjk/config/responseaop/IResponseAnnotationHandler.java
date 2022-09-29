package com.ossjk.config.responseaop;

import java.lang.reflect.Field;
import java.util.Collection;

/**
 * Copyright 2021 QLH. Tech Ltd. All rights reserved.
 *
 * @Package: com.ossjk.config.responseaop
 * @ClassName: IResponseAnnotationHandler
 * @Description: 响应注解处理器
 * @author: Jason
 * @date: 2021年3月13日 下午10:32:43
 */
public interface IResponseAnnotationHandler {

    /**
     * 执行注解处理器的类型
     *
     * @return
     */
    Class getAnnotationClass();

    /**
     * 执行注解处理器实现
     *
     * @param responseObject 响应对象
     * @param fieldSets    注解属性集合
     * @return
     * @throws Throwable
     */
    Object handle(Object responseObject, Collection<Field> fieldSets);

}
