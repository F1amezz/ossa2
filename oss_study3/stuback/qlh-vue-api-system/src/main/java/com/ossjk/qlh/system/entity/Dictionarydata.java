package com.ossjk.qlh.system.entity;

import java.io.Serializable;

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
 * @ClassName: Dictionarydata
 * @Description: 数据字典数据-实体类
 * @author: Jason
 * @date: 2021-01-16 22:33:54
 */
@ApiModel(value = "数据字典数据")
@Data
public class Dictionarydata extends BaseEntity<Dictionarydata> {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@ApiModelProperty(value = "主键", required = true)
	@TableId("id")
	private String id;
	/**
	 * 数据字典id
	 */
	@ApiModelProperty(value = "数据字典id", required = true)
	@TableField("did")
	private String did;
	/**
	 * 名称
	 */
	@ApiModelProperty(value = "名称", required = true)
	@TableField("dkey")
	private String dkey;
	/**
	 * 数据值
	 */
	@ApiModelProperty(value = "数据值", required = true)
	@TableField("dvalue")
	private String dvalue;
	/**
	 * 排序
	 */
	@ApiModelProperty(value = "排序")
	@TableField("sort")
	private Integer sort;
	/**
	 * 描述
	 */
	@ApiModelProperty(value = "描述")
	@TableField("description")
	private String description;
	/**
	 * 是否禁用：1-是、2-否
	 */
	@ApiModelProperty(value = "是否禁用：1-是、2-否", required = true)
	@TableField("isdisable")
	private Integer isdisable;

}
