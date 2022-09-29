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
 * @ClassName: Summarize
 * @Description: -实体类
 * @author: Rick.yang
 * @date: 2022-06-13 18:03:21
 */
@ApiModel(value = "")
@Data
public class Summarize extends BaseEntity<Summarize> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @ApiModelProperty(value = "", required = true)
    @TableId("id")
    private String id;

    /**
     * 学生id
     */
    @ApiModelProperty(value = "学生id")
    @TableField("sid")
    // @DbColumnMapper(columns = "name", tableName = "student", condition = "id")
    private String sid;

    /**
     * 班级id
     */
    @ApiModelProperty(value = "班级id")
    @TableField("cid")
    // @DbColumnMapper(columns = "name", tableName = "clazz", condition = "id")
    private String cid;

    /**
     * 老师id
     */
    @ApiModelProperty(value = "老师id")
    @TableField("tid")
    // @DbColumnMapper(columns = "name", tableName = "teacher", condition = "id")
    private String tid;

    /**
     * 课程id
     */
    @ApiModelProperty(value = "课程id")
    @TableField("courseId")
    // @DbColumnMapper(columns = "name", tableName = "course", condition = "id")
    private String courseid;

    /**
     * 内容
     */
    @ApiModelProperty(value = "内容")
    @TableField("content")
    private String content;

    /**
     * 已读状态（0-未读，1-已读）
     */
    @ApiModelProperty(value = "已读状态（0-未读，1-已读）")
    @TableField("readst")
    private String readst;

    /**
     * 学生提交时间
     */
    @ApiModelProperty(value = "学生提交时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @JSONField(format = "yyyy-MM-dd HH:mm")
    @TableField("subtime")
    private Date subtime;

    /**
     * 老师查看时间
     */
    @ApiModelProperty(value = "老师查看时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @JSONField(format = "yyyy-MM-dd HH:mm")
    @TableField("chetime")
    private Date chetime;

    /**
     * 反馈1
     */
    @ApiModelProperty(value = "反馈1")
    @TableField("a1")
    private String a1;

    /**
     * 反馈2
     */
    @ApiModelProperty(value = "反馈2")
    @TableField("a2")
    private String a2;

    /**
     * 反馈3
     */
    @ApiModelProperty(value = "反馈3")
    @TableField("a3")
    private String a3;

    /**
     * 反馈4
     */
    @ApiModelProperty(value = "反馈4")
    @TableField("a4")
    private String a4;

    /**
     * 反馈5
     */
    @ApiModelProperty(value = "反馈5")
    @TableField("a5")
    private String a5;

    /**
     * 反馈6
     */
    @ApiModelProperty(value = "反馈6")
    @TableField("a6")
    private String a6;

    @Override
    protected Serializable pkVal() {
        return id;
    }

}
