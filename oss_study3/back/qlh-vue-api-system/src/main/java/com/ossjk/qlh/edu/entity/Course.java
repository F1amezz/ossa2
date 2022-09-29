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
 * @ClassName: Course
 * @Description: 课程表-实体类
 * @author: lin
 * @date: 2022-06-14 19:24:30
 */
@ApiModel(value = "课程表")
@Data
public class Course extends BaseEntity<Course> {

    private static final long serialVersionUID = 1L;

    /**
     * 课程id
     */
    @ApiModelProperty(value = "课程id", required = true)
    @TableId("id")
    private String id;

    /**
     * 1-web前端、2-java后端、3-测试
     */
    @ApiModelProperty(value = "1-web前端、2-java后端、3-测试", required = true)
    @TableField("type")
    private Integer type;

    /**
     * 年份
     */
    @ApiModelProperty(value = "年份", required = true)
    @TableField("year")
    private Integer year;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    @TableField("remk")
    private String remk;

    /**
     * 课程名称
     */
    @ApiModelProperty(value = "课程名称")
    @TableField("name")
    private String name;

    /**
     * 课程天数
     */
    @ApiModelProperty(value = "课程天数")
    @TableField("days")
    private Integer days;

    /**
     * 版本号
     */
    @ApiModelProperty(value = "版本号")
    @TableField("version")
    private String version;

    /**
     * 科目id
     */
    @ApiModelProperty(value = "科目id")
    @TableField("sid")
    private String sid;

    @Override
    protected Serializable pkVal() {
        return id;
    }

}
