package com.ossjk.qlh.system.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ossjk.core.base.controller.BaseController;
import com.ossjk.core.system.annotation.LogModule;
import com.ossjk.core.system.annotation.LogOperation;
import com.ossjk.core.system.api.ISystemCommonApi;
import com.ossjk.core.system.dto.LoginUser;
import com.ossjk.core.vo.ResponseBean;
import com.ossjk.qlh.system.dto.StudentDto;
import com.ossjk.qlh.system.entity.Student;
import com.ossjk.qlh.system.service.IStudentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 
 * Copyright 2021 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.system.controller
 * @ClassName: MainController
 * @Description: 基础管理
 * @author: Jason
 * @date: 2021年3月1日 上午9:33:56
 */
@LogModule(value = "基础管理")
@Api(tags = "基础管理")
@RestController
@RequestMapping("/main")
public class MainController extends BaseController {
	@Autowired
	private ISystemCommonApi systemCommonApi;

	@Autowired
	private IStudentService iUserService;

	@LogOperation("获取用户信息")
	@ApiOperation(value = "获取用户信息")
	@GetMapping(value = "/getInfo")
	public ResponseBean<LoginUser> getInfo(Page page, HttpServletRequest request) {
		// 获取用户信息
		LoginUser loginUser = systemCommonApi.getLoginUser(getToekn(request));
		System.out.println(loginUser);
		return ResponseBean.Success(loginUser);
	}




	/**
	 * 去编辑用户信息
	 *
	 * @param id
	 * @return
	 */
	@LogOperation("去编辑用户信息")
	@ApiOperation(value = "去编辑用户信息")
	@GetMapping(value = "/toUpdateInfo")
	public ResponseBean<LoginUser> toUpdateInfo(HttpServletRequest request) {
		return ResponseBean.Success(systemCommonApi.getLoginUser(getToekn(request)));
	}

	/**
	 * 编辑用户信息
	 *
	 * @param id
	 * @return
	 */
	@LogOperation("编辑用户信息")
	@ApiOperation(value = "编辑用户信息")
	@PutMapping(value = "/updateInfo")
	public ResponseBean updateInfo(@RequestBody Student user, HttpServletRequest request) {
		if (iUserService.updateById(user)) {
			systemCommonApi.refreshTokenCacheLoginUser(getToekn(request));
			return ResponseBean.Success();
		} else {
			return ResponseBean.Fail();
		}
	}

	/**
	 * 编辑密码
	 * 
	 * @param id
	 * @return
	 */
	@LogOperation("编辑密码")
	@ApiOperation(value = "编辑密码")
	@PutMapping(value = "/updatePwd")
	public ResponseBean updatePwd(@RequestBody StudentDto user, HttpServletRequest request) {
		LoginUser loginUser = systemCommonApi.getLoginUser(getToekn(request));
		Student old = iUserService.getById(loginUser.getId());
		// 验证旧密码
		// 获取数据库的密码
		if (!StrUtil.equals(old.getPwd(), SecureUtil.md5(user.getPwd()))) {
			return ResponseBean.Fail("旧密码错误");
		} else {
			// 旧密码正确
			// 编辑密码
			Student user2 = new Student();
			user2.setId(old.getId());
			user2.setPwd(SecureUtil.md5(user.getNewPwd()));
			if (iUserService.updateById(user2)) {
				return ResponseBean.Success();
			} else {
				return ResponseBean.Fail();
			}
		}
	}

}
