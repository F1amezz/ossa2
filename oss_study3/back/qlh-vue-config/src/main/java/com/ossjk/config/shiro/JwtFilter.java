package com.ossjk.config.shiro;

import javax.servlet.Filter;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;

import com.alibaba.fastjson.JSON;
import com.ossjk.core.constant.Constant;
import com.ossjk.core.system.api.ISystemCommonApi;
import com.ossjk.core.vo.ResponseBean;

import cn.hutool.extra.servlet.ServletUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * Copyright © 2020 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.config.shiro
 * @ClassName: JwtFilter
 * @Description: JwtFilter拦截器
 * @author: chair
 * @date: 2020年12月8日 下午9:00:25
 */
@Slf4j
public class JwtFilter extends BasicHttpAuthenticationFilter implements Filter {

	private ISystemCommonApi iSystemCommonApi;

	public JwtFilter(ISystemCommonApi iSystemCommonApi) {
		this.iSystemCommonApi = iSystemCommonApi;
	}

	/**
	 * 执行登录
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@Override
	protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		// 获取token
		String token = ISystemCommonApi.getRequestToekn(httpServletRequest);
		ResponseBean responseBean = null;
		try {
			JwtToken jwtToken = new JwtToken(token);
			// 提交给realm进行登入，如果错误他会抛出异常并被捕获
			getSubject(request, response).login(jwtToken);
			// 如果没有抛出异常则代表登入成功，返回true
			return true;
		} catch (NoTokenException e) {
			e.printStackTrace();
			// token失效
			responseBean = new ResponseBean(Constant.RESPONSE_CODE_NOLOGIN, Constant.RESPONSE_MSG_NOLOGIN, null);
		} catch (TokenFlushException e) {
			e.printStackTrace();
			// 删除缓存token生成新token，并且缓存用户信息
			token = iSystemCommonApi.refreshTokenAndCacheLoginUser(token);
			responseBean = new ResponseBean(Constant.RESPONSE_CODE_REFRESH, Constant.RESPONSE_MSG_REFRESH, token);
		} catch (AuthenticationException e) {
			e.printStackTrace();
			// token失效
			responseBean = new ResponseBean(Constant.RESPONSE_CODE_BADTOKEN, Constant.RESPONSE_MSG_BADTOKEN, null);
		}
		String json = JSON.toJSONString(responseBean);
		log.warn("拦截请求：" + httpServletRequest.getRequestURI() + ",原因：" + json);
		ServletUtil.write(httpServletResponse, json, "application/json;charset=UTF-8");
		return false;

	}

	/**
	 * 执行登录认证
	 *
	 * @param request
	 * @param response
	 * @param mappedValue
	 * @return
	 */
	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
		try {
			return executeLogin(request, response);
			// return true;有一篇博客这里直接返回true是不正确的,在这里我特别指出一下
		} catch (Exception e) {
			log.error("拦截器报错", e);
			return false;
		}
	}

}
