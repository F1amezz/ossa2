package com.ossjk.config.shiro;

import com.ossjk.core.system.api.ISystemCommonApi;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 
 * Copyright © 2021 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.config.shiro
 * @ClassName: ShiroConfig
 * @Description: Shiro配置
 * @author: chair
 * @date: 2021年3月7日 下午11:02:56
 */
@Configuration
public class ShiroConfig {

	@Autowired
	private ShiroRealm shiroRealm;
	@Autowired
	private ISystemCommonApi iSystemCommonApi;

	/**
	 * 注入安全过滤器
	 * 
	 * @param securityManager
	 * @return
	 */
	@Bean("shiroFilter")
	public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		shiroFilterFactoryBean.setSecurityManager(securityManager);
		// 拦截器
		Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();

		// 添加自己的过滤器并且取名为jwt
		Map<String, Filter> filterMap = new HashMap<String, Filter>(2);
		// 跨域拦截器
		filterMap.put("cros", new CrosFilter());
		// token 拦截器
		filterMap.put("jwt", new JwtFilter(iSystemCommonApi));
			shiroFilterFactoryBean.setFilters(filterMap);
		// <!-- 过滤链定义，从上向下顺序执行，一般将/**放在最为下边
		filterChainDefinitionMap.put("/login", "cros,anon");

		filterChainDefinitionMap.put("/getVerifyCode", "cros,anon");
		filterChainDefinitionMap.put("/getSystemSetting", "cros,anon");
		filterChainDefinitionMap.put("/sendFindPwdEmail", "cros,anon");
		filterChainDefinitionMap.put("/findPwd", "cros,anon");
		filterChainDefinitionMap.put("/getAllAreas", "cros,anon");
		// 放行静态资源
		filterChainDefinitionMap.put("/statics/**", "cros,anon");
		// 放行druid监控
		filterChainDefinitionMap.put("/druid/**", "cros,anon");
		// 放行站内信息
		filterChainDefinitionMap.put("/smsServer/**", "cros,anon");
		// 放行knife4j
		filterChainDefinitionMap.put("/swagger-resources", "cros,anon");
		filterChainDefinitionMap.put("/v2/api-docs", "cros,anon");
		filterChainDefinitionMap.put("/v2/api-docs-ext", "cros,anon");
		filterChainDefinitionMap.put("/doc.html", "cros,anon");
		filterChainDefinitionMap.put("/webjars/**", "cros,anon");
		// 放行微信登录接口
		filterChainDefinitionMap.put("/getWechatLoginInfo", "cros,anon");
		filterChainDefinitionMap.put("/wechatLogin", "cros,anon");
		filterChainDefinitionMap.put("/wechatBind", "cros,anon");

		// 开发阶段临时放行小程序
		filterChainDefinitionMap.put("/weapp/**", "cros,anon");

		filterChainDefinitionMap.put("/**", "cros,jwt");

		// 未授权界面;
		shiroFilterFactoryBean.setUnauthorizedUrl("/403");
		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
		return shiroFilterFactoryBean;
	}

	/**
	 * 注入安全管理器
	 * 
	 * @param shiroRealm
	 * @return
	 */
	@Bean("securityManager")
	public SecurityManager securityManager(ShiroRealm shiroRealm) {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		securityManager.setRealm(shiroRealm);
		/*
		 * 关闭shiro自带的session，详情见文档
		 * http://shiro.apache.org/session-management.html#SessionManagement-
		 * StatelessApplications%28Sessionless%29
		 */
		DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
		DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
		defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
		subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
		securityManager.setSubjectDAO(subjectDAO);
		return securityManager;

	}

	/**
	 * 使用Aop
	 * 
	 * @return
	 */
	@Bean
	public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
		DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
		advisorAutoProxyCreator.setProxyTargetClass(true);
		return advisorAutoProxyCreator;
	}

	/**
	 * 生效注解
	 * 
	 * @param securityManager
	 * @return
	 */
	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
		AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
		authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
		return authorizationAttributeSourceAdvisor;
	}

}
