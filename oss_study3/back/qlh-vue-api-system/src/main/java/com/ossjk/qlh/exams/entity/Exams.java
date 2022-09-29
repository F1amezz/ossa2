package com.ossjk.qlh.exams.entity;

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
 * Copyright  2022-06-14 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.exams.entity
 * @ClassName: Exams
 * @Description: 考试表-实体类
 * @author: 
 * @date:  2022-06-14 11:31:17 
 */
@ApiModel(value = "考试表")
@Data
public class Exams extends BaseEntity<Exams> {

	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@ApiModelProperty(value = "id", required = true)
	@TableId("id")
	private String id;
	/**
	 * 学生id
	 */
	@ApiModelProperty(value = "学生id")
	@TableField("sid")
	private String sid;
	/**
	 * 考试班级id
	 */
	@ApiModelProperty(value = "考试班级id")
	@TableField("cid")
	private String cid;
	/**
	 * 试卷id
	 */
	@ApiModelProperty(value = "试卷id")
	@TableField("expid")
	private String expid;
	/**
	 * 生效时间
	 */
	@ApiModelProperty(value = "生效时间")
	@TableField("start_time")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date startTime;
	/**
	 * 截止时间
	 */
	@ApiModelProperty(value = "截止时间")
	@TableField("end_time")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date endTime;
	/**
	 * 考试名称
	 */
	@ApiModelProperty(value = "考试名称", required = true)
	@TableField("name")
	private String name;

	 
	 
	 
	@Override
	protected Serializable pkVal() {
				return id;
	}
 

	 
}
