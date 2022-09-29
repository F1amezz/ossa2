package com.ossjk.qlh.system.entity;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.ossjk.core.base.entity.BaseEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Copyright 2020-12-14 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.system.entity
 * @ClassName: Loginfo
 * @Description: 日志信息-实体类
 * @author: chair
 * @date: 2020-12-14 22:06:30
 */
@ApiModel(value = "日志信息")
@Data
public class Loginfo extends BaseEntity<Loginfo> {

	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@ApiModelProperty(value = "", required = true)
	@TableId("id")
	private String id;
	/**
	 * 用户id
	 */
	@ApiModelProperty(value = "用户id")
	@TableField("uid")
	private String uid;
	/**
	 * 登录ip
	 */
	@ApiModelProperty(value = "登录ip")
	@TableField("ip")
	private String ip;
	/**
	 * 客户端
	 */
	@ApiModelProperty(value = "客户端")
	@TableField("useragent")
	private String useragent;
	/**
	 * 模块
	 */
	@ApiModelProperty(value = "模块")
	@TableField("module")
	private String module;
	/**
	 * 操作
	 */
	@ApiModelProperty(value = "操作")
	@TableField("operation")
	private String operation;
	/**
	 * 操作时间
	 */
	@ApiModelProperty(value = "操作时间")
	@TableField("optime")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date optime;
	/**
	 * 日志类型 1-业务、2-错误信息
	 */
	@ApiModelProperty(value = "日志类型 1-业务、2-错误信息")
	@TableField("type")
	private Integer type;
	/**
	 * 操作接口
	 */
	@ApiModelProperty(value = "操作接口")
	@TableField("uri")
	private String uri;
	/**
	 * 内容
	 */
	@ApiModelProperty(value = "内容")
	@TableField("content")
	private String content;

}
