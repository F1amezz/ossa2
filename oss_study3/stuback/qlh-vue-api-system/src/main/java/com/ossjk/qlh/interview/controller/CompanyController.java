package com.ossjk.qlh.interview.controller;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ossjk.core.base.controller.BaseController;
import com.ossjk.core.vo.ResponseBean;
import com.ossjk.qlh.interview.entity.Company;
import com.ossjk.qlh.interview.service.ICompanyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * Copyright  2022-06-06 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.study3.controller
 * @ClassName: CompanyController
 * @Description: Company-控制器
 * @author: Rick.yang
 * @date:  2022-06-06 14:48:32 
 */
@Api(tags = "企业表")
@RestController
@RequestMapping("/interview/company")
public class CompanyController extends BaseController {
	
	@Autowired
	private ICompanyService iCompanyService;

	@ApiOperation(value = "列表")
	//@RequiresPermissions("")
	@GetMapping("/list")
	public ResponseBean<Page<Company>> list(Page page) {
		QueryWrapper<Company> queryWrapper = new QueryWrapper<Company>();
		return ResponseBean.Success(iCompanyService.page(page,queryWrapper));
	}

	@ApiOperation(value = "感应name列表")
	//@RequiresPermissions("")
	@GetMapping("/listIntraNm")
	public ResponseBean<List<Company>> listIntraName(@ApiParam(value = "name") @RequestParam(name = "name") String name) {
		return ResponseBean.Success(iCompanyService.interaCname(name) );
	}

	@ApiOperation(value = "添加")
	//@RequiresPermissions("")
	@PostMapping("/save")
	public ResponseBean save(@RequestBody Company record) {
		if (iCompanyService.save(record)) {
			return ResponseBean.Success();
		} else {
			return ResponseBean.Fail();
		}
	}

	@ApiOperation(value = "去编辑")
	//@RequiresPermissions("")
	@GetMapping("/toUpdate")
	public ResponseBean<Company> toUpdate(@ApiParam(value = "id") @RequestParam(name = "id") String id) {
		Company company = iCompanyService.getById(id);
		if (ObjectUtil.isNotNull(company)) {
			return ResponseBean.Success(company);
		} else {
			return ResponseBean.Fail();
		}
	}


	@ApiOperation(value = "编辑")
	//@RequiresPermissions("")
	@PutMapping("/update")
	public ResponseBean update(@RequestBody Company record) {
		if (iCompanyService.updateById(record)) {
			return ResponseBean.Success();
		} else {
			return ResponseBean.Fail();
		}
	}

	@ApiOperation(value = "删除")
	//@RequiresPermissions("")
	@DeleteMapping("/remove")
	public ResponseBean remove(@ApiParam(value = "ids") @RequestParam(name = "ids") String[] ids) {
		if (iCompanyService.removeByIds(Arrays.asList(ids))) {
			return ResponseBean.Success();
		} else {
			return ResponseBean.Fail();
		}
	}
}
