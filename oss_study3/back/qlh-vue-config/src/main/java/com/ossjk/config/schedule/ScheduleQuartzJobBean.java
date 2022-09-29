package com.ossjk.config.schedule;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.concurrent.ExecutorService;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import com.ossjk.core.exception.QlhRunTimeException;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.thread.ExecutorBuilder;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.extra.spring.SpringUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * Copyright © 2021 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.config.schedule
 * @ClassName: ScheduleQuartzJobBean 
 * @Description: 定时任务调用逻辑
 * @author: chair
 * @date: 2021年8月10日 上午9:24:17
 */
@Component
@Import(cn.hutool.extra.spring.SpringUtil.class)
@Slf4j
public class ScheduleQuartzJobBean extends QuartzJobBean {
	@Autowired
	private IScheduleLogCommonApi iScheduleLogCommonApi;

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		if(ObjectUtil.isEmpty(iScheduleLogCommonApi)) {
			throw new QlhRunTimeException("调度器日志Api注入失败，请注入后再使用。");
		}
		// 任务开始时间
		long start = System.currentTimeMillis();
		// 获取调度器参数
		ScheduleTask scheduleTask = (ScheduleTask) context.getMergedJobDataMap().get(ScheduleUtils.JOB_PARAMS_KEY);
		ScheduleTaskLog scheduleTaskLog = BeanUtil.copyProperties(scheduleTask, ScheduleTaskLog.class, "result", "error", "crtm", "jobid", "times");
		scheduleTaskLog.setJobid(scheduleTask.getId());
		scheduleTaskLog.setCrtm(new Date());
		try {
			// 执行任务
			log.info("任务准备执行，任务ID：" + scheduleTask.getId() + "，任务名称：" + scheduleTask.getName() + "。");
			Object target = SpringUtil.getBean(scheduleTask.getBeanname());
			if (ObjectUtil.isEmpty(target)) {
				throw new QlhRunTimeException("任务ID：" + scheduleTask.getId() + "，任务名称：" + scheduleTask.getName() + "。beanName：" + scheduleTask.getBeanname() + "获取失败。");
			}
			Method method= ReflectUtil.getMethod(target.getClass(), scheduleTask.getMethod(), String.class);
			if (ObjectUtil.isEmpty(target)) {
				throw new QlhRunTimeException("任务ID：" + scheduleTask.getId() + "，任务名称：" + scheduleTask.getName() + "，beanName：" + scheduleTask.getBeanname() + "。methodName："+scheduleTask.getMethod()+"获取失败。");
			}
			ReflectUtil.invoke(target, method, scheduleTask.getParams());
			long end = System.currentTimeMillis();
			// 执行结果 1-成功、2-失败
			scheduleTaskLog.setResult(1);
		} catch (Exception e) {
			// 执行结果 1-成功、2-失败
			scheduleTaskLog.setResult(2);
			scheduleTaskLog.setError(e.getMessage());
			log.error("任务执行失败，任务ID：" + scheduleTask.getId() + "，任务名称：" + scheduleTask.getName() + "。", e);
		}finally {
			long end = System.currentTimeMillis();
			//算出耗时
			scheduleTaskLog.setTimes((end - start));
			log.info("任务执行结束，任务ID：" + scheduleTask.getId() + "，任务名称：" + scheduleTask.getName() + "，耗时："+scheduleTaskLog.getTimes()+"ms。");
			//保存日志
			//是否保存日志 1-是、2-否
			if(ObjectUtil.equal(scheduleTask.getIslog(), 1)) {
				iScheduleLogCommonApi.saveLog(scheduleTaskLog);
			}
		}

	}

}
