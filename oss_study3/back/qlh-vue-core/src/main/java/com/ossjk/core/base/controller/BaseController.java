package com.ossjk.core.base.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ossjk.core.constant.Constant;
import com.ossjk.core.system.api.ISystemCommonApi;
import com.ossjk.core.vo.ResponseBean;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.servlet.ServletUtil;

/**
 * 
 * Copyright © 2020 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.core.base.controller
 * @ClassName: BaseController
 * @Description: 基础控制层
 * @author: chair
 * @date: 2020年12月8日 下午2:30:22
 */
public class BaseController {

	/**
	 * 获取Ip
	 * 
	 * @param request
	 * @return
	 */
	protected String getIp(HttpServletRequest request) {
		return ServletUtil.getClientIP(request);
	}

	/**
	 * 获取token
	 * 
	 * @param request
	 * @return
	 */
	protected String getToekn(HttpServletRequest request) {
		System.out.println("request"+request);
		return ISystemCommonApi.getRequestToekn(request);
	}

}
