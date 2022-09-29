package com.ossjk.config.responseaop;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.ossjk.config.utils.DynamicBean;
import com.ossjk.core.exception.QlhRunTimeException;
import com.ossjk.core.system.annotation.AreaMapper;
import com.ossjk.core.system.api.IAreaCommonApi;
import com.ossjk.core.system.dto.AreaDto;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReflectUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * Copyright 2021 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.config.responseaop
 * @ClassName: AreaMapperHandler
 * @Description: 响应对象地区映射处理器
 * @author: Jason
 * @date: 2021年3月13日 下午10:33:13
 */
@Component
@Order(1)
@Slf4j
public class AreaMapperHandler implements IResponseAnnotationHandler {
	@Autowired
	private IAreaCommonApi iAreaCommonApi;
	/**
	 * 返回前缀
	 */
	private static final String AREA_PREFIX = "area_";

	@Override
	public Object handle(Object responseObject, Collection<Field> fieldSets) {
		if (ObjectUtil.isEmpty(iAreaCommonApi)) {
			QlhRunTimeException qlhException = new QlhRunTimeException("AreaAspect 注入组件出错。");
			log.error("异常：" + qlhException.getMessage(), qlhException);
			throw qlhException;
		}
		// 注解属性，解析后属性存储map
		Map<String, Object> addField = new HashMap<String, Object>();
		for (Field setField : fieldSets) {
			// 获取属性注解
			AreaMapper[] areaMappers = setField.getAnnotationsByType(AreaMapper.class);
			if (ObjectUtil.isNotEmpty(areaMappers)) {
				for (AreaMapper areaMapper : areaMappers) {
					// 返回属性名
					String annotationFieldName = areaMapper.fieldName();
					// 返回字段
					String annotationColumnName = areaMapper.columnName();

					// 返回字段值
					Object annotationColumnValue = null;

					// 判断是否自定义返回属性名
					if (ObjectUtil.isEmpty(annotationFieldName)) {
						annotationFieldName = AREA_PREFIX + setField.getName();
					} else {
						annotationFieldName = AREA_PREFIX + annotationFieldName;
					}

					// 获取属性值
					Object fieldValue = ReflectUtil.getFieldValue(responseObject, setField.getName());
					if (ObjectUtil.isEmpty(fieldValue)) {
						// 转换为代理类后，属性前加上了"$cglib_prop_"，普通属性名获取为空是再获取一次代理属性值
						fieldValue = ReflectUtil.getFieldValue(responseObject, "$cglib_prop_" + setField.getName());
					}
					// 属性值为空返回空字符串，如果返回null，PicBeanAddPropertiesUtil会报错
					if (ObjectUtil.isEmpty(fieldValue)) {
						log.warn("class:" + setField.getDeclaringClass() + ",field:" + setField.getName() + ",columnName:" + annotationColumnName + ",fieldValue:" + fieldValue + "。地区映射项失败。");
						annotationColumnValue = "";
					} else {
						// 查询数据
						AreaDto area = iAreaCommonApi.getAreaById(fieldValue.toString());
						// 数据为空返回原值，不为空根据注解定义的属性返回
						if (ObjectUtil.isEmpty(area)) {
							log.warn("class:" + setField.getDeclaringClass() + ",field:" + setField.getName() + ",columnName:" + annotationColumnName + ",fieldValue:" + fieldValue + "。地区映射值失败。");
							annotationColumnValue = fieldValue;
						} else {
							annotationColumnValue = ReflectUtil.getFieldValue(area, annotationColumnName);
						}
					}
					// 设置要追加的属性
					addField.put(annotationFieldName, annotationColumnValue);
				}
			}
		}
		if (ObjectUtil.isNotEmpty(addField)) {
			// responseObject添加属性
			responseObject = DynamicBean.getTarget(responseObject, addField);
		}
		return responseObject;
	}

	@Override
	public Class getAnnotationClass() {
		return AreaMapper.class;
	}

}
