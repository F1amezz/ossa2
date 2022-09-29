package com.ossjk.qlh.schedule.entity;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Copyright 2021-08-09 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.schedule.entity
 * @ClassName: Schedulejoblog
 * @Description: 定时任务日志-实体类
 * @author: chair
 * @date: 2021-08-09 14:22:48
 */
@ApiModel(value = "定时任务日志")
@Data
public class Schedulejoblog extends Model<Schedulejoblog> {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@ApiModelProperty(value = "主键", required = true)
	@TableId("id")
	private String id;
	/**
	 * 任务id
	 */
	@ApiModelProperty(value = "任务id")
	@TableField("jobid")
	private String jobid;
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
	 * 执行结果 1-成功、2-失败
	 */
	@ApiModelProperty(value = "执行结果 1-成功、2-失败", required = true)
	@TableField("result")
	private Integer result;
	/**
	 * 错误原因
	 */
	@ApiModelProperty(value = "错误原因", required = true)
	@TableField("error")
	private String error;
	/**
	 * 执行耗时
	 */
	@ApiModelProperty(value = "执行耗时")
	@TableField("times")
	private Integer times;
	/**
	 * 执行时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value = "执行时间")
	@TableField("crtm")
	private Date crtm;

	@Override
	protected Serializable pkVal() {
		return id;
	}

}
