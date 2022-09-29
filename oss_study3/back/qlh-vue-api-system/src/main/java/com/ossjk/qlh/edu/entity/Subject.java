package com.ossjk.qlh.edu.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.ossjk.core.base.entity.BaseEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Copyright 2022-06-14 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.edu.entity
 * @ClassName: Subject
 * @Description: 科目表-实体类
 * @author: lin
 * @date: 2022-06-14 19:24:30
 */
@ApiModel(value = "科目表")
@Data
public class Subject extends BaseEntity<Subject> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @ApiModelProperty(value = "id", required = true)
    @TableId("id")
    private String id;

    /**
     * 父id
     */
    @ApiModelProperty(value = "父id")
    @TableField("pid")
    private String pid;

    /**
     * 名称
     */
    @ApiModelProperty(value = "名称")
    @TableField("name")
    private String name;

    /**
     * 等级1容易2一般3困难
     */
    @ApiModelProperty(value = "等级1容易2一般3困难")
    @TableField("level")
    private Integer level;

    /**
     * 排序
     */
    @ApiModelProperty(value = "排序")
    @TableField("sort")
    private Integer sort;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    @TableField("remk")
    private String remk;

    /**
     * 课程天数
     */
    @ApiModelProperty(value = "课程天数")
    @TableField("days")
    private Integer days;

    /**
     * 视频路径
     */
    @ApiModelProperty(value = "视频路径")
    @TableField("url")
    private String url;

    /**
     * 课程id
     */
    @ApiModelProperty(value = "课程id")
    @TableField("cid")
    private String cid;

    @Override
    protected Serializable pkVal() {
        return id;
    }

}
