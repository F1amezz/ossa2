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
 * @ClassName: Exampaper
 * @Description: 试卷-实体类
 * @author: 
 * @date:  2022-06-14 11:31:17 
 */
@ApiModel(value = "试卷")
@Data
public class Exampaper extends BaseEntity<Exampaper> {

	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@ApiModelProperty(value = "id", required = true)
	@TableId("id")
	private String id;
	/**
	 * 试卷名称
	 */
	@ApiModelProperty(value = "试卷名称")
	@TableField("name")
	private String name;
	/**
	 * 考试时间（分钟）
	 */
	@ApiModelProperty(value = "考试时间（分钟）")
	@TableField("duration")
	private Integer duration;
	/**
	 * 总分
	 */
	@ApiModelProperty(value = "总分")
	@TableField("score")
	private Integer score;
	/**
	 * 及格分数
	 */
	@ApiModelProperty(value = "及格分数")
	@TableField("pass_score")
	private Integer passScore;
	/**
	 * 类型：1-小测、2-阶段考、3-毕业考
	 */
	@ApiModelProperty(value = "类型：1-小测、2-阶段考、3-毕业考")
	@TableField("type")
	private Integer type;
	/**
	 * 是否可见 1-可见、2-不可见
	 */
	@ApiModelProperty(value = "是否可见 1-可见、2-不可见")
	@TableField("is_visible")
	private Integer isVisible;
	/**
	 * 试题例子{types:[1,2,3],scores:[20,40,40],qnums:[5,10,2]contexts:[{qid:[1,2,3,4,5]qscore[4,4,4,4,4]},{qid:[1,2,3,4,5,6,7,8,9,10]qscore[4,4,4,4,4,4,4,4,4,4]},{qid:[11,12]qscore[10,30]}]}
	 */
	@ApiModelProperty(value = "试题例子{types:[1,2,3],scores:[20,40,40],qnums:[5,10,2]contexts:[{qid:[1,2,3,4,5]qscore[4,4,4,4,4]},{qid:[1,2,3,4,5,6,7,8,9,10]qscore[4,4,4,4,4,4,4,4,4,4]},{qid:[11,12]qscore[10,30]}]}")
	@TableField("content")
	private String content;
	/**
	 * 状态 1-启用、2-不启用
	 */
	@ApiModelProperty(value = "状态 1-启用、2-不启用")
	@TableField("status")
	private Integer status;
	/**
	 * 备注
	 */
	@ApiModelProperty(value = "备注")
	@TableField("mark")
	private String mark;
	/**
	 * 课程id
	 */
	@ApiModelProperty(value = "课程id")
	@DbColumnMapper( tableName = "course",  condition = "id",  columns = "name")
	@TableField("cid")
	private String cid;
	/**
	 * 模块id
	 */
	@ApiModelProperty(value = "模块id")
	@DbColumnMapper( tableName = "subject",  condition = "id",  columns = "name")
	@TableField("mid")
	private String mid;
	/**
	 * 科目id
	 */
	@ApiModelProperty(value = "科目id")
	@DbColumnMapper( tableName = "subject",  condition = "id",  columns = "name")
	@TableField("kid")
	private String kid;
	 
	 
	 
	@Override
	protected Serializable pkVal() {
				return id;
	}
 

	 
}
