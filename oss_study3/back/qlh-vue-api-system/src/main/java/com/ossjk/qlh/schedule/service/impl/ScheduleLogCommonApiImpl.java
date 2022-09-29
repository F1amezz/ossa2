package com.ossjk.qlh.schedule.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ossjk.config.schedule.IScheduleLogCommonApi;
import com.ossjk.config.schedule.ScheduleTaskLog;
import com.ossjk.qlh.schedule.entity.Schedulejoblog;
import com.ossjk.qlh.schedule.service.ISchedulejobService;
import com.ossjk.qlh.schedule.service.ISchedulejoblogService;

import cn.hutool.core.bean.BeanUtil;

/**
 * 
 * Copyright © 2021 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.schedule.service.impl
 * @ClassName: ScheduleLogCommonApiImpl 
 * @Description: 调度器日志公共接口实现类
 * @author: chair
 * @date: 2021年8月9日 下午5:21:42
 */
@Service
public class ScheduleLogCommonApiImpl implements IScheduleLogCommonApi {
	@Autowired
	private ISchedulejoblogService iSchedulejoblogService;

	@Override
	public boolean saveLog(ScheduleTaskLog scheduleTaskLog) {
		Schedulejoblog schedulejoblog = BeanUtil.copyProperties(scheduleTaskLog, Schedulejoblog.class);
		return iSchedulejoblogService.save(schedulejoblog);
	}

}
