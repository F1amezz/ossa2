package com.ossjk.config.mybatis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * Copyright © 2020 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.config.mybatis
 * @ClassName: MyBatisConfigProperties 
 * @Description: MyBatis配置属性
 * @author: chair
 * @date: 2020年12月8日 下午8:58:53
 */
@Slf4j
@Data
@ConfigurationProperties()
public class MyBatisConfigProperties {

	@Value("${spring.datasource.url}")
	private String url;

	@Value("${spring.datasource.username}")
	private String dbuser;

	@Value("${spring.datasource.password}")
	private String password;

	@Value("${spring.datasource.driver-class-name}")
	private String driverClassName;

	@Value("${spring.datasource.druid.initialSize}")
	private int initialSize;

	@Value("${spring.datasource.druid.minIdle}")
	private int minIdle;

	@Value("${spring.datasource.druid.maxActive}")
	private int maxActive;

	@Value("${spring.datasource.druid.maxWait}")
	private int maxWait;

	@Value("${spring.datasource.druid.timeBetweenEvictionRunsMillis}")
	private int timeBetweenEvictionRunsMillis;

	@Value("${spring.datasource.druid.minEvictableIdleTimeMillis}")
	private int minEvictableIdleTimeMillis;

	@Value("${spring.datasource.druid.validationQuery}")
	private String validationQuery;

	@Value("${spring.datasource.druid.testWhileIdle}")
	private boolean testWhileIdle;

	@Value("${spring.datasource.druid.testOnBorrow}")
	private boolean testOnBorrow;

	@Value("${spring.datasource.druid.testOnReturn}")
	private boolean testOnReturn;

	@Value("${spring.datasource.druid.filters}")
	private String filters;

	@Value("${spring.datasource.druid.logSlowSql}")
	private String logSlowSql;
}
