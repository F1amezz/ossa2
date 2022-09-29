package com.ossjk.qlh.sms.dto;

import java.io.Serializable;
import java.util.Map;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 
 * Copyright © 2021 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.sms.dto
 * @ClassName: EmailSmsDto
 * @Description: 邮件信息Dto
 * @author: chair
 * @date: 2021年4月14日 上午11:50:14
 */
@ApiModel(value = "邮件信息Dto")
@Data
public class EmailSmsDto implements Serializable {
	/**
	 * 接受者
	 */
	@ApiModelProperty(value = "接受者", required = true)
	private String to;
	/**
	 * 标题
	 */
	@ApiModelProperty(value = "标题", required = true)
	private String subject;
	/**
	 * 发送类型 1-内容发送、2-模版发送
	 */
	@ApiModelProperty(value = "发送类型 1-内容发送、2-模版发送", required = true)
	private Integer sendType;
	/**
	 * 模版标识
	 */
	@ApiModelProperty(value = "模版标识")
	private String tcode;
	/**
	 * 模版参数
	 */
	@ApiModelProperty(value = "模版参数")
	private String parameter;

	/**
	 * 邮件内容
	 */
	@ApiModelProperty(value = "邮件内容", required = true)
	private String content;
}
