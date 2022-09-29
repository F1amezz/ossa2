package com.ossjk.core.system.api;

import javax.servlet.http.HttpServletRequest;

import com.ossjk.core.constant.Constant;
import com.ossjk.core.system.dto.LoginUser;
import com.ossjk.core.system.dto.LoginfoDto;

import cn.hutool.core.util.StrUtil;

/**
 * 
 * Copyright © 2021 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.core.system.api
 * @ClassName: ILogInfoCommonApi 
 * @Description: 日志公共业务接口
 * @author: chair
 * @date: 2021年3月3日 下午10:09:13
 */
public interface ILogInfoCommonApi {

	void saveLog(LoginfoDto loginfoDto);

}
