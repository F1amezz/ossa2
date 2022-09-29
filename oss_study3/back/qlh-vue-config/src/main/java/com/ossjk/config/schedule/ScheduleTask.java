package com.ossjk.config.schedule;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 
 * Copyright © 2021 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.config.schedule
 * @ClassName: ScheduleTask
 * @Description: 调度器任务
 * @author: chair
 * @date: 2021年8月9日 下午3:18:34
 */
@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleTask implements Serializable {
	/**
	 * 主键
	 */
	private String id;
	/**
	 * 任务名称
	 */
	private String name;
	/**
	 * spring bean名称
	 */
	private String beanname;
	/**
	 * 方法名称
	 */
    private String method;
	/**
	 * 参数
	 */
	private String params;
	/**
	 * 状态 1-正常、2-暂停
	 */
	private Integer state;
	/**
	 * 是否保存日志 1-是、2-否
	 */
	private Integer islog;
	/**
	 * cron表达式
	 */
	private String cron;
}
