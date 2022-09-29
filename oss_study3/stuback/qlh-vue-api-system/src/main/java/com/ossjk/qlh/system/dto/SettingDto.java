package com.ossjk.qlh.system.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 
 * Copyright © 2021 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.system.dto
 * @ClassName: SettingDto
 * @Description: 系统设置&邮件设置对象
 * @author: chair
 * @date: 2021年3月4日 下午2:10:42
 */
@ApiModel(value = "系统设置&邮件设置对象")
@Data
public class SettingDto implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * 系统标题
	 */
	@ApiModelProperty(value = "系统标题")
	private String title;
	/**
	 * 系统名称
	 */
	@ApiModelProperty(value = "系统名称")
	private String systemName;
	/**
	 * 系统图标
	 */
	@ApiModelProperty(value = "系统图标")
	private String icon;
	/**
	 * 系统备案
	 */
	@ApiModelProperty(value = "系统备案")
	private String icp;
	/**
	 * 底部内容
	 */
	@ApiModelProperty(value = "底部内容")
	private String footer;

	/**
	 * 邮件发送者
	 */
	@ApiModelProperty(value = "邮件发送者")
	private String mailSender;
	/**
	 * 邮件服务器
	 */
	@ApiModelProperty(value = "邮件服务器")
	private String host;
	/**
	 * 邮件服务器端口
	 */
	@ApiModelProperty(value = "邮件服务器端口")
	private Integer port;
	/**
	 * 邮件服务用户
	 */
	@ApiModelProperty(value = "邮件服务用户")
	private String user;
	/**
	 * 邮件服务授权码
	 */
	@ApiModelProperty(value = "邮件服务授权码")
	private String pass;

}
