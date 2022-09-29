package com.ossjk.qlh.interview.entity;
import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.ossjk.core.base.entity.BaseEntity;
import com.ossjk.core.system.annotation.DbColumnMapper;
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
 * @ClassName: Mianshi
 * @Description: 面试表-实体类
 * @author: Rick.yang
 * @date:  2022-06-06 14:48:32 
 */
@ApiModel(value = "面试表")
@Data
public class Interview extends BaseEntity<Interview> {

	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@ApiModelProperty(value = "")
	@TableField("id")
	private String id;
	/**
	 * 学生id
	 */
	@ApiModelProperty(value = "学生id")
	@TableField("stuid")
	@DbColumnMapper(columns="name",tableName="student",condition="id")
	private String stuid;
	/**
	 * 企业名称
	 */
	@ApiModelProperty(value = "企业名称")
	@TableField("cmpname")
	private String cmpname;
	/**
	 * 岗位
	 */
	@ApiModelProperty(value = "岗位")
	@TableField("postid")
	@DbColumnMapper(columns="name",tableName="posts",condition="id")
	private String postid;
	/**
	 * 面试准备情况：ABCD
	 */
	@ApiModelProperty(value = "面试准备情况")
	@TableField("mszbqk")
	private Integer mszbqk;
	/**
	 * 面试过程打分
	 */
	@ApiModelProperty(value = "面试过程打分")
	@TableField("msscore")
	private Integer msscore;
	/**
	 * 面试时间
	 */
	@ApiModelProperty(value = "面试时间")
	@TableField("mstm")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	@JSONField(format = "yyyy-MM-dd HH:mm")
	private Date mstm;
	/**
	 * 面试结果
	 */
	@ApiModelProperty(value = "面试结果")
	@TableField("msresult")
	private String msresult;
	/**
	 * 面试总结
	 */

	@ApiModelProperty(value = "面试难题")
	@TableField("exam")
	private String  exam;


	@ApiModelProperty(value = "面试总结")
	@TableField("mszj")
	private String mszj;
	/**
	 * 面试点评
	 */
	@ApiModelProperty(value = "面试点评")
	@TableField("msdp")
	private String msdp;
	/**
	 * 面试时长(分钟)
	 */
	@ApiModelProperty(value = "面试时长(分钟)")
	@TableField("mslast")
	private Integer mslast;



	@Override
	protected Serializable pkVal() {
		return id;
	}



}
