package com.ossjk.qlh.system.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * 
 * Copyright © 2021 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.system.dto
 * @ClassName: FindPwdDto
 * @Description: 找密码Dto
 * @author: chair
 * @date: 2021年3月7日 下午11:42:39
 */
@ApiModel(value = "找密码Dto")
@Data
public class FindPwdDto implements Serializable {
	/**
	 * 邮件
	 */
	private String email;
	/**
	 * 验证码
	 */
	private String code;
	/**
	 * 新密码
	 */
	private String newPwd;
}
