package com.ossjk.qlh.edu.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.ossjk.core.base.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Copyright  2022-06-30 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.edu.entity
 * @ClassName: Teacher
 * @Description: 教师-实体类
 * @author: Rick.yang
 * @date:  2022-06-30 11:06:52 
 */
@ApiModel(value = "教师")
@Data
public class Teacher extends BaseEntity<Teacher> {

	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@ApiModelProperty(value = "id", required = true)
	@TableId("id")
	private String id;
	/**
	 * 登录名
	 */
	@ApiModelProperty(value = "登录名")
	@TableField("lname")
	private String lname;
	/**
	 * 密码
	 */
	@ApiModelProperty(value = "密码")
	@TableField("pwd")
	private String pwd;
	/**
	 * 姓名
	 */
	@ApiModelProperty(value = "姓名")
	@TableField("name")
	private String name;
	/**
	 * 年龄
	 */
	@ApiModelProperty(value = "年龄")
	@TableField("age")
	private Integer age;
	/**
	 * 微信id
	 */
	@ApiModelProperty(value = "微信id")
	@TableField("wxuid")
	private String wxuid;
	/**
	 * 手机号码
	 */
	@ApiModelProperty(value = "手机号码")
	@TableField("phone")
	private String phone;
	/**
	 * 状态 1-启用、2-停用
	 */
	@ApiModelProperty(value = "状态 1-启用、2-停用")
	@TableField("state")
	private Integer state;
	/**
	 * 备注
	 */
	@ApiModelProperty(value = "备注")
	@TableField("remk")
	private String remk;
	/**
	 * 性别 1-男、2-女
	 */
	@ApiModelProperty(value = "性别 1-男、2-女")
	@TableField("sex")
	private Integer sex;
	 
	 
	 
	@Override
	protected Serializable pkVal() {
				return id;
	}
 

	 
}
