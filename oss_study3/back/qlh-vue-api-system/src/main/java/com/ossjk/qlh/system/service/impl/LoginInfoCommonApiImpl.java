package com.ossjk.qlh.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ossjk.core.system.api.ILogInfoCommonApi;
import com.ossjk.core.system.dto.LoginfoDto;
import com.ossjk.qlh.system.entity.Loginfo;
import com.ossjk.qlh.system.service.ILoginfoService;

import cn.hutool.core.bean.BeanUtil;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class LoginInfoCommonApiImpl implements ILogInfoCommonApi {

	@Autowired
	private ILoginfoService iLoginfoService;

	@Override
	public void saveLog(LoginfoDto loginfoDto) {
		iLoginfoService.save(BeanUtil.copyProperties(loginfoDto, Loginfo.class));
	}

}
