package com.ossjk.config.responseaop;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.ossjk.config.utils.DynamicBean;
import com.ossjk.core.exception.QlhRunTimeException;
import com.ossjk.core.system.annotation.DbColumnMapper;
import com.ossjk.core.system.annotation.OptionDictionary;
import com.ossjk.core.system.api.IDictionaryCommonApi;
import com.ossjk.core.system.dto.DictionarydataDto;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * Copyright 2021 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.config.responseaop
 * @ClassName: OptionDictionaryHandler
 * @Description: 响应选项字典处理器
 * @author: Jason
 * @date: 2021年3月13日 下午10:33:36
 */
@Component
@Order(2)
@Slf4j
public class OptionDictionaryHandler implements IResponseAnnotationHandler {
	@Autowired
	private IDictionaryCommonApi iDictionaryCommonApi;
	/**
	 * 返回前缀
	 */
	private static final String DICTIONARY_PREFIX = "dictionary_";

	@Override
	public Object handle(Object responseObject, Collection<Field> fieldSets) {
		if (ObjectUtil.isEmpty(iDictionaryCommonApi)) {
			QlhRunTimeException qlhException = new QlhRunTimeException("OptionDictionaryAspect 注入组件出错。");
			log.error("异常：" + qlhException.getMessage(), qlhException);
			throw qlhException;
		}

		// 注解属性，解析后属性存储map
		Map<String, Object> addField = new HashMap<String, Object>();
		for (Field setField : fieldSets) {
			// 获取属性注解
			OptionDictionary[] optionDictionarys = setField.getAnnotationsByType(OptionDictionary.class);
			if (ObjectUtil.isNotEmpty(optionDictionarys)) {
				for (OptionDictionary optionDictionary : optionDictionarys) {

					// 数据字典key
					String optionDictionaryKey = optionDictionary.key();

					// 返回属性名称
					String optionDictionaryFieldName = optionDictionary.fieldName();

					// 返回字段值
					Object annotationColumnValue = null;

					// 判断是否自定义返回属性名
					if (ObjectUtil.isEmpty(optionDictionaryFieldName)) {
						optionDictionaryFieldName = DICTIONARY_PREFIX + setField.getName();
					} else {
						optionDictionaryFieldName = DICTIONARY_PREFIX + optionDictionaryFieldName;
					}

					// 获取属性值
					Object fieldValue = ReflectUtil.getFieldValue(responseObject, setField.getName());
					if (ObjectUtil.isEmpty(fieldValue)) {
						// 转换为代理类后，属性前加上了"$cglib_prop_"，普通属性名获取为空是再获取一次代理属性值
						fieldValue = ReflectUtil.getFieldValue(responseObject, "$cglib_prop_" + setField.getName());
					}
					if (ObjectUtil.isEmpty(fieldValue)) {
						log.warn("class:" + setField.getDeclaringClass() + ",field:" + setField.getName() + ",fieldValue:" + fieldValue + "。字典选项属性值为null。");
						annotationColumnValue = "";
					} else {
						List<DictionarydataDto> dictionarydataDtos = iDictionaryCommonApi.listDictionaryOption(optionDictionaryKey);
						// 判断属性值在数据库是否有数据，有数据匹配成功，没有返回原值
						if (ObjectUtil.isEmpty(dictionarydataDtos)) {
							log.warn("class:" + setField.getDeclaringClass() + ",field:" + setField.getName() + ",fieldValue:" + fieldValue + "。字典选项映射项失败。");
							annotationColumnValue = fieldValue;
						} else {
							// 匹配返回值属性值和字典选项数据值
							for (DictionarydataDto dictionarydataDto : dictionarydataDtos) {
								if (StrUtil.equals(dictionarydataDto.getDvalue(), fieldValue.toString())) {
									annotationColumnValue = dictionarydataDto.getDkey();
								}
							}
							if (ObjectUtil.isEmpty(annotationColumnValue)) {
								// 无法匹配，返回原值
								log.warn("class:" + setField.getDeclaringClass() + ",field:" + setField.getName() + ",fieldValue:" + fieldValue + "。字典选项映射值失败。");
								annotationColumnValue = fieldValue;
							}

						}
					}
					// 设置追加解析属性
					addField.put(optionDictionaryFieldName, annotationColumnValue);
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
		return OptionDictionary.class;
	}

}
