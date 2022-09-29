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
import com.ossjk.qlh.system.service.IDepartmentService;

import cn.hutool.core.util.ObjectUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 
 * Copyright © 2021 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.components.controller
 * @ClassName: DepartmentComponentContrller
 * @Description: 部门组件-控制器
 * @author: Jason
 * @date: 2021年1月30日 下午11:50:13
 */
@Api(tags = "部门组件-控制器")
@RestController
@RequestMapping("/components/departmentComponent")
public class DepartmentComponentContrller extends BaseController {
	@Autowired
	private IDepartmentService iDepartmentService;

	@ApiOperation(value = "列表")
	@GetMapping("/list")
	public ResponseBean<List<Department>> list() {
		QueryWrapper<Department> queryWrapper = new QueryWrapper<Department>();
		queryWrapper.orderByAsc("level");
		queryWrapper.orderByAsc("sort");
		queryWrapper.orderByAsc("crtm");
		// 查询所有的部门
		List<Department> list = iDepartmentService.list(queryWrapper);
		return ResponseBean.Success(list);
	}

	@ApiOperation(value = "根据ids查询")
	@GetMapping("/getByIds")
	public ResponseBean<List<Department>> getByIds(
			@ApiParam(value = "ids") @RequestParam(name = "ids", required = false) String[] ids) {
		List<Department> departments = null;
		if (ObjectUtil.isNotEmpty(ids)) {
			departments = new ArrayList<Department>();
			for (String id : ids) {
				departments.add(iDepartmentService.getById(id));
			}
		}
		return ResponseBean.Success(departments);
	}

}
