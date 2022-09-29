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
import com.ossjk.qlh.system.dto.OptionDictionaryDto;
import com.ossjk.qlh.system.entity.Dictionary;
import com.ossjk.qlh.system.entity.Dictionarydata;
import com.ossjk.qlh.system.service.IDictionarydataService;
import com.ossjk.qlh.system.service.IOptionDictionaryService;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * Copyright 2021-01-12 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.system.controller
 * @ClassName: OptionDictionaryController
 * @Description: 选项字典-控制器
 * @author: Jason
 * @date: 2021-01-12 11:01:59
 */
@Api(tags = "选项字典")
@LogModule(value = "字典选项")
@RestController
@RequestMapping("/system/optionDictionary")
public class OptionDictionaryController extends BaseController {

	@Autowired
	private IDictionarydataService dictionarydataService;
	@Autowired
	private IOptionDictionaryService optionDictionaryService;

	@LogOperation("列表")
	@RequiresPermissions(value = { "00000025" })
	@ApiOperation(value = "列表")
	@GetMapping("/list")
	public ResponseBean<Page<Dictionary>> list(String name, String dkey, Integer isdisable, Page page) {
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
		queryWrapper.eq("type", "1");
		queryWrapper.orderByDesc("crtm");
		return ResponseBean.Success(optionDictionaryService.page(page, queryWrapper));
	}

	@LogOperation("添加")
	@RequiresPermissions(value = { "000000250000" })
	@ApiOperation(value = "添加")
	@PostMapping("/save")
	public ResponseBean save(@RequestBody Dictionary recode) throws Exception {
		// 防止数据字典编码重复
		Dictionary dictionary = optionDictionaryService.getOne(new QueryWrapper<Dictionary>().eq("dkey", recode.getDkey()));
		if (ObjectUtil.isNotEmpty(dictionary)) {
			return ResponseBean.Fail("数据字典标识	重复");
		}
		if (optionDictionaryService.save(recode)) {
			return ResponseBean.Success();
		} else {
			return ResponseBean.Fail();
		}
	}

	@LogOperation("去编辑")
	@RequiresPermissions(value = { "000000250005" })
	@ApiOperation(value = "去编辑")
	@GetMapping("/toUpdate")
	public ResponseBean<Dictionary> toUpdate(@ApiParam(value = "id") @RequestParam(name = "id") String id) {
		Dictionary dictionary = optionDictionaryService.getById(id);
		if (ObjectUtil.isNotNull(dictionary)) {
			return ResponseBean.Success(dictionary);
		} else {
			return ResponseBean.Fail();
		}
	}

	@LogOperation("编辑")
	@RequiresPermissions(value = { "000000250005" })
	@ApiOperation(value = "编辑")
	@PutMapping("/update")
	public ResponseBean update(@RequestBody Dictionary recode) throws Exception {
		if (optionDictionaryService.updateById(recode)) {
			return ResponseBean.Success();
		} else {
			return ResponseBean.Fail();
		}
	}

	@LogOperation("删除")
	@RequiresPermissions(value = { "000000250010" })
	@ApiOperation(value = "删除")
	@DeleteMapping("/remove")
	public ResponseBean remove(@ApiParam(value = "ids") @RequestParam(name = "ids") String[] ids) {
		if (optionDictionaryService.removeByIds(Arrays.asList(ids))) {
			// 并且把选项数据删除
			QueryWrapper queryWrapper = new QueryWrapper();
			queryWrapper.in("did", Arrays.asList(ids));
			dictionarydataService.remove(queryWrapper);
			return ResponseBean.Success();
		} else {
			return ResponseBean.Fail();
		}
	}

	@LogOperation("去编辑选项")
	@RequiresPermissions(value = { "000000250015" })
	@ApiOperation(value = "去编辑选项")
	@GetMapping("/toUpdateOption")
	public ResponseBean updateOption(@ApiParam(value = "did") @RequestParam(name = "did") String did) {
		return ResponseBean.Success(dictionarydataService.list(new QueryWrapper<Dictionarydata>().eq("did", did).orderByAsc("sort")));
	}

	@LogOperation("编辑选项")
	@RequiresPermissions(value = { "000000250015" })
	@ApiOperation(value = "编辑选项")
	@PutMapping("/updateOption")
	public ResponseBean updateOption(@RequestBody() OptionDictionaryDto optionDictionaryDto) {
		if (dictionarydataService.updateOptionDictionaryDto(optionDictionaryDto)) {
			return ResponseBean.Success();
		} else {
			return ResponseBean.Fail();
		}
	}
}
