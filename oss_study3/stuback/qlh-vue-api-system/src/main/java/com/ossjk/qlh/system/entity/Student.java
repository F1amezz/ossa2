package com.ossjk.qlh.system.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.ossjk.core.base.entity.BaseEntity;
import com.ossjk.core.system.annotation.OptionDictionary;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Copyright 2020-12-14 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.system.entity
 * @ClassName: User
 * @Description: 用户表-实体类
 * @author: chair
 * @date: 2020-12-14 22:06:31
 */
@ApiModel(value = "用户表")
@Data
public class Student extends BaseEntity<Student> {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@ApiModelProperty(value = "主键", required = true)
	@TableId("id")
	private String id;

	@ApiModelProperty(value = "微信小程序unionID", required = true)
	@TableId("wxappuid")
	private String wxappuid;

	@ApiModelProperty(value = "微信小程序openID", required = true)
	@TableId("wxappopenid")
	private String wxappopenid;
	/**
	 * 头像
	 */
	@ApiModelProperty(value = "头像")
	@TableField("head")
	private String head;
	/**
	 * 登录名
	 */
	@ApiModelProperty(value = "登录名", required = true)
	@TableField("lname")
	private String lname;
	/**
	 * 姓名
	 */
	@ApiModelProperty(value = "姓名")
	@TableField("name")
	private String name;
	/**
	 * 密码
	 */
	@ApiModelProperty(value = "密码", required = true)
	@TableField("pwd")
	private String pwd;
	/**
	 * 生日
	 */
	@ApiModelProperty(value = "生日")
	@TableField("birth")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JSONField(format = "yyyy-MM-dd")
	private Date birth;
	/**
	 * 性别 1-男、2-女
	 */
	@OptionDictionary(key = "sex")
	@ApiModelProperty(value = "性别 1-男、2-女", required = true)
	@TableField("sex")
	private Integer sex;
	/**
	 * 邮件
	 */
	@ApiModelProperty(value = "邮件")
	@TableField("email")
	private String email;
	/**
	 * 手机
	 */
	@ApiModelProperty(value = "手机", required = true)
	@TableField("mobile")
	private String mobile;
	/**
	 * 备注
	 */
	@ApiModelProperty(value = "备注")
	@TableField("remarks")
	private String remarks;


	// ==================================扩展

	////////////////////////////////////////////////////////////////////////////////
	/**
	 * 是否禁用：1-是、2-否
	 */
	@ApiModelProperty(value = "是否禁用：1-是、2-否", required = true)
	@TableField("isdisable")
	private Integer isdisable;
	/**
	 * 学校
	 */
	@ApiModelProperty(value = "学校")
	@TableField("school")
	private String school;
	/**
	 * 专业
	 */
	@ApiModelProperty(value = "专业")
	@TableField("professional")
	private String professional;
	/**
	 * 籍贯
	 */
	@ApiModelProperty(value = "籍贯")
	@TableField("nativePlace")
	private String nativeplace;
	/**
	 * 报到时间
	 */
	@ApiModelProperty(value = "报到时间")
	@TableField("comeTime")
	private Date cometime;
	/**
	 * 紧急联系人关系：1-父亲；2-母亲；3-兄弟；4-姐妹；5-亲戚；6-朋友
	 */
	@ApiModelProperty(value = "紧急联系人关系")
	@TableField("relationship")
	private String relationship;
	/**
	 * 紧急联系人名字
	 */
	@ApiModelProperty(value = "紧急联系人名字")
	@TableField("rname")
	private String rname;
	/**
	 * 紧急联系人电话
	 */
	@ApiModelProperty(value = "紧急联系人电话")
	@TableField("rphone")
	private String rphone;
	/**
	 * 上课时间
	 */
	@ApiModelProperty(value = "上课时间")
	@TableField("schoolTime")
	private Date schooltime;
	/**
	 * 身份证
	 */
	@ApiModelProperty(value = "身份证")
	@TableField("idCard")
	private String idcard;

	/**
	 * 描述
	 */
	@ApiModelProperty(value = "描述")
	@TableField("describes")
	private String describes;

	/**
	 * 状态：1-在读；2-休学；3-毕业
	 */
	@ApiModelProperty(value = "状态：1-在读；2-休学；3-毕业")
	@TableField("state")
	private Integer state;
	/**
	 * 班级id
	 */
	@ApiModelProperty(value = "班级id")
	@TableField("cid")
	private String cid;
	
	@ApiModelProperty(value = "职务：2-组长、1-班长", required = true)
	private Integer groupleader;



}
