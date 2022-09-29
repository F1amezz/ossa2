package com.ossjk.qlh.system.entity;

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
 * @ClassName: Role
 * @Description: 角色表-实体类
 * @author: chair
 * @date: 2020-12-14 22:06:30
 */
@ApiModel(value = "角色表")
@Data
public class Role extends BaseEntity<Role> {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@ApiModelProperty(value = "主键", required = true)
	@TableId("id")
	private String id;
	/**
	 * 编号
	 */
	@ApiModelProperty(value = "编号", required = true)
	@TableField("code")
	private String code;
	/**
	 * 姓名
	 */
	@ApiModelProperty(value = "姓名", required = true)
	@TableField("name")
	private String name;
	/**
	 * 父id
	 */
	@ApiModelProperty(value = "父id")
	@TableField("pid")
	private String pid;
	/**
	 * 排序
	 */
	@ApiModelProperty(value = "排序", required = true)
	@TableField("sort")
	private Integer sort;
	/**
	 * 等级
	 */
	@ApiModelProperty(value = "等级", required = true)
	@TableField("level")
	private Integer level;
	/**
	 * 备注
	 */
	@ApiModelProperty(value = "备注")
	@TableField("remarks")
	private String remarks;


	@ApiModelProperty(value = "绩效公式")
	@TableField("jxfm")
	private String jxfm;
}
