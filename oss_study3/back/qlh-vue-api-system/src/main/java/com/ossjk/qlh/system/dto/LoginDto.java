package com.ossjk.qlh.system.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 
 * Copyright © 2020 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.system.dto
 * @ClassName: LoginDto
 * @Description: 登录对象
 * @author: chair
 * @date: 2020年12月15日 下午11:03:03
 */
@ApiModel(value = "登录对象")
@Data
public class LoginDto implements Serializable {
	@ApiModelProperty(value = "登录名", required = true)
	private String name;
	@ApiModelProperty(value = "密码", required = true)
	private String pwd;
	@ApiModelProperty(value = "验证码", required = true)
	private String code;
	@ApiModelProperty(value = "验证码key", required = true)
	private String key;

}
