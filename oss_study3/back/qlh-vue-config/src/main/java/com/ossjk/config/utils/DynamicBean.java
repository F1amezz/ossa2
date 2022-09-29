package com.ossjk.config.utils;

import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtilsBean;

import cn.hutool.core.bean.BeanUtil;
import net.sf.cglib.beans.BeanGenerator;
import net.sf.cglib.beans.BeanMap;

/**
 * 
 * Copyright 2021 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.config.utils
 * @ClassName: DynamicBean
 * @Description: cglib动态对象添加属性
 * @author: Jason
 * @date: 2021年3月21日 下午6:06:32
 */
public class DynamicBean {

	/**
	 * 目标对象
	 */
	private Object target;

	/**
	 * 属性集合
	 */
	private BeanMap beanMap;

	public DynamicBean(Class superclass, Map<String, Class> propertyMap) {
		this.target = generateBean(superclass, propertyMap);
		this.beanMap = BeanMap.create(this.target);
	}

	/**
	 * bean 添加属性和值
	 *
	 * @param property
	 * @param value
	 */
	public void setValue(String property, Object value) {
		beanMap.put(property, value);
	}

	/**
	 * 获取属性值
	 *
	 * @param property
	 * @return
	 */
	public Object getValue(String property) {
		return beanMap.get(property);
	}

	/**
	 * 获取对象
	 *
	 * @return
	 */
	public Object getTarget() {
		return this.target;
	}

	/**
	 * 根据属性生成对象
	 *
	 * @param superclass
	 * @param propertyMap
	 * @return
	 */
	private Object generateBean(Class superclass, Map<String, Class> propertyMap) {
		BeanGenerator generator = new BeanGenerator();
		if (null != superclass) {
			generator.setSuperclass(superclass);
		}
		BeanGenerator.addProperties(generator, propertyMap);
		return generator.create();
	}

	/**
	 * 为dest对象添加addProperties中的属性
	 * 
	 * @param dest
	 * @param addProperties
	 * @return
	 */
	public static Object getTarget(Object dest, Map<String, Object> addProperties) {
		// 定义获取所有属性的对象
		PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean();
		// 获取对象中的所有属性
		PropertyDescriptor[] descriptors = propertyUtilsBean.getPropertyDescriptors(dest);
		// 定义组装类型的属性集合
		Map<String, Class> propertyMap = new HashMap();
		// 获取dest对象的属性和属性的类型封装到propertyMap
		for (PropertyDescriptor d : descriptors) {
			if (!"class".equalsIgnoreCase(d.getName())) {
				propertyMap.put(d.getName(), d.getPropertyType());
			}
		}
		// 把新添加的属性也添加到propertyMap
		addProperties.forEach((k, v) -> propertyMap.put(k, v.getClass()));
		// 动态创建新对象
		DynamicBean dynamicBean = new DynamicBean(dest.getClass(), propertyMap);
		// 把dest对象的值设置到dynamicBean
		propertyMap.forEach((k, v) -> {
			try {
				if (!addProperties.containsKey(k)) {
					dynamicBean.setValue(k, propertyUtilsBean.getNestedProperty(dest, k));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		// 把新添加的属性的值设置到dynamicBean
		addProperties.forEach((k, v) -> {
			try {
				dynamicBean.setValue(k, v);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		Object target = dynamicBean.getTarget();
		return target;

	}

}