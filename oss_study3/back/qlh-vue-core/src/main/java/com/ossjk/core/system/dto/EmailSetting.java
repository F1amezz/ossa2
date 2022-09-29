package com.ossjk.core.system.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 
 * Copyright © 2021 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.core.system.dto
 * @ClassName: SystemSetting
 * @Description: 系统设置
 * @author: chair
 * @date: 2021年3月3日 下午10:41:36
 */
@Data
public class EmailSetting implements Serializable {
	
	private static final long serialVersionUID = 1L;
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
