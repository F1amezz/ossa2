package com.ossjk.qlh.components.controller;

import java.util.ArrayList;
import java.util.List;

import com.ossjk.qlh.system.vo.StudentToUpdateVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ossjk.core.base.controller.BaseController;
import com.ossjk.core.vo.ResponseBean;
import com.ossjk.qlh.system.entity.Student;
import com.ossjk.qlh.system.service.IStudentService;
import com.ossjk.qlh.system.vo.StudentVo;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 
 * Copyright © 2021 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.components.controller
 * @ClassName: UserComponentContrller
 * @Description: 用户组件-控制器
 * @author: chair
 * @date: 2021年1月30日 下午11:50:13
 */
@Api(tags = "用户组件-控制器")
@RestController
@RequestMapping("/components/userComponent")
public class UserComponentContrller extends BaseController {
	@Autowired
	private IStudentService iUserService;

	@ApiOperation(value = "列表")
	@GetMapping("/list")
	public ResponseBean<Page<StudentVo>> list(
			@ApiParam(value = "名字") @RequestParam(name = "name", required = false) String name,
			@ApiParam(value = "登录名") @RequestParam(name = "lname", required = false) String lname, 
			@ApiParam(value = "手机") @RequestParam(name = "mobile", required = false) String mobile,
			@ApiParam(value = "部门id") @RequestParam(name = "did", required = false) String did, 
			@ApiParam(value = "角色id") @RequestParam(name = "rid", required = false) String rid,
			Page page) {
		QueryWrapper<Student> queryWrapper = new QueryWrapper<Student>();
		if (StrUtil.isNotBlank(name)) {
			queryWrapper.like("name", name);
		}
		if (StrUtil.isNotBlank(lname)) {
			queryWrapper.like("lname", lname);
		}
		if (StrUtil.isNotBlank(mobile)) {
			queryWrapper.like("mobile", mobile);
		}
		if (StrUtil.isNotBlank(did)) {
			queryWrapper.like("d.id", did);
		}
		if (StrUtil.isNotBlank(rid)) {
			queryWrapper.like("r.id", rid);
		}
		queryWrapper.orderByDesc("crtm");

		return ResponseBean.Success(iUserService.pageVo(page, queryWrapper));
	}

	@ApiOperation(value = "根据ids查询")
	@GetMapping("/getByIds")
	public ResponseBean<List<Student>> getByIds(@ApiParam(value = "ids") @RequestParam(name = "ids", required = false) String[] ids) {
		List<Student> users = null;
		if (ObjectUtil.isNotEmpty(ids)) {
			users = new ArrayList<Student>();
			for (String id : ids) {
				users.add(iUserService.getById(id));
			}
		}
		return ResponseBean.Success(users);
	}

}
