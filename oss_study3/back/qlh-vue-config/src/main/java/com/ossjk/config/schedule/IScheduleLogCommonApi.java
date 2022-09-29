package com.ossjk.config.schedule;

/**
 * 
 * Copyright © 2021 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.config.schedule
 * @ClassName: IScheduleLogCommonApi 
 * @Description: 调度器日志公共接口
 * @author: chair
 * @date: 2021年8月9日 下午5:21:48
 */
public interface IScheduleLogCommonApi {
	boolean saveLog(ScheduleTaskLog scheduleTaskLog);
}
