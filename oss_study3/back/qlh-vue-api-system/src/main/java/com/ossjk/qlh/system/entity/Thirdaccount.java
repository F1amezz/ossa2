package com.ossjk.qlh.system.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** 
 * Copyright  2021-08-09 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.system.entity
 * @ClassName: Thirdaccount
 * @Description: Thirdaccount-实体类
 * @author: chair
 * @date:  2021-08-09 10:45:44 
 */
@ApiModel(value = "第三方帐号")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Thirdaccount extends Model<Thirdaccount> {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@ApiModelProperty(value = "主键", required = true)
	@TableId("id")
	private String id;
	/**
	 * 用户id
	 */
	@ApiModelProperty(value = "用户id")
	@TableField("uid")
	private String uid;
	/**
	 * 第三方帐号id
	 */
	@ApiModelProperty(value = "第三方帐号id")
	@TableField("openid")
	private String openid;
	/**
	 * 类型 1-微信、2-QQ、3-阿里
	 */
	@ApiModelProperty(value = "类型 1-微信、2-QQ、3-阿里")
	@TableField("type")
	private Integer type;
	 
	 
	 
	@Override
	protected Serializable pkVal() {
				return id;
	}
 

	 
}
