package com.ossjk.qlh.edu.entity;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.ossjk.core.base.entity.BaseEntity;
import com.ossjk.core.system.annotation.DbColumnMapper;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Copyright 2022-06-08 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.edu.entity
 * @ClassName: Clazz
 * @Description: 班级表-实体类
 * @author: Rick.yang
 * @date: 2022-06-08 19:34:03
 */
@ApiModel(value = "班级表")
@Data
public class Clazz extends BaseEntity<Clazz> {

    private static final long serialVersionUID = 1L;

    /**
     * 班级id
     */
    @ApiModelProperty(value = "班级id", required = true)
    @TableId("id")
    private String id;

    /**
     * 班级名称
     */
    @ApiModelProperty(value = "班级名称")
    @TableField("name")
    private String name;

    /**
     * 所属课程名称+版本
     */
    @DbColumnMapper(columns = "name", tableName = "course", condition = "id")
    @ApiModelProperty(value = "所属课程id")
    @TableField("cid")
    private String cid;

    /**
     * 带班老师id
     */
    @DbColumnMapper(columns = "name", tableName = "user", condition = "id")
    @ApiModelProperty(value = "带班老师id")
    @TableField("tid")
    private String tid;

    /**
     * 带班老师id
     */
    @ApiModelProperty(value = "带班老师纪录")
    @TableField("historyteacher")
    private String historyteacher;

    /**
     * 类型：线上推广班，线下推广班，培训班
     */
    @ApiModelProperty(value = "类型：线上推广班，线下推广班，培训班")
    @TableField("types")
    private String types;

    /**
     * 状态：预科，授课
     */
    @ApiModelProperty(value = "状态：预科，授课")
    @TableField("state")
    private String state;

    /**
     * 该班班长
     */
    @ApiModelProperty(value = "该班班长")
    @TableField("monitor")
    private String monitor;

    /**
     * 预科时间
     */
    @ApiModelProperty(value = "预科时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JSONField(format = "yyyy-MM-dd")
    @TableField("prepTime")
    private Date preptime;

    /**
     * 开班时间
     */
    @ApiModelProperty(value = "开班时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JSONField(format = "yyyy-MM-dd")
    @TableField("startTime")
    private Date starttime;

    /**
     * 结课时间
     */
    @ApiModelProperty(value = "结课时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JSONField(format = "yyyy-MM-dd")
    @TableField("endTime")
    private Date endtime;

    /**
     * 班级默认密码
     */
    @ApiModelProperty(value = "班级默认密码")
    @TableField("deftpwd")
    private String deftpwd;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    @TableField("remark")
    private String remark;

    @Override
    protected Serializable pkVal() {
        return id;
    }

}
