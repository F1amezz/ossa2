package com.ossjk.config.schedule;

import java.io.Serializable;
import java.util.Date;

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
 * @ClassName: ScheduleTaskLog 
 * @Description: 调度任务日志
 * @author: chair
 * @date: 2021年8月10日 上午9:33:33
 */
@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleTaskLog implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 任务id
	 */
	private String jobid;
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
	 * 执行结果 1-成功、2-失败
	 */
	private Integer result;
	/**
	 * 错误原因
	 */

	private String error;
	/**
	 * 执行耗时
	 */
	private Long times;
	/**
	 * 执行时间
	 */
	private Date crtm;

}
