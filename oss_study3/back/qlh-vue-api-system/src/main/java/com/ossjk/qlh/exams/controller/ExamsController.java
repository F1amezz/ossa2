package com.ossjk.qlh.exams.controller;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ossjk.core.base.controller.BaseController;
import com.ossjk.core.vo.ResponseBean;
import com.ossjk.qlh.exams.entity.Exams;
import com.ossjk.qlh.exams.service.IExamsService;
import com.ossjk.qlh.exams.vo.ExamsVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * Copyright  2022-06-14 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.exams.controller
 * @ClassName: ExamsController
 * @Description: Exams-控制器
 * @author: 
 * @date:  2022-06-14 11:31:17 
 */
@Api(tags = "考试表")
@RestController
@RequestMapping("/exams/exams")
public class ExamsController extends BaseController {
	
	@Autowired
	private IExamsService iExamsService;

	@ApiOperation(value = "列表")
	//@RequiresPermissions("")
	@GetMapping("/list")
	public ResponseBean<Page<ExamsVo>> list(Page page) {

		QueryWrapper<Exams> queryWrapper = new QueryWrapper<Exams>();
		queryWrapper.orderByDesc("crtm");
		Page<ExamsVo> examsVoPage = iExamsService.selectExamVo(page, queryWrapper);



		return ResponseBean.Success(examsVoPage);
	}

	@ApiOperation(value = "添加")
	//@RequiresPermissions("")
	@PostMapping("/save")
	public ResponseBean save(@RequestBody Exams record) {
		if (iExamsService.save(record)) {
			return ResponseBean.Success();
		} else {
			return ResponseBean.Fail();
		}
	}

	@ApiOperation(value = "去编辑")
	//@RequiresPermissions("")
	@GetMapping("/toUpdate")
	public ResponseBean<Exams> toUpdate(@ApiParam(value = "id") @RequestParam(name = "id") String id) {
		Exams exams = iExamsService.getById(id);
		if (ObjectUtil.isNotNull(exams)) {
			return ResponseBean.Success(exams);
		} else {
			return ResponseBean.Fail();
		}
	}


	@ApiOperation(value = "编辑")
	//@RequiresPermissions("")
	@PutMapping("/update")
	public ResponseBean update(@RequestBody Exams record) {
		if (iExamsService.updateById(record)) {
			return ResponseBean.Success();
		} else {
			return ResponseBean.Fail();
		}
	}

	@ApiOperation(value = "删除")
	//@RequiresPermissions("")
	@DeleteMapping("/remove")
	public ResponseBean remove(@ApiParam(value = "ids") @RequestParam(name = "ids") String[] ids) {
		if (iExamsService.removeByIds(Arrays.asList(ids))) {
			return ResponseBean.Success();
		} else {
			return ResponseBean.Fail();
		}
	}
}
