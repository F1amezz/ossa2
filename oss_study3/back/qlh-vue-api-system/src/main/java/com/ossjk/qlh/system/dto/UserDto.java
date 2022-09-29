package com.ossjk.qlh.system.dto;

import com.ossjk.qlh.system.entity.User;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * Copyright 2020-12-14 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.system.entity
 * @ClassName: User
 * @Description: 用户表-实体类
 * @author: chair
 * @date: 2020-12-14 22:06:31
 */
@ApiModel(value = "用户表")
@Data
public class UserDto extends User {

	private String roleId;
	
	private String newPwd;

}
