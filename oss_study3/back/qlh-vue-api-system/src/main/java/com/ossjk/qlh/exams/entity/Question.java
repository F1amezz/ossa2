package com.ossjk.qlh.exams.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.ossjk.core.base.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


/**
 * Copyright  2022-06-14 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.exams.entity
 * @ClassName: Question
 * @Description: 试题表-实体类
 * @author: 
 * @date:  2022-06-14 11:31:17 
 */
@ApiModel(value = "试题表")
@Data
public class Question extends BaseEntity<Question> {

	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@ApiModelProperty(value = "id", required = true)
	@TableId("id")
	private String id;
	/**
	 * 知识点id（章节）
	 */
	@ApiModelProperty(value = "科目id")
	@TableField("sbid")
	@Excel(name="科目")
	private String sbid;
	/**
	 * 试题名称
	 */
	@ApiModelProperty(value = "试题名称")
	@TableField("name")
	@Excel(name="题目")
	private String name;
	/**
	 * 试题 1-单选题、2多选题、3-判断题、4-填空题、5-、简答题、6-论述题、7-分析题
	 */
	@ApiModelProperty(value = "试题 1-单选题、2多选题、3-判断题、4-填空题、5-、简答题、6-论述题、7-分析题")
	@TableField("type")
	@Excel(name="试题类型", replace={"单选题_1","多选题_2","判断题_3","填空题_4","简答题_5","论述题_6","分析题_7",})
	private Integer type;
	/**
	 * 内容
	 */
	@ApiModelProperty(value = "内容")
	@TableField("content")
	@Excel(name="选项内容")
	private String content;
	/**
	 * 答案
	 */
	@ApiModelProperty(value = "答案")
	@TableField("answer")
	@Excel(name="答案")
	private String answer;
	/**
	 * 题目解释
	 */
	@ApiModelProperty(value = "题目解释")
	@TableField("analysis")
	@Excel(name="题目解析*")
	private String analysis;
	/**
	 * 是否主观题 1-是、2-否
	 */
	@ApiModelProperty(value = "是否主观题 1-是、2-否")
	@TableField("is_subjective")
	private Integer isSubjective;
	/**
	 * 出题次数
	 */
	@ApiModelProperty(value = "出题次数")
	@TableField("expose_times")
	private Integer exposeTimes;
	/**
	 * 正确次数
	 */
	@ApiModelProperty(value = "正确次数")
	@TableField("right_times")
	private Integer rightTimes;
	/**
	 * 来源
	 */
	@ApiModelProperty(value = "来源")
	@TableField("reference")
	@Excel(name="试题来源")
	private String reference;
	/**
	 * 考点
	 */
	@ApiModelProperty(value = "考点")
	@TableField("examing_point")
	private String examingPoint;
	/**
	 * 关键字
	 */
	@ApiModelProperty(value = "关键字")
	@TableField("keyword")
	private String keyword;

//正误状态
@TableField(exist = false)
	private int result;
//学生得分
	@TableField(exist = false)
	private int resultScore;
	//学生答案
	@TableField(exist = false)
	private String resultAnswer;
	//该题总分分
	@TableField(exist = false)
	private int resultAllScore;


	@Override
	protected Serializable pkVal() {
				return id;
	}
 

	 
}
