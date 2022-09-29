package com.ossjk.config.mybatis;

import org.springframework.aop.aspectj.AspectJExpressionPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.interceptor.*;

import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * Copyright © 2020 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.config.mybatis
 * @ClassName: TransactionAopConfig 
 * @Description: 事务拦截器
 * @author: chair
 * @date: 2020年12月8日 下午8:59:28
 */
@Slf4j
@Configuration
public class TransactionAopConfig {

	/**
	 * 事务拦截器
	 */
	@Bean("txInterceptor")
	TransactionInterceptor getTransactionInterceptor(PlatformTransactionManager tx) {
		return new TransactionInterceptor(tx, transactionAttributeSource());
	}

	/**
	 * 切面拦截规则 参数会自动从容器中注入
	 */
	@Bean
	public AspectJExpressionPointcutAdvisor pointcutAdvisor(TransactionInterceptor txInterceptor) {
		AspectJExpressionPointcutAdvisor pointcutAdvisor = new AspectJExpressionPointcutAdvisor();
		pointcutAdvisor.setAdvice(txInterceptor);
		pointcutAdvisor.setExpression("execution (* com.ossjk.qlh.*.service.*.*(..))");
		return pointcutAdvisor;
	}

	/**
	 * 事务拦截类型
	 */
	@Bean("txSource")
	public TransactionAttributeSource transactionAttributeSource() {
		NameMatchTransactionAttributeSource source = new NameMatchTransactionAttributeSource();
		/* 只读事务，不做更新操作 */
		RuleBasedTransactionAttribute readOnlyTx = new RuleBasedTransactionAttribute();
		readOnlyTx.setReadOnly(true);
		readOnlyTx.setPropagationBehavior(TransactionDefinition.PROPAGATION_NOT_SUPPORTED);
		/* 当前存在事务就使用当前事务，当前不存在事务就创建一个新的事务 */
		RuleBasedTransactionAttribute requiredTx = new RuleBasedTransactionAttribute();
		requiredTx.setRollbackRules(Collections.singletonList(new RollbackRuleAttribute(Exception.class)));
		requiredTx.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		// requiredTx.setTimeout(5);
		Map<String, TransactionAttribute> txMap = new HashMap<>();
		txMap.put("remove*", requiredTx);
		txMap.put("save*", requiredTx);
		txMap.put("insert*", requiredTx);
		txMap.put("update*", requiredTx);
		txMap.put("delete*", requiredTx);
		txMap.put("batch*", requiredTx);
		txMap.put("select*", readOnlyTx);
		txMap.put("get*", readOnlyTx);
		txMap.put("list*", readOnlyTx);
		txMap.put("page*", readOnlyTx);
		source.setNameMap(txMap);
		return source;
	}

}