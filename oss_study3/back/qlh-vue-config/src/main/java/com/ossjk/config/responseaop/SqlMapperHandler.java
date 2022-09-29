package com.ossjk.config.responseaop;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.db.DbUtil;
import cn.hutool.db.Entity;
import cn.hutool.db.handler.EntityHandler;
import cn.hutool.db.sql.SqlExecutor;
import com.ossjk.config.utils.DynamicBean;
import com.ossjk.core.exception.QlhRunTimeException;
import com.ossjk.core.system.annotation.DbColumnMapper;
import com.ossjk.core.system.annotation.OptionDictionary;
import com.ossjk.core.system.annotation.SqlMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Copyright © 2021 QLH. Tech Ltd. All rights reserved.
 *
 * @Package: com.ossjk.config.responseaop
 * @ClassName: SqlMapperHandler
 * @Description: 响应Sql映射处理器
 * @author: Chair
 * @date: 2021/7/21 11:43 下午
 */
@Component
@Order(4)
@Slf4j
public class SqlMapperHandler implements IResponseAnnotationHandler {
	@Autowired
	private DataSource dataSource;

	/**
	 * 返回前缀
	 */
	private static final String FIELDCOLUMN_PREFIX = "sqlMapper_";

	@Override
	public Object handle(Object responseObject, Collection<Field> fieldSets) {
		if (ObjectUtil.isEmpty(dataSource)) {
			QlhRunTimeException qlhException = new QlhRunTimeException("FieldColumnMapperHandler 注入组件出错。");
			log.error("异常：" + qlhException.getMessage(), qlhException);
			throw qlhException;
		}
		// 注解属性，解析后属性存储map
		Map<String, Object> addField = new HashMap<String, Object>();
		for (Field setField : fieldSets) {
			// 获取属性注解
			SqlMapper[] sqlMappers = setField.getAnnotationsByType(SqlMapper.class);
			if (ObjectUtil.isNotEmpty(sqlMappers)) {
				for (SqlMapper sqlMapper : sqlMappers) {
					// 查询语句
					String querySql = sqlMapper.querySql();
					// 多字段内容连接符("-")
					String conjunction = sqlMapper.conjunction();
					// 返回属性名称
					String targetFiledName = FIELDCOLUMN_PREFIX + (ObjectUtil.isEmpty(sqlMapper.fieldName()) ? setField.getName() : sqlMapper.fieldName());

					// 检查注解必填属性
					if (ObjectUtil.isEmpty(querySql)) {
						QlhRunTimeException qlhException = new QlhRunTimeException("class:" + setField.getDeclaringClass() + ",field:" + setField.getName() + ",SqlMapper注解必填属性缺失。");
						log.error("异常：" + qlhException.getMessage(), qlhException);
						throw qlhException;
					}
					// 返回字段值
					Object targetFieldValue = null;
					// 获取源属性值
					Object sourceFieldValue = ReflectUtil.getFieldValue(responseObject, setField.getName());
					if (ObjectUtil.isEmpty(sourceFieldValue)) {
						// 转换为代理类后，属性前加上了"$cglib_prop_"，普通属性名获取为空是再获取一次代理属性值
						sourceFieldValue = ReflectUtil.getFieldValue(responseObject, "$cglib_prop_" + setField.getName());
					}
					if (ObjectUtil.isEmpty(sourceFieldValue)) {
						log.warn("class:" + setField.getDeclaringClass() + ",field:" + setField.getName() + ",fieldValue:" + sourceFieldValue + "。数据表字段属性值为null。");
						targetFieldValue = "";
					} else {
						Connection connection = null;
						try {
							connection = dataSource.getConnection();
							String sql = querySql;
							Object[] args = newSameArray(sourceFieldValue, getSqlPlaceholder(sql));
							log.debug("语句:" + sql);
							log.debug("参数:" + ArrayUtil.toString(args));
							Entity entity = SqlExecutor.query(connection, sql, new EntityHandler(), args);
							Object result = ObjectUtil.isNull(entity) ? null : CollUtil.join(entity.values(), conjunction);
							if (ObjectUtil.isEmpty(result)) {
								log.warn("class:" + setField.getDeclaringClass() + ",field:" + setField.getName() + ",fieldValue:" + sourceFieldValue + "。数据表字段映射项失败。");
								targetFieldValue = sourceFieldValue;
							} else {
								targetFieldValue = result;
							}
						} catch (SQLException e) {
							e.printStackTrace();
							log.error("sql异常。", e);
							throw new QlhRunTimeException("sql异常。");
						} finally {
							DbUtil.close(connection);
						}
					}
					addField.put(targetFiledName, targetFieldValue);
				}
			}
		}
		if (ObjectUtil.isNotEmpty(addField)) {
			// responseObject添加属性
			responseObject = DynamicBean.getTarget(responseObject, addField);
		}
		return responseObject;
	}

	private int getSqlPlaceholder(String sql) {
		if (StrUtil.isNotBlank(sql)) {
			if (sql.lastIndexOf("?") == (sql.length() - 1)) {
				sql = sql + " ";
			}
			String[] splitArray = sql.split("\\?");
			return (splitArray.length - 1);
		}
		return 0;
	}

	private Object[] newSameArray(Object value, int size) {
		Object[] result = ArrayUtil.newArray(size);
		for (int i = 0; i < size; i++) {
			result[i] = value;
		}
		return result;
	}

	@Override
	public Class getAnnotationClass() {
		return SqlMapper.class;
	}

}
