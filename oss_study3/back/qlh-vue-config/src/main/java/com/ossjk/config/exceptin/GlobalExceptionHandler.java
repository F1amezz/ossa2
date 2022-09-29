package com.ossjk.config.exceptin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.AuthorizationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSON;
import com.ossjk.core.constant.Constant;
import com.ossjk.core.vo.ResponseBean;

import cn.hutool.extra.servlet.ServletUtil;
import io.swagger.models.Response;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * Copyright © 2021 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.config.exceptin
 * @ClassName: GlobalExceptionHandler
 * @Description: 全局异常处理
 * @author: chair
 * @date: 2021年2月28日 下午9:07:21
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

	@ExceptionHandler(value = Exception.class)
	public void ExceptionHandler(Exception e) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
		log.error("发现异常：" + e.getMessage(), e);
		ServletUtil.write(response, JSON.toJSONString(ResponseBean.Exception(Constant.RESPONSE_MSG_EXCEPTION,e.getMessage())), "application/json;charset=UTF-8");
	}

	@ExceptionHandler(value = AuthorizationException.class)
	public void authorizationExceptionHandler(AuthorizationException e) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
		log.error("没有权限异常：" + e.getMessage(), e);
		ServletUtil.write(response, JSON.toJSONString(new ResponseBean(Constant.RESPONSE_CODE_NOAUTH, Constant.RESPONSE_MSG_NOAUTH, null)), "application/json;charset=UTF-8");
	}
}
