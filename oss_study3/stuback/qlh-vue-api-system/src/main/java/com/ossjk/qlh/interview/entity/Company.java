package com.ossjk.qlh.interview.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.ossjk.core.base.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Copyright  2022-06-06 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.study3.entity
 * @ClassName: Company
 * @Description: 企业表-实体类
 * @author: Rick.yang
 * @date:  2022-06-06 14:48:32 
 */
@ApiModel(value = "企业表")
@Data
public class Company extends BaseEntity<Company> {

	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@ApiModelProperty(value = "id", required = true)
	@TableId("id")
	private String id;
	/**
	 * 名称
	 */
	@ApiModelProperty(value = "名称")
	@TableField("name")
	private String name;
	/**
	 * 地址
	 */
	@ApiModelProperty(value = "地址")
	@TableField("address")
	private String address;
	/**
	 * 电话
	 */
	@ApiModelProperty(value = "电话")
	@TableField("tel")
	private String tel;
	/**
	 * 来源类型 1-学生、2-公司
	 */
	@ApiModelProperty(value = "来源类型 1-学生、2-公司")
	@TableField("source")
	private Integer source;
	/**
	 * 规模
	 */
	@ApiModelProperty(value = "规模")
	@TableField("maxemps")
	private String maxemps;
	/**
	 * 联系人电话
	 */
	@ApiModelProperty(value = "联系人电话")
	@TableField("mobile")
	private String mobile;
	/**
	 * 网站
	 */
	@ApiModelProperty(value = "网站")
	@TableField("url")
	private String url;
	/**
	 * 描述
	 */
	@ApiModelProperty(value = "描述")
	@TableField("descb")
	private String descb;
	/**
	 * 备注
	 */
	@ApiModelProperty(value = "备注")
	@TableField("remark")
	private String remark;
	 
	 
	 
	@Override
	protected Serializable pkVal() {
				return id;
	}
 

	 
}
