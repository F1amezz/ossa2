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
 * @ClassName: Permission
 * @Description: 权限表-实体类
 * @author: chair
 * @date: 2020-12-14 22:06:30
 */
@ApiModel(value = "权限表")
@Data
public class Permission extends BaseEntity<Permission> {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@ApiModelProperty(value = "主键", required = true)
	@TableId("id")
	private String id;
	/**
	 * 姓名
	 */
	@ApiModelProperty(value = "姓名", required = true)
	@TableField("name")
	private String name;
	/**
	 * 1-展示菜单、2-功能菜单、3功能按钮
	 */
	@ApiModelProperty(value = "1-展示菜单、2-功能菜单、3功能按钮", required = true)
	@TableField("type")
	private Integer type;
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
	 * 权限编码
	 */
	@ApiModelProperty(value = "权限编码", required = true)
	@TableField("code")
	private String code;
	/**
	 * 图标
	 */
	@ApiModelProperty(value = "图标")
	@TableField("icon")
	private String icon;
	/**
	 * 地址
	 */
	@ApiModelProperty(value = "地址")
	@TableField("url")
	private String url;
	/**
	 * 组件
	 */
	@ApiModelProperty(value = "组件")
	@TableField("component")
	private String component;
	/**
	 * 打开方式：1-内部、2-外部
	 */
	@ApiModelProperty(value = "打开方式：1-内部、2-外部", required = true)
	@TableField("opentype")
	private Integer opentype;
	/**
	 * 备注
	 */
	@ApiModelProperty(value = "备注")
	@TableField("remarks")
	private String remarks;

}
