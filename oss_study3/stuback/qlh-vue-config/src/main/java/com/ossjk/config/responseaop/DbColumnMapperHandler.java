package com.ossjk.config.responseaop;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.db.DbUtil;
import cn.hutool.db.Entity;
import cn.hutool.db.handler.EntityHandler;
import cn.hutool.db.sql.SqlExecutor;
import com.ossjk.config.utils.DynamicBean;
import com.ossjk.core.exception.QlhRunTimeException;
import com.ossjk.core.system.annotation.AreaMapper;
import com.ossjk.core.system.annotation.DbColumnMapper;
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
 * @ClassName: DbColumnMapperHandler
 * @Description: 响应数据库字段映射处理器
 * @author: Chair
 * @date: 2021/7/21 11:43 下午
 */
@Component
@Order(3)
@Slf4j
public class DbColumnMapperHandler implements IResponseAnnotationHandler {
	@Autowired
	private DataSource dataSource;

	private String querySql = "select %s  from %s where %s = ?";
	/**
	 * 返回前缀
	 */
	private static final String FIELDCOLUMN_PREFIX = "dbColumn_";

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
			DbColumnMapper[] dbColumnMappers = setField.getAnnotationsByType(DbColumnMapper.class);
			if (ObjectUtil.isNotEmpty(dbColumnMappers)) {
				for (DbColumnMapper dbColumnMapper : dbColumnMappers) {
					// 数据表名
					String tableName = dbColumnMapper.tableName();
					// 查询字段
					String[] columns = dbColumnMapper.columns();
					// 条件字段
					String condition = dbColumnMapper.condition();
					// 多字段内容连接符("-")
					String conjunction = dbColumnMapper.conjunction();
					// 返回属性名称
					String targetFiledName = FIELDCOLUMN_PREFIX + (ObjectUtil.isEmpty(dbColumnMapper.fieldName()) ? setField.getName() : dbColumnMapper.fieldName());

					// 检查注解必填属性
					if (ObjectUtil.isEmpty(columns) || ObjectUtil.isEmpty(tableName) || ObjectUtil.isEmpty(condition)) {
						QlhRunTimeException qlhException = new QlhRunTimeException("class:" + setField.getDeclaringClass() + ",field:" + setField.getName() + ",DbColumnMapper注解必填属性缺失。");
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
						String sql = String.format(querySql, StrUtil.join(",", columns), tableName, condition);
						Connection connection = null;
						try {
							connection = dataSource.getConnection();
							log.debug("语句:" + sql);
							log.debug("参数:" + sourceFieldValue);
							Entity entity = SqlExecutor.query(connection, sql, new EntityHandler(), sourceFieldValue);
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

	@Override
	public Class getAnnotationClass() {
		return DbColumnMapper.class;
	}

}
