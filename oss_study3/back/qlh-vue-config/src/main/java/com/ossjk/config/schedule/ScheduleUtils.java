package com.ossjk.config.schedule;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;

import com.ossjk.core.exception.QlhRunTimeException;

/**
 * 
 * Copyright © 2021 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.config.schedule
 * @ClassName: ScheduleUtils
 * @Description: 定时任务工具类
 * @author: chair
 * @date: 2021年8月9日 下午2:52:13
 */
public class ScheduleUtils {

	public final static String JOB_NAME = "TASK_";

	public final static Integer JOB_STATE_NORMAL = 1;

	public final static Integer JOB_STATE_PAUSE = 2;

	public final static String JOB_PARAMS_KEY = "JOB_PARAMS_KEY";

	/**
	 * 获取触发器key
	 */
	public static TriggerKey getTriggerKey(String jobId) {
		return TriggerKey.triggerKey(JOB_NAME + jobId);
	}

	/**
	 * 获取jobKey
	 */
	public static JobKey getJobKey(String jobId) {
		return JobKey.jobKey(JOB_NAME + jobId);
	}

	/**
	 * 获取表达式触发器
	 */
	public static CronTrigger getCronTrigger(Scheduler scheduler, String jobId) {
		try {
			return (CronTrigger) scheduler.getTrigger(getTriggerKey(jobId));
		} catch (SchedulerException e) {
			throw new QlhRunTimeException("获取定时任务CronTrigger出现异常。");
		}
	}

	/**
	 * 创建定时任务
	 */
	public static void createScheduleJob(Scheduler scheduler, ScheduleTask scheduleTask) {
		try {
			// 构建job信息
			JobDetail jobDetail = JobBuilder.newJob(ScheduleQuartzJobBean.class).withIdentity(getJobKey(scheduleTask.getId())).build();

			// 表达式调度构建器
			CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(scheduleTask.getCron()).withMisfireHandlingInstructionDoNothing();

			// 按新的cronExpression表达式构建一个新的trigger
			CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(getTriggerKey(scheduleTask.getId())).withSchedule(scheduleBuilder).build();

			// 放入参数，运行时的方法可以获取
			jobDetail.getJobDataMap().put(JOB_PARAMS_KEY, scheduleTask);

			scheduler.scheduleJob(jobDetail, trigger);

			// 暂停任务
			if (scheduleTask.getState() == JOB_STATE_PAUSE) {
				pauseJob(scheduler, scheduleTask.getId());
			}
		} catch (SchedulerException e) {
			throw new QlhRunTimeException("创建定时任务失败。");
		}
	}

	/**
	 * 更新定时任务
	 */
	public static void updateScheduleJob(Scheduler scheduler, ScheduleTask scheduleTask) {
		try {
			TriggerKey triggerKey = getTriggerKey(scheduleTask.getId());

			// 表达式调度构建器
			CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(scheduleTask.getCron()).withMisfireHandlingInstructionDoNothing();

			CronTrigger trigger = getCronTrigger(scheduler, scheduleTask.getId());

			// 按新的cronExpression表达式重新构建trigger
			trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();

			// 参数
			trigger.getJobDataMap().put(JOB_PARAMS_KEY, scheduleTask);

			scheduler.rescheduleJob(triggerKey, trigger);

			// 暂停任务
			if (scheduleTask.getState() == JOB_STATE_PAUSE) {
				pauseJob(scheduler, scheduleTask.getId());
			}
		} catch (SchedulerException e) {
			throw new QlhRunTimeException("更新定时任务失败。");
		}
	}

	/**
	 * 暂停任务
	 */
	public static void pauseJob(Scheduler scheduler, String jobId) {
		try {
			scheduler.pauseJob(getJobKey(jobId));
		} catch (SchedulerException e) {
			throw new QlhRunTimeException("暂停定时任务失败。");
		}
	}

	/**
	 * 立即执行任务
	 */
	public static void run(Scheduler scheduler, ScheduleTask scheduleTask) {
		try {
			// 参数
			JobDataMap dataMap = new JobDataMap();
			dataMap.put(JOB_PARAMS_KEY, scheduleTask);
			scheduler.triggerJob(getJobKey(scheduleTask.getId()), dataMap);
		} catch (SchedulerException e) {
			throw new QlhRunTimeException("立即执行定时任务失败。");
		}
	}

	/**
	 * 恢复任务
	 */
	public static void resumeJob(Scheduler scheduler, String jobId) {
		try {
			scheduler.resumeJob(getJobKey(jobId));
		} catch (SchedulerException e) {
			throw new QlhRunTimeException("暂停定时任务失败。");
		}
	}

	/**
	 * 删除定时任务
	 */
	public static void deleteScheduleJob(Scheduler scheduler, String jobId) {
		try {
			scheduler.deleteJob(getJobKey(jobId));
		} catch (SchedulerException e) {
			throw new QlhRunTimeException("删除定时任务失败");
		}
	}

}