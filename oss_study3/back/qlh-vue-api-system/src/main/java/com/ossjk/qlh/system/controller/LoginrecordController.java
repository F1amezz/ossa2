package com.ossjk.qlh.system.controller;

import java.util.Arrays;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ossjk.core.base.controller.BaseController;
import com.ossjk.core.system.annotation.LogModule;
import com.ossjk.core.system.annotation.LogOperation;
import com.ossjk.core.vo.ResponseBean;
import com.ossjk.qlh.system.entity.Loginrecord;
import com.ossjk.qlh.system.service.ILoginrecordService;
import com.ossjk.qlh.system.vo.LoginrecordVo;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * Copyright 2021-01-19 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.system.controller
 * @ClassName: LoginrecordController
 * @Description: 登录记录-控制器
 * @author: Jason
 * @date: 2021-01-19 10:41:20
 */
@Api(tags = "登录日志")
@LogModule(value = "登录日志")
@RestController
@RequestMapping("/system/loginrecord")
public class LoginrecordController extends BaseController {

	@Autowired
	private ILoginrecordService iLoginrecordService;

	@LogOperation("列表")
	@RequiresPermissions(value = { "00000035" })
	@ApiOperation(value = "列表")
	@GetMapping("/list")
	public ResponseBean<Page<Loginrecord>> list(Page page, @ApiParam(value = "用户名") @RequestParam(name = "uname", required = false) String uname, @ApiParam(value = "手机号") @RequestParam(name = "mobile", required = false) String mobile, @ApiParam(value = "登录记录") @RequestParam(name = "loginresult", required = false) String loginresult) {
		QueryWrapper<LoginrecordVo> queryWrapper = new QueryWrapper<LoginrecordVo>();

		if (!StrUtil.isBlank(uname)) {
			queryWrapper.like("u.name", uname);
		}
		if (!StrUtil.isBlank(mobile)) {
			queryWrapper.like("u.mobile", mobile);
		}
		if (!StrUtil.isBlank(loginresult)) {
			queryWrapper.like("l.loginresult", loginresult);
		}
		queryWrapper.orderByDesc("l.logintime");
		return ResponseBean.Success(iLoginrecordService.pageVo(page, queryWrapper));
	}

	@LogOperation("删除")
	@RequiresPermissions(value = { "000000350005" })
	@ApiOperation(value = "删除")
	@DeleteMapping("/remove")
	public ResponseBean remove(@ApiParam(value = "ids") @RequestParam(name = "ids") String[] ids) {
		if (iLoginrecordService.removeByIds(Arrays.asList(ids))) {
			return ResponseBean.Success();
		} else {
			return ResponseBean.Fail();
		}
	}

	@LogOperation("详细")
	@RequiresPermissions(value = { "000000350000" })
	@ApiOperation(value = "详细")
	@GetMapping("/toDetail")
	public ResponseBean<LoginrecordVo> toDetail(@ApiParam(value = "id") @RequestParam(name = "id") String id) {
		LoginrecordVo loginrecordVo = iLoginrecordService.getVoById(id);
		if (ObjectUtil.isNotNull(loginrecordVo)) {
			return ResponseBean.Success(loginrecordVo);
		} else {
			return ResponseBean.Fail();
		}
	}
}
