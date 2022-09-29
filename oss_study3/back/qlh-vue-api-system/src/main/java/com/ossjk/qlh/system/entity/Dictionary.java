package com.ossjk.qlh.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.ossjk.core.base.entity.BaseEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Copyright 2021-01-16 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.system.entity
 * @ClassName: Dictionary
 * @Description: 数据字典-实体类
 * @author: Jason
 * @date: 2021-01-16 22:31:48
 */
@ApiModel(value = "数据字典")
@Data
public class Dictionary extends BaseEntity<Dictionary> {

	private static final long serialVersionUID = 1L;

	/** 
	 * 主键
	 */
	@ApiModelProperty(value = "主键", required = true)
	@TableId("id")
	private String id;
	/**
	 * 字典名称
	 */
	@ApiModelProperty(value = "字典名称", required = true)
	@TableField("name")
	private String name;
	/**
	 * 字典编号
	 */
	@ApiModelProperty(value = "字典编号", required = true)
	@TableField("dkey")
	private String dkey;
	/**
	 * 属性值
	 */
	@ApiModelProperty(value = "属性值")
	@TableField("dvalue")
	private String dvalue;
	/**
	 * 类型：1-选项、2-属性值、3-系统属性、4-邮箱设置
	 */
	@ApiModelProperty(value = "类型：1-选项、2-属性值、3-系统属性、4-邮箱设置", required = true)
	@TableField("type")
	private Integer type;
	/**
	 * 描述
	 */
	@ApiModelProperty(value = "描述")
	@TableField("description")
	private String description;
	/**
	 * 备注
	 */
	@ApiModelProperty(value = "备注")
	@TableField("remarks")
	private String remarks;
	/**
	 * 是否禁用：1-是、2-否
	 */
	@ApiModelProperty(value = "是否禁用：1-是、2-否", required = true)
	@TableField("isdisable")
	private Integer isdisable;

}
