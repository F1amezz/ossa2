package com.ossjk.qlh.components.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ossjk.core.base.controller.BaseController;
import com.ossjk.core.vo.ResponseBean;
import com.ossjk.qlh.system.entity.Department;
import com.ossjk.qlh.system.entity.Permission;
import com.ossjk.qlh.system.entity.Role;
import com.ossjk.qlh.system.service.IPermissionService;

import cn.hutool.core.util.ObjectUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 
 * Copyright © 2021 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.components.controller
 * @ClassName: PermissionComponentContrller
 * @Description: 权限组件-控制器
 * @author: Jason
 * @date: 2021年1月30日 下午11:50:13
 */
@Api(tags = "角色组件-控制器")
@RestController
@RequestMapping("/components/permissionComponent")
public class PermissionComponentContrller extends BaseController {
	@Autowired
	private IPermissionService iPermissionService;

	@ApiOperation(value = "列表")
	@GetMapping("/list")
	public ResponseBean<List<Permission>> list() {
		QueryWrapper<Permission> queryWrapper = new QueryWrapper<Permission>();
		queryWrapper.orderByAsc("level");
		queryWrapper.orderByAsc("sort");
		queryWrapper.orderByAsc("crtm");
		List<Permission> list = iPermissionService.list(queryWrapper);
		return ResponseBean.Success(list);
	}

	@ApiOperation(value = "根据ids查询")
	@GetMapping("/getByIds")
	public ResponseBean<List<Permission>> getByIds(
			@ApiParam(value = "ids") @RequestParam(name = "ids", required = false) String[] ids) {
		List<Permission> permissions = null;
		if (ObjectUtil.isNotEmpty(ids)) {
			permissions = new ArrayList<Permission>();
			for (String id : ids) {
				permissions.add(iPermissionService.getById(id));
			}
		}
		return ResponseBean.Success(permissions);
	}

}
