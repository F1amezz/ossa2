package com.ossjk.qlh.schedule.controller;

import java.util.Arrays;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ossjk.core.base.controller.BaseController;
import com.ossjk.core.vo.ResponseBean;
import com.ossjk.qlh.schedule.entity.Schedulejoblog;
import com.ossjk.qlh.schedule.service.ISchedulejoblogService;
import com.ossjk.qlh.sms.service.impl.EmailServiceImpl;

import cn.hutool.core.util.ObjectUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/** 
 * Copyright  2021-08-09 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.schedule.controller
 * @ClassName: SchedulejoblogController
 * @Description: Schedulejoblog-控制器
 * @author: chair
 * @date:  2021-08-09 14:22:48 
 */
@Api(tags = "定时任务日志")
@RestController
@RequestMapping("/schedule/schedulejoblog")
public class SchedulejoblogController extends BaseController {
	
	@Autowired
	private ISchedulejoblogService iSchedulejoblogService;
	
	private Scheduler scheduler;
	
	@ApiOperation(value = "列表")
	@RequiresPermissions("00250005")
	@GetMapping("/list")
	public ResponseBean<Page<Schedulejoblog>> list(@ApiParam(value = "bean") @RequestParam(name = "beanname", required = false)String beanname, Page page) {
		QueryWrapper<Schedulejoblog> queryWrapper = new QueryWrapper<Schedulejoblog>();
		if (ObjectUtil.isNotEmpty(beanname)) {
			queryWrapper.like("beanname", beanname);
		}
		queryWrapper.orderByDesc("crtm");
		return ResponseBean.Success(iSchedulejoblogService.page(page,queryWrapper));
	}

	 

	@ApiOperation(value = "去编辑")
	@RequiresPermissions("002500050005")
	@GetMapping("/toUpdate")
	public ResponseBean<Schedulejoblog> toUpdate(@ApiParam(value = "id") @RequestParam(name = "id") String id) {
		Schedulejoblog schedulejoblog = iSchedulejoblogService.getById(id);
		if (ObjectUtil.isNotNull(schedulejoblog)) {
			return ResponseBean.Success(schedulejoblog);
		} else {
			return ResponseBean.Fail();
		}
	}


	 

	@ApiOperation(value = "删除")
	@RequiresPermissions("002500050000")
	@DeleteMapping("/remove")
	public ResponseBean remove(@ApiParam(value = "ids") @RequestParam(name = "ids") String[] ids) {
		if (iSchedulejoblogService.removeByIds(Arrays.asList(ids))) {
			return ResponseBean.Success();
		} else {
			return ResponseBean.Fail();
		}
	}
}
