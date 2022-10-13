package com.ossjk.qlh.study.entity;

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
 * Copyright 2022-06-13 QLH. Tech Ltd. All rights reserved.
 *
 * @Package: com.ossjk.qlh.xuexi.entity
 * @ClassName: Summarize
 * @Description: -实体类
 * @author: Rick.yang
 * @date: 2022-06-13 18:03:21
 */
@ApiModel(value = "考勤实体")
@Data
public class Attendance extends BaseEntity<Attendance> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @ApiModelProperty(value = "", required = true)
    @TableId("id")
    private String id;

    /**
     * 班级id
     */
    @ApiModelProperty(value = "学生id")
    @TableField("stuid")
    private String sid;


    /**
     *
     */
    @ApiModelProperty(value = "考勤日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JSONField(format = "yyyy-MM-dd")
    @TableField("kqdate")
    private Date kqdate;

    /**
     * 内容
     */
    @ApiModelProperty(value = "上班打卡")
    @DateTimeFormat(pattern = "HH:mm:ss")
    @JSONField(format = "HH:mm:ss")
    @TableField("chkin")
    private String chkin;

    /**
     * 已读状态（0-未读，1-已读）
     */
    @ApiModelProperty(value = "下班打卡）")
    @DateTimeFormat(pattern = "HH:mm:ss")
    @JSONField(format = "HH:mm:ss")
    @TableField("chkout")
    private String chkout;

    /**
     * 确认时间
     */
    @ApiModelProperty(value = "确认时间")
    @TableField("cfmdate")
    private String cfmdate;

    /**
     * 反馈3
     */
    @ApiModelProperty(value = "状态")
    @TableField("state")
    private Integer state;

    @ApiModelProperty(value = "反馈")
    @TableField("feedback")
    private String feedback;

    @ApiModelProperty(value = "考勤结果")
    @TableField("result")
    private String result;


    /**
     * 反馈4
     */
    @ApiModelProperty(value = "异常反馈")
    @TableField("remarks")
    private String remarks;


    @Override
    protected Serializable pkVal() {
        return id;
    }

}
