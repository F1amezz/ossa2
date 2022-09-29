package com.ossjk.core.system.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Copyright © 2021 QLH. Tech Ltd. All rights reserved.
 *
 * @Package: com.ossjk.core.system.dto
 * @ClassName: AreaDto
 * @Description: 地区数据
 * @author: chair
 * @date: 2021/3/12 14:58
 */
@ApiModel(value = "地区数据")
@Data
public class AreaDto {

	private static final long serialVersionUID = 1L;

	/**
	 * 区域编码
	 */
	@ApiModelProperty(value = "区域编码", required = true)
	private String id;
	/**
	 * 区域名称
	 */
	@ApiModelProperty(value = "区域名称")
	private String name;
	/**
	 * 区域上级标识
	 */
	@ApiModelProperty(value = "区域上级标识")
	private Integer pid;
	/**
	 * 地名简称
	 */
	@ApiModelProperty(value = "地名简称")
	private String simplename;
	/**
	 * 区域等级
	 */
	@ApiModelProperty(value = "区域等级")
	private Integer level;
	/**
	 * 城市编码
	 */
	@ApiModelProperty(value = "城市编码")
	private String citycode;
	/**
	 * 邮政编码
	 */
	@ApiModelProperty(value = "邮政编码")
	private String zipcode;
	/**
	 * 组合名称
	 */
	@ApiModelProperty(value = "组合名称")
	private String mername;
	/**
	 * 经度
	 */
	@ApiModelProperty(value = "经度")
	private Float lng;
	/**
	 * 纬度
	 */
	@ApiModelProperty(value = "纬度")
	private Float lat;
	/**
	 * 拼音
	 */
	@ApiModelProperty(value = "拼音")
	private String pinyin;


}
