package com.ossjk.qlh.system.controller;

import com.ossjk.qlh.system.service.IDepartmentService;
import com.ossjk.qlh.system.service.IStudentService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.ossjk.core.base.controller.BaseController;
import com.ossjk.core.system.annotation.LogModule;
import com.ossjk.core.system.annotation.LogOperation;
import com.ossjk.core.system.api.ISystemCommonApi;
import com.ossjk.core.system.dto.EmailSetting;
import com.ossjk.core.system.dto.SystemSetting;
import com.ossjk.core.vo.ResponseBean;
import com.ossjk.qlh.system.dto.SettingDto;
import com.ossjk.qlh.system.vo.SettingVo;

import cn.hutool.core.bean.BeanUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Copyright 2021-01-16 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.system.controller
 * @ClassName: SystemsettingController
 * @Description: 系统设置-控制器
 * @author: Rick
 * @date: 2021-01-16 22:11:02
 */
@Api(tags = "系统设置")
@LogModule(value = "系统设置")
@RestController
@RequestMapping("/system/systemsetting")
public class SystemsettingController extends BaseController {

	@Autowired
	private ISystemCommonApi iSystemCommonApi;
	@Autowired
	private IDepartmentService departmentService;
	@Autowired
	private IStudentService iUserService;

	@LogOperation("列表")
	@RequiresPermissions(value = { "00000030" })
	@ApiOperation(value = "列表")
	@GetMapping("/list")
	public ResponseBean<SettingVo> list() {
		SystemSetting systemSetting = iSystemCommonApi.getSystemSetting();
		EmailSetting emailSetting = iSystemCommonApi.getEmailSetting();
		SettingVo settingVo = new SettingVo();
		settingVo.setSystemSetting(systemSetting);
		settingVo.setEmailSetting(emailSetting);
		return ResponseBean.Success(settingVo);
	}


	@LogOperation("编辑")
	@RequiresPermissions(value = { "00000030" })
	@ApiOperation(value = "编辑")
	@PutMapping("/update")
	public ResponseBean update(@RequestBody SettingDto settingDto) {
		if (iSystemCommonApi.updateSystemSetting(BeanUtil.copyProperties(settingDto, SystemSetting.class)) & iSystemCommonApi.updateEmailSetting(BeanUtil.copyProperties(settingDto, EmailSetting.class))) {
			return ResponseBean.Success();
		} else {
			return ResponseBean.Fail();
		}
	}


}
