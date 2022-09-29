package com.ossjk.config.mybatis;

import java.sql.SQLException;

import javax.sql.DataSource;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.optimize.JsqlParserCountOptimize;
import com.ossjk.config.redis.RedisConfig;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * Copyright © 2020 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.config.mybatis
 * @ClassName: MybatisConfig
 * @Description: Mybatis配置
 * @author: chair
 * @date: 2020年12月8日 下午8:58:28
 */
@Slf4j
@Configuration
@MapperScan("com.ossjk.qlh.*.mapper")
@EnableConfigurationProperties(MyBatisConfigProperties.class)
public class MybatisConfig {

	@Autowired
	private MyBatisConfigProperties myBatisConfigProperties;

	/**
	 * 数据库连接池
	 *
	 * @return
	 */
	@Bean("druidDataSource")
	public DataSource druidDataSource() {
		log.info("druid数据库连接池初始化。。。");
		DruidDataSource datasource = new DruidDataSource();
		try {
			datasource.setUrl(myBatisConfigProperties.getUrl());
			datasource.setUsername(myBatisConfigProperties.getDbuser());
			datasource.setPassword(myBatisConfigProperties.getPassword());
			datasource.setDriverClassName(myBatisConfigProperties.getDriverClassName());
			datasource.setInitialSize(myBatisConfigProperties.getInitialSize());
			datasource.setMinIdle(myBatisConfigProperties.getMinIdle());
			datasource.setMaxActive(myBatisConfigProperties.getMaxActive());
			datasource.setMaxWait(myBatisConfigProperties.getMaxWait());
			datasource.setTimeBetweenEvictionRunsMillis(myBatisConfigProperties.getTimeBetweenEvictionRunsMillis());
			datasource.setMinEvictableIdleTimeMillis(myBatisConfigProperties.getMinEvictableIdleTimeMillis());
			datasource.setValidationQuery(myBatisConfigProperties.getValidationQuery());
			datasource.setTestWhileIdle(myBatisConfigProperties.isTestWhileIdle());
			datasource.setTestOnBorrow(myBatisConfigProperties.isTestOnBorrow());
			datasource.setTestOnReturn(myBatisConfigProperties.isTestOnReturn());
			datasource.setFilters(myBatisConfigProperties.getFilters());
		} catch (SQLException e) {
			log.error("druid数据库连接池初始化失败。", e);
		}
		log.info("druid数据库连接池完成。");
		return datasource;
	}

	/**
	 * 分页插件，自动识别数据库类型 多租户，请参考官网【插件扩展】
	 */
	@Bean
	public PaginationInterceptor paginationInterceptor() {
		PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
		// 设置请求的页面大于最大页后操作， true调回到首页，false 继续请求 默认false
		// paginationInterceptor.setOverflow(false);
		// 设置最大单页限制数量，默认 500 条，-1 不受限制
		paginationInterceptor.setLimit(-1);
		// 开启 count 的 join 优化,只针对部分 left join
		paginationInterceptor.setCountSqlParser(new JsqlParserCountOptimize(true));
		return paginationInterceptor;
	}

//	@Bean
//	public MethodValidationPostProcessor methodValidationPostProcessor() {
//		MethodValidationPostProcessor postProcessor = new MethodValidationPostProcessor();
//		/** 设置validator模式为快速失败返回 */
//		postProcessor.setValidator(validator());
//		return postProcessor;
//	}
//
//	@Bean
//	public Validator validator() {
//		ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class).configure().addProperty("hibernate.validator.fail_fast", "true").buildValidatorFactory();
//		Validator validator = validatorFactory.getValidator();
//		return validator;
//	}
}