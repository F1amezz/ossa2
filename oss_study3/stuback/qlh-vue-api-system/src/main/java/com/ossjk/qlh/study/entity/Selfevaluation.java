package com.ossjk.qlh.study.entity;
import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.ossjk.core.base.entity.BaseEntity;
import com.ossjk.core.system.annotation.DbColumnMapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;


import java.io.Serializable;
import java.util.Date;

/**
 * Copyright  2022-06-14 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.xuexi.entity
 * @ClassName: Selfevaluation
 * @Description: 自我评价表-实体类
 * @author: Rick.yang
 * @date:  2022-06-14 17:12:17 
 */
@ApiModel(value = "自我评价表")
@Data
public class Selfevaluation extends BaseEntity<Selfevaluation> {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@ApiModelProperty(value = "主键", required = true)
	@TableId("id")
	private String id;
	/**
	 * 学生id
	 */
	@ApiModelProperty(value = "学生id")
	@TableField("sid")
	private String sid;

	/**
	 * 班级id
	 */
	@ApiModelProperty(value = "班级id")
	@TableField("cid")
	private String cid;

	/**
	 * 老师id
	 */
	@ApiModelProperty(value = "老师id")
	@TableField("tid")
	private String tid;
	/**
	 * 标题
	 */
	@ApiModelProperty(value = "标题")
	@TableField("title")
	private String title;
	/**
	 * 周数
	 */
	@ApiModelProperty(value = "周数")
	@TableField("week")
	private Integer week;
	/**
	 * 自我评价内容
	 */
	@ApiModelProperty(value = "自我评价内容")
	@TableField("content")
	private String content;
	/**
	 * 问题1
	 */
	@ApiModelProperty(value = "问题1")
	@TableField("quest1")
	private Integer quest1;
	/**
	 * 问题2
	 */
	@ApiModelProperty(value = "问题2")
	@TableField("quest2")
	private Integer quest2;
	/**
	 * 问题3
	 */
	@ApiModelProperty(value = "问题3")
	@TableField("quest3")
	private Integer quest3;
	/**
	 * 学生提交时间
	 */
	@ApiModelProperty(value = "学生提交时间")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	@TableField("subtime")
	private Date subtime;
	/**
	 * 老师查看时间
	 */
	@ApiModelProperty(value = "老师查看时间")
	@TableField("readtime")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	@JSONField(format = "yyyy-MM-dd HH:mm")
	private Date readtime;
	/**
	 * 状态（0-未读，1-已读）
	 */
	@ApiModelProperty(value = "状态（0-未读，1-已读）")
	@TableField("readst")
	private String readst;
	 
	 
	 
	@Override
	protected Serializable pkVal() {
				return id;
	}
 

	 
}
