package com.ossjk.qlh.system.controller;

import java.util.Arrays;

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
import com.ossjk.core.system.annotation.LogModule;
import com.ossjk.core.system.annotation.LogOperation;
import com.ossjk.core.vo.ResponseBean;
import com.ossjk.qlh.system.entity.Dictionary;
import com.ossjk.qlh.system.service.IDictionaryService;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * Copyright 2021-01-16 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.system.controller
 * @ClassName: DictionaryController
 * @Description: 数据字典-控制器
 * @author: Jason
 * @date: 2021-01-16 22:11:02
 */
@Api(tags = "数据字典")
@LogModule(value = "数据字典")
@RestController
@RequestMapping("/system/dictionary")
public class DictionaryController extends BaseController {

	@Autowired
	private IDictionaryService iDictionaryService;

	@LogOperation("列表")
	@RequiresPermissions(value = { "00000020" })
	@ApiOperation(value = "列表")
	@GetMapping("/list")
	public ResponseBean<Page<Dictionary>> list(
			@ApiParam(value = "字典名称") @RequestParam(name = "name", required = false) String name, 
			@ApiParam(value = "字典编号") @RequestParam(name = "dkey", required = false) String dkey, 
			@ApiParam(value = "是否禁用：1-是、2-否") @RequestParam(name = "isdisable", required = false) Integer isdisable, 
			Page page) {
		QueryWrapper<Dictionary> queryWrapper = new QueryWrapper<Dictionary>();
		if (StrUtil.isNotBlank(name)) {
			queryWrapper.like("name", name);
		}
		if (StrUtil.isNotBlank(dkey)) {
			queryWrapper.like("dkey", dkey);
		}
		if (ObjectUtil.isNotEmpty(isdisable)) {
			queryWrapper.eq("isdisable", isdisable);
		}
		// 类型：1-选项、2-属性值、3-系统属性
		queryWrapper.eq("type", "2");
		queryWrapper.orderByDesc("crtm");
		return ResponseBean.Success(iDictionaryService.page(page, queryWrapper));
	}

	@LogOperation("添加")
	@RequiresPermissions(value = { "000000200005" })
	@ApiOperation(value = "添加")
	@PostMapping("/save")
	public ResponseBean save(@RequestBody Dictionary recode) {
		// 防止数据字典编码重复
		Dictionary dictionary = iDictionaryService.getOne(new QueryWrapper<Dictionary>().eq("dkey", recode.getDkey()));
		if (ObjectUtil.isNotEmpty(dictionary)) {
			return ResponseBean.Fail("数据字典标识	重复");
		}

		// 类型：1-选项、2-属性值、3-系统属性
		recode.setType(2);
		if (iDictionaryService.save(recode)) {
			return ResponseBean.Success();
		} else {
			return ResponseBean.Fail();
		}
	}

	@LogOperation("去编辑")
	@RequiresPermissions(value = { "000000200010" })
	@ApiOperation(value = "去编辑")
	@GetMapping("/toUpdate")
	public ResponseBean<Dictionary> toUpdate(@ApiParam(value = "id") @RequestParam(name = "id") String id) {
		Dictionary dictionary = iDictionaryService.getById(id);
		if (ObjectUtil.isNotNull(dictionary)) {
			return ResponseBean.Success(dictionary);
		} else {
			return ResponseBean.Fail();
		}
	}

	@LogOperation("编辑")
	@RequiresPermissions(value = { "000000200010" })
	@ApiOperation(value = "编辑")
	@PutMapping("/update")
	public ResponseBean update(@RequestBody Dictionary recode) {
		if (iDictionaryService.updateById(recode)) {
			return ResponseBean.Success();
		} else {
			return ResponseBean.Fail();
		}
	}

	@LogOperation("删除")
	@RequiresPermissions(value = { "000000200015" })
	@ApiOperation(value = "删除")
	@DeleteMapping("/remove")
	public ResponseBean remove(@ApiParam(value = "ids", required = true) @RequestParam(name = "ids") String[] ids) {
		if (iDictionaryService.removeByIds(Arrays.asList(ids))) {
			return ResponseBean.Success();
		} else {
			return ResponseBean.Fail();
		}
	}
}
