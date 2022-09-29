package com.ossjk.qlh.schedule.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.ossjk.core.base.entity.BaseEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Copyright 2021-08-09 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.schedule.entity
 * @ClassName: Schedulejob
 * @Description: 定时任务-实体类
 * @author: chair
 * @date: 2021-08-09 14:22:48
 */
@ApiModel(value = "定时任务")
@Data
public class Schedulejob extends BaseEntity<Schedulejob> {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@ApiModelProperty(value = "主键", required = true)
	@TableId("id")
	private String id;
	/**
	 * 任务名称
	 */
	@ApiModelProperty(value = "任务名称", required = true)
	@TableField("name")
	private String name;
	/**
	 * spring bean名称
	 */
	@ApiModelProperty(value = "spring bean名称", required = true)
	@TableField("beanname")
	private String beanname;
	/**
	 * 方法
	 */
	@ApiModelProperty(value = "方法", required = true)
	@TableField("method")
	private String method;
	/**
	 * 参数
	 */
	@ApiModelProperty(value = "参数")
	@TableField("params")
	private String params;
	/**
	 * 状态 1-正常、2-暂停
	 */
	@ApiModelProperty(value = "状态 1-正常、2-暂停", required = true)
	@TableField("state")
	private Integer state;
	/**
	 * 是否保存日志 1-是、2-否
	 */
	@ApiModelProperty(value = "是否保存日志 1-是、2-否", required = true)
	@TableField("islog")
	private Integer islog;
	/**
	 * cron表达式
	 */
	@ApiModelProperty(value = "cron表达式", required = true)
	@TableField("cron")
	private String cron;

	@Override
	protected Serializable pkVal() {
		return id;
	}

}
