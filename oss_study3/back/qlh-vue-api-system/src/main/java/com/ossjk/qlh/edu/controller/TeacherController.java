package com.ossjk.qlh.edu.controller;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ossjk.core.base.controller.BaseController;
import com.ossjk.core.vo.ResponseBean;
import com.ossjk.qlh.edu.entity.Teacher;
import com.ossjk.qlh.edu.service.ITeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

/**
 * Copyright  2022-06-30 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.edu.controller
 * @ClassName: TeacherController
 * @Description: Teacher-控制器
 * @author: Rick.yang
 * @date:  2022-06-30 11:06:52 
 */
@Api(tags = "教师")
@RestController
@RequestMapping("/edu/teacher")
public class TeacherController extends BaseController {
	
	@Autowired
	private ITeacherService iTeacherService;

	@ApiOperation(value = "列表")
//	@RequiresPermissions("")
	@GetMapping("/list")
	public ResponseBean<Page<Teacher>> list(Page page) {
		QueryWrapper<Teacher> queryWrapper = new QueryWrapper<Teacher>();
		return ResponseBean.Success(iTeacherService.page(page,queryWrapper));
	}

	@ApiOperation(value = "添加")
//	@RequiresPermissions("")
	@PostMapping("/save")
	public ResponseBean save(@RequestBody Teacher record) {
		if (iTeacherService.save(record)) {
			return ResponseBean.Success();
		} else {
			return ResponseBean.Fail();
		}
	}

	@ApiOperation(value = "去编辑")
//	@RequiresPermissions("")
	@GetMapping("/toUpdate")
	public ResponseBean<Teacher> toUpdate(@ApiParam(value = "id") @RequestParam(name = "id") String id) {
		Teacher teacher = iTeacherService.getById(id);
		if (ObjectUtil.isNotNull(teacher)) {
			return ResponseBean.Success(teacher);
		} else {
			return ResponseBean.Fail();
		}
	}


	@ApiOperation(value = "编辑")
//	@RequiresPermissions("")
	@PutMapping("/update")
	public ResponseBean update(@RequestBody Teacher record) {
		if (iTeacherService.updateById(record)) {
			return ResponseBean.Success();
		} else {
			return ResponseBean.Fail();
		}
	}

	@ApiOperation(value = "删除")
//	@RequiresPermissions("")
	@DeleteMapping("/remove")
	public ResponseBean remove(@ApiParam(value = "ids") @RequestParam(name = "ids") String[] ids) {
		if (iTeacherService.removeByIds(Arrays.asList(ids))) {
			return ResponseBean.Success();
		} else {
			return ResponseBean.Fail();
		}
	}
}
