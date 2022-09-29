package com.ossjk.qlh.interview.entity;
import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.ossjk.core.base.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * Copyright  2022-06-06 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.study3.entity
 * @ClassName: Posts
 * @Description: 岗位表-实体类
 * @author: Rick.yang
 * @date:  2022-06-06 14:48:32 
 */
@ApiModel(value = "岗位表")
@Data
public class Posts extends BaseEntity<Posts> {

	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@ApiModelProperty(value = "", required = true)
	@TableId("id")
	private String id;
	/**
	 * name
	 */
	@ApiModelProperty(value = "name")
	@TableField("name")
	private String name;

	@ApiModelProperty(value = "就业企业")
	@TableField("company")
	private String company;
	/**
	 * 招聘人数
	 */
	@ApiModelProperty(value = "招聘人数")
	@TableField("maxneed")
	private Integer maxneed;
	/**
	 * 经验
	 */
	@ApiModelProperty(value = "经验")
	@TableField("exps")
	private String exps;


	@ApiModelProperty(value = "招聘记录")
	@TableField("history")
	private String  history;
	/**
	 * 学历
	 */
	@ApiModelProperty(value = "学历")
	@TableField("xueli")
	private String xueli;
	/**
	 * 待遇
	 */
	@ApiModelProperty(value = "待遇")
	@TableField("sarry")
	private String sarry;
	/**
	 * 技术要求
	 */
	@ApiModelProperty(value = "技术要求")
	@TableField("tech")
	private String tech;
	/**
	 * 备注
	 */
	@ApiModelProperty(value = "备注")
	@TableField("remark")
	private String remark;
	/**
	 * 开始时间
	 */
	@ApiModelProperty(value = "开始时间")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JSONField(format = "yyyy-MM-dd")
	@TableField("bgntm")
	private Date bgntm;
	/**
	 * 结束时间
	 */
	@ApiModelProperty(value = "结束时间")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JSONField(format = "yyyy-MM-dd")
	@TableField("endtm")
	private Date endtm;
	 
	 
	 
	@Override
	protected Serializable pkVal() {
				return id;
	}
 

	 
}
