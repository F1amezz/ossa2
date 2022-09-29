package com.ossjk.qlh.study.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.ossjk.core.base.entity.BaseEntity;
import com.ossjk.core.system.annotation.DbColumnMapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Copyright  2022-06-16 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.xuexi.entity
 * @ClassName: Myclass
 * @Description: -实体类
 * @author: Rick.yang
 * @date:  2022-06-16 17:38:14 
 */
@ApiModel(value = "")
@Data
public class Myclass extends BaseEntity<Myclass> {

	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@ApiModelProperty(value = "", required = true)
	@TableId("id")
	private String id;
	/**
	 * 头像
	 */
	@ApiModelProperty(value = "头像")
	@TableField("head")
	private String head;
	/**
	 * 学生名字
	 */
	@ApiModelProperty(value = "学生名字")
	@TableField("sid")
	@DbColumnMapper(columns = "name", tableName = "student", condition = "id")
	private String sid;
	/**
	 * 班级名字
	 */
	@ApiModelProperty(value = "班级名字")
	@TableField("cid")
	@DbColumnMapper(columns = "name", tableName = "clazz", condition = "id")
	private String cid;
	/**
	 * 班主任
	 */
	@ApiModelProperty(value = "班主任")
	@TableField("tid")
	@DbColumnMapper(columns = "name", tableName = "teacher", condition = "id")
	private String tid;
	/**
	 * 课程+章节
	 */
	@ApiModelProperty(value = "课程+章节")
	@TableField("courseid")
	@DbColumnMapper(columns = "name", tableName = "course", condition = "id")
	private String courseid;
	/**
	 * 手机号码
	 */
	@ApiModelProperty(value = "手机号码")
	@TableField("mobile")
	private String mobile;
	/**
	 * 带班老师
	 */
	@ApiModelProperty(value = "带班老师")
	@TableField("teachname")
	private String teachname;
	/**
	 * 该班班长
	 */
	@ApiModelProperty(value = "该班班长")
	@TableField("monitor")
	private String monitor;
	 
	 
	 
	@Override
	protected Serializable pkVal() {
				return id;
	}
 

	 
}
