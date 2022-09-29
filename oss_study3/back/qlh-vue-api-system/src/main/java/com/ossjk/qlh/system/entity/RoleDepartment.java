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
 * @ClassName: RoleDepartment
 * @Description: 角色部门表-实体类
 * @author: chair
 * @date: 2020-12-14 22:06:30
 */
@ApiModel(value = "角色部门表")
@Data
public class RoleDepartment {

	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@ApiModelProperty(value = "", required = true)
	@TableId("id")
	private String id;
	/**
	 * 部门id
	 */
	@ApiModelProperty(value = "部门id")
	@TableField("did")
	private String did;
	/**
	 * 角色id
	 */
	@ApiModelProperty(value = "角色id")
	@TableField("rid")
	private String rid;

}
