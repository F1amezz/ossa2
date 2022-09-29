package com.ossjk.qlh.sms.controller;

import java.util.Arrays;

import org.apache.shiro.authz.annotation.Logical;
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
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ossjk.core.base.controller.BaseController;
import com.ossjk.core.vo.ResponseBean;
import com.ossjk.qlh.sms.entity.Templates;
import com.ossjk.qlh.sms.service.ITemplatesService;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * Copyright 2021-04-27 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.sms.controller
 * @ClassName: TemplatesController
 * @Description: 信息模版-控制器
 * @author: chair
 * @date: 2021-04-27 10:23:22
 */
@Api(tags = "信息模版")
@RestController
@RequestMapping("/sms/templates")
public class TemplatesController extends BaseController {

	@Autowired
	private ITemplatesService iTemplatesService;

	@ApiOperation(value = "列表")
	@RequiresPermissions("002000150000")
	@GetMapping("/list")
	public ResponseBean<Page<Templates>> list(
			@ApiParam(value = "模版标识") @RequestParam(name = "code", required = false) String code,
			@ApiParam(value = "模版名称") @RequestParam(name = "name", required = false) String name,Page page) {
		QueryWrapper<Templates> queryWrapper = new QueryWrapper<Templates>();
		if(StrUtil.isNotBlank(code)) {
			queryWrapper.like("code", code);
		}
		if(StrUtil.isNotBlank(name)) {
			queryWrapper.like("name", name);
		}
		queryWrapper.orderByDesc("crtm");
		return ResponseBean.Success(iTemplatesService.page(page, queryWrapper));
	}

	@ApiOperation(value = "添加")
	@RequiresPermissions("002000150000")
	@PostMapping("/save")
	public ResponseBean save(@RequestBody Templates record) {
		// 模版标识查重
		Templates tmp = iTemplatesService.getOne(new QueryWrapper<Templates>().eq("code", record.getCode()));
		if (ObjectUtil.isNotEmpty(tmp)) {
			return ResponseBean.Fail("模版标识已存在。");
		}
		if (iTemplatesService.save(record)) {
			return ResponseBean.Success();
		} else {
			return ResponseBean.Fail();
		}
	}

	@ApiOperation(value = "去编辑")
	@RequiresPermissions(value = { "002000150005", "002000150015" }, logical = Logical.OR)
	@GetMapping("/toUpdate")
	public ResponseBean<Templates> toUpdate(@ApiParam(value = "id") @RequestParam(name = "id") String id) {
		Templates templates = iTemplatesService.getById(id);
		if (ObjectUtil.isNotNull(templates)) {
			return ResponseBean.Success(templates);
		} else {
			return ResponseBean.Fail();
		}
	}

	@ApiOperation(value = "编辑")
	@RequiresPermissions("002000150005")
	@PutMapping("/update")
	public ResponseBean update(@RequestBody Templates record) {
		if (iTemplatesService.updateById(record)) {
			return ResponseBean.Success();
		} else {
			return ResponseBean.Fail();
		}
	}

	@ApiOperation(value = "删除")
	@RequiresPermissions("002000150010")
	@DeleteMapping("/remove")
	public ResponseBean remove(@ApiParam(value = "ids") @RequestParam(name = "ids") String[] ids) {
		if (iTemplatesService.removeByIds(Arrays.asList(ids))) {
			return ResponseBean.Success();
		} else {
			return ResponseBean.Fail();
		}
	}
}
