package com.ossjk.qlh.schedule.controller;

import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.quartz.CronTrigger;
import org.quartz.Scheduler;
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
import com.ossjk.config.schedule.ScheduleTask;
import com.ossjk.config.schedule.ScheduleUtils;
import com.ossjk.core.base.controller.BaseController;
import com.ossjk.core.vo.ResponseBean;
import com.ossjk.qlh.schedule.entity.Schedulejob;
import com.ossjk.qlh.schedule.service.ISchedulejobService;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * Copyright 2021-08-09 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.schedule.controller
 * @ClassName: SchedulejobController
 * @Description: Schedulejob-控制器
 * @author: chair
 * @date: 2021-08-09 14:22:48
 */
@Api(tags = "定时任务")
@RestController
@RequestMapping("/schedule/schedulejob")
public class SchedulejobController extends BaseController {

	@Autowired
	private ISchedulejobService iSchedulejobService;
	@Autowired
	private Scheduler scheduler;

	/**
	 * 项目启动时，初始化定时器
	 */
	@PostConstruct
	public void init() {
		// new QueryWrapper<Schedulejob>().eq("state", 1)
		List<Schedulejob> scheduleJobList = iSchedulejobService.list();
		if (ObjectUtil.isNotEmpty(scheduleJobList)) {
			for (Schedulejob schedulejob : scheduleJobList) {
				ScheduleTask scheduleTask = BeanUtil.copyProperties(schedulejob, ScheduleTask.class);
				CronTrigger cronTrigger = ScheduleUtils.getCronTrigger(scheduler, scheduleTask.getId());
				// 如果不存在，则创建
				if (cronTrigger == null) {
					ScheduleUtils.createScheduleJob(scheduler, scheduleTask);
				} else {
					ScheduleUtils.updateScheduleJob(scheduler, scheduleTask);
				}
			}
		}
	}

	@ApiOperation(value = "列表")
	@RequiresPermissions("00250000")
	@GetMapping("/list")
	public ResponseBean<Page<Schedulejob>> list(@ApiParam(value = "任务名称") @RequestParam(name = "name", required = false)String name, @ApiParam(value = "bean") @RequestParam(name = "beanname", required = false)String beanname, Page page) {
		QueryWrapper<Schedulejob> queryWrapper = new QueryWrapper<Schedulejob>();
		if (ObjectUtil.isNotEmpty(name)) {
			queryWrapper.like("name", name);
		}
		if (ObjectUtil.isNotEmpty(beanname)) {
			queryWrapper.like("beanname", beanname);
		}
		queryWrapper.orderByDesc("crtm");

		return ResponseBean.Success(iSchedulejobService.page(page, queryWrapper));
	}

	@ApiOperation(value = "添加")
	@RequiresPermissions("002500000000")
	@PostMapping("/save")
	public ResponseBean save(@RequestBody Schedulejob record) {
		if (iSchedulejobService.save(record)) {
			ScheduleTask scheduleTask = BeanUtil.copyProperties(record, ScheduleTask.class);
			ScheduleUtils.createScheduleJob(scheduler, scheduleTask);
			return ResponseBean.Success();
		} else {
			return ResponseBean.Fail();
		}
	}

	@ApiOperation(value = "去编辑")
	@RequiresPermissions("002500000005")
	@GetMapping("/toUpdate")
	public ResponseBean<Schedulejob> toUpdate(@ApiParam(value = "id") @RequestParam(name = "id") String id) {
		Schedulejob schedulejob = iSchedulejobService.getById(id);
		if (ObjectUtil.isNotNull(schedulejob)) {
			return ResponseBean.Success(schedulejob);
		} else {
			return ResponseBean.Fail();
		}
	}

	@ApiOperation(value = "编辑")
	@RequiresPermissions("002500000005")
	@PutMapping("/update")
	public ResponseBean update(@RequestBody Schedulejob record) {
		if (iSchedulejobService.updateById(record)) {
			ScheduleTask scheduleTask = BeanUtil.copyProperties(record, ScheduleTask.class);
			ScheduleUtils.updateScheduleJob(scheduler, scheduleTask);
			return ResponseBean.Success();
		} else {
			return ResponseBean.Fail();
		}
	}

	@ApiOperation(value = "删除")
	@RequiresPermissions("002500000010")
	@DeleteMapping("/remove")
	public ResponseBean remove(@ApiParam(value = "ids") @RequestParam(name = "ids") String[] ids) {
		if (iSchedulejobService.removeByIds(Arrays.asList(ids))) {
			for (String id : ids) {
				ScheduleUtils.deleteScheduleJob(scheduler, id);
			}
			return ResponseBean.Success();
		} else {
			return ResponseBean.Fail();
		}
	}

	@ApiOperation(value = "运行")
	@RequiresPermissions("002500000020")
	@PutMapping("/run")
	public ResponseBean run(@RequestBody Schedulejob record) {
		record = iSchedulejobService.getById(record.getId());
		ScheduleTask scheduleTask = BeanUtil.copyProperties(record, ScheduleTask.class);
		ScheduleUtils.run(scheduler, scheduleTask);
		return ResponseBean.Success();
	}

	@ApiOperation(value = "恢复")
	@RequiresPermissions("002500000025")
	@PutMapping("/resume")
	public ResponseBean resume(@RequestBody Schedulejob record) {
		record.setState(1);
		if (iSchedulejobService.updateById(record)) {
			record = iSchedulejobService.getById(record.getId());
			ScheduleTask scheduleTask = BeanUtil.copyProperties(record, ScheduleTask.class);
			ScheduleUtils.resumeJob(scheduler, scheduleTask.getId());
			return ResponseBean.Success();
		} else {
			return ResponseBean.Fail();
		}
	}

	@ApiOperation(value = "暂停")
	@RequiresPermissions("002500000030")
	@PutMapping("/pause")
	public ResponseBean pause(@RequestBody Schedulejob record) {
		record.setState(2);
		if (iSchedulejobService.updateById(record)) {
			ScheduleUtils.pauseJob(scheduler, record.getId());
			return ResponseBean.Success();
		} else {
			return ResponseBean.Fail();
		}
	}
}
