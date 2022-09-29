package com.ossjk.qlh.system.entity;

import java.io.Serializable;

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
 * @ClassName: UserRole
 * @Description: 用户角色表-实体类
 * @author: chair
 * @date: 2020-12-14 22:06:31
 */
@ApiModel(value = "用户角色表")
@Data
public class UserRole {

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
	 * 角色id
	 */
	@ApiModelProperty(value = "角色id")
	@TableField("rid")
	private String rid;

}
