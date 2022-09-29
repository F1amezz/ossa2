package com.ossjk.qlh.study.entity;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.ossjk.core.base.entity.BaseEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Copyright 2022-06-13 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.xuexi.entity
 * @ClassName: Feedback
 * @Description: 总结反馈表-实体类
 * @author: Rick.yang
 * @date: 2022-06-13 18:03:21
 */
@ApiModel(value = "总结反馈表")
@Data
public class Feedback extends BaseEntity<Feedback> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @ApiModelProperty(value = "", required = true)
    @TableId("id")
    private String id;

    /**
     * 学生名字
     */
    @ApiModelProperty(value = "学生名字")
    @TableField("sid")
    // @DbColumnMapper(columns = "name", tableName = "student", condition = "id")
    private String sid;

    /**
     * 班级名字
     */
    @ApiModelProperty(value = "班级名字")
    @TableField("cid")
    // @DbColumnMapper(columns = "name", tableName = "clazz", condition = "id")
    private String cid;

    /**
     * 班主任
     */
    @ApiModelProperty(value = "班主任")
    @TableField("tid")
    // @DbColumnMapper(columns = "name", tableName = "teacher", condition = "id")
    private String tid;

    /**
     * 课程+章节
     */
    @ApiModelProperty(value = "课程")
    @TableField("courseid")
    // @DbColumnMapper(columns = "name", tableName = "course", condition = "id")
    private String courseid;

    /**
     * 内容
     */
    @ApiModelProperty(value = "内容")
    @TableField("content")
    private String content;

    /**
     * 评分
     */
    @ApiModelProperty(value = "评分")
    @TableField("score")
    private int score;

    /**
     * 已读状态（0-未读，1-已读）
     */
    @ApiModelProperty(value = "已读状态")
    @TableField("readst")

    private String readst;

    /**
     * 学生提交时间
     */
    @ApiModelProperty(value = "学生提交时间")
    @TableField("subtime")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JSONField(format = "yyyy-MM-dd")
    private Date subtime;

    /**
     * 老师查看时间
     */
    @ApiModelProperty(value = "老师查看时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JSONField(format = "yyyy-MM-dd")
    @TableField("chetime")
    private Date chetime;

    /**
     * 标题
     */
    @ApiModelProperty(value = "标题")
    @TableField("title")
    private String title;

    @Override
    protected Serializable pkVal() {
        return id;
    }

}
