package com.ossjk.qlh.system.controller;

import java.util.Arrays;
import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ossjk.core.base.controller.BaseController;
import com.ossjk.core.system.annotation.LogModule;
import com.ossjk.core.system.annotation.LogOperation;
import com.ossjk.core.vo.ResponseBean;
import com.ossjk.qlh.system.dto.RoleDto;
import com.ossjk.qlh.system.entity.Role;
import com.ossjk.qlh.system.entity.RoleDepartment;
import com.ossjk.qlh.system.entity.RolePermission;
import com.ossjk.qlh.system.service.IRoleDepartmentService;
import com.ossjk.qlh.system.service.IRolePermissionService;
import com.ossjk.qlh.system.service.IRoleService;
import com.ossjk.qlh.system.vo.RoleToUpdateVo;

import cn.hutool.core.util.ObjectUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 
 * Copyright © 2021 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.system.controller
 * @ClassName: RoleController
 * @Description: 角色表-控制器
 * @author: chair
 * @date: 2021年1月6日 上午11:22:41
 */
@Api(tags = "角色表")
@LogModule(value = "角色管理")
@RestController
@RequestMapping("/system/role")
public class RoleController extends BaseController {

	@Autowired
	private IRoleService iRoleService;
	@Autowired
	private IRolePermissionService iRolePermissionService;
	@Autowired
	private IRoleDepartmentService iRoleDepartmentService;

	@LogOperation("列表")
	@RequiresPermissions(value = { "00000010" })
	@ApiOperation(value = "列表")
	@GetMapping("/list")
	public ResponseBean<List<Role>> list() {
		return ResponseBean.Success(iRoleService.list(new QueryWrapper<Role>().orderByAsc("level", "sort")));
	}

	@LogOperation("添加")
	@RequiresPermissions(value = { "000000100005" })
	@ApiOperation(value = "添加")
	@PostMapping("/save")
	public ResponseBean save(@RequestBody RoleDto recode) {
		// 防止角色编码重复
		Role role = iRoleService.getOne(new QueryWrapper<Role>().eq("code", recode.getCode()));
		if (ObjectUtil.isNotEmpty(role)) {
			return ResponseBean.Fail("角色标识	重复");
		}
		if (iRoleService.saveDto(recode)) {
			return ResponseBean.Success();
		} else {
			return ResponseBean.Fail();
		}
	}

	@LogOperation("去编辑")
	@RequiresPermissions(value = { "000000100010" })
	@ApiOperation(value = "去编辑")
	@GetMapping("/toUpdate")
	public ResponseBean<RoleToUpdateVo> toUpdate(@ApiParam(value = "id") @RequestParam(name = "id") String id) {
		Role role = iRoleService.getById(id);
		// 查询角色授权
		QueryWrapper queryWrapperrp = new QueryWrapper();
		queryWrapperrp.eq("rid", role.getId());
		List<RolePermission> rolePermissions = iRolePermissionService.list(queryWrapperrp);

		// 查询角色部门
		QueryWrapper queryWrapperrd = new QueryWrapper();
		queryWrapperrd.eq("rid", role.getId());
		RoleDepartment roleDepartment = iRoleDepartmentService.getOne(queryWrapperrd);

		RoleToUpdateVo roleToUpdateVo = new RoleToUpdateVo();
		roleToUpdateVo.setRole(role);
		roleToUpdateVo.setRolePermissions(rolePermissions);
		roleToUpdateVo.setRoleDepartment(roleDepartment);
		if (ObjectUtil.isNotEmpty(role)) {
			return ResponseBean.Success(roleToUpdateVo);
		} else {
			return ResponseBean.Fail();
		}
	}

	@LogOperation("取回绩效公式")
	@ApiOperation(value = "取回绩效公式")
	@GetMapping("/jxfn")
	public ResponseBean<RoleToUpdateVo> toGetJxfn(@ApiParam(value = "rcode") @RequestParam(name = "rcode") String rcode) {
		return ResponseBean.Success(iRoleService.selectJXFMByRname(rcode));
	}


	@LogOperation("编辑")
	@RequiresPermissions(value = { "000000100010" })
	@ApiOperation(value = "编辑")
	@PutMapping("/update")
	public ResponseBean update(@RequestBody RoleDto recode) {
		if (iRoleService.updateDtoById(recode)) {
			return ResponseBean.Success();
		} else {
			return ResponseBean.Fail();
		}
	}

	@LogOperation("删除")
	@RequiresPermissions(value = { "000000100015" })
	@ApiOperation(value = "删除")
	@DeleteMapping("/remove")
	public ResponseBean remove(@ApiParam(value = "ids") @RequestParam(name = "ids") String[] ids) {
		if (iRoleService.removeByIds(Arrays.asList(ids))) {
			return ResponseBean.Success();
		} else {
			return ResponseBean.Fail();
		}
	}
}
