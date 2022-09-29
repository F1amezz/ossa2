package com.ossjk.core.system.dto;

import java.io.Serializable;
import java.util.List;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 字典数据 Copyright © 2021 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.core.system.dto
 * @ClassName: DictionarydataDto
 * @Description: 数据字典数据
 * @author: chair
 * @date: 2021年3月3日 下午9:39:54
 */
@ApiModel(value = "数据字典数据")
@Data
public class DictionarydataDto implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@ApiModelProperty(value = "主键", required = true)
	private String id;
	/**
	 * 数据字典id
	 */
	@ApiModelProperty(value = "数据字典id", required = true)
	private String did;
	/**
	 * 名称
	 */
	@ApiModelProperty(value = "名称", required = true)
	private String dkey;
	/**
	 * 数据值
	 */
	@ApiModelProperty(value = "数据值", required = true)
	private String dvalue;
	/**
	 * 排序
	 */
	@ApiModelProperty(value = "排序")
	private Integer sort;
	/**
	 * 描述
	 */
	@ApiModelProperty(value = "描述")
	private String description;
	/**
	 * 是否禁用：1-是、2-否
	 */
	@ApiModelProperty(value = "是否禁用：1-是、2-否", required = true)
	private Integer isdisable;

}
