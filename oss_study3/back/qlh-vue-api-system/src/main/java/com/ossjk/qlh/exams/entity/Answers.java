package com.ossjk.qlh.exams.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.ossjk.core.base.entity.BaseEntity;
import com.ossjk.core.system.annotation.DbColumnMapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Copyright  2022-06-14 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.exams.entity
 * @ClassName: Answers
 * @Description: 答卷（题）表-实体类
 * @author: 
 * @date:  2022-06-14 11:31:17 
 */
@ApiModel(value = "答卷（题）表")
@Data
public class Answers extends BaseEntity<Answers> {

	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@ApiModelProperty(value = "", required = true)
	@TableId("id")
	private String id;
	/**
	 * 考试id
	 */
	@ApiModelProperty(value = "考试id")
	@TableField("kid")
	private String kid;
	/**
	 * 班级id
	 */
	@ApiModelProperty(value = "班级id")
	@DbColumnMapper( tableName = "clazz",  condition = "id",  columns = "name")
	@TableField("cid")
	private String cid;
	/**
	 * 考试人id
	 */
	@ApiModelProperty(value = "考试人id")
	@DbColumnMapper( tableName = "student",  condition = "id",  columns = "name")
	@TableField("sid")
	private String sid;
	/**
	 * 得分
	 */
	@ApiModelProperty(value = "得分")
	@TableField("score")
	private Integer score;
	/**
	 * 考生的答案
	 */
	@ApiModelProperty(value = "考生的答案")
	@TableField("answer")
	private String answer;
	/**
	 * 试卷id
	 */
	@ApiModelProperty(value = "试卷id")
	@TableField("expid")
	private String expid;
	/**
	 * 答卷是否已批
	 */
	@ApiModelProperty(value = "答卷是否已批", required = true)
	@TableField("isjudged")
	private Integer isjudged;
	 

	@Override
	protected Serializable pkVal() {
				return id;
	}

}
