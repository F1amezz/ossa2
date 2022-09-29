package com.ossjk.qlh.study.vo;

import com.baomidou.mybatisplus.annotation.TableField;

import cn.afterturn.easypoi.excel.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("/班级vo")
@Data
public class MyclassVo {
    @ApiModelProperty(value = "学生名称")
    @TableField("sname")
    private String sname;

    /**
     * 班级名字
     */
    @ApiModelProperty(value = "班级名字")
    @TableField("cname")
    private String cname;

    /**
     * 老师
     */
    @ApiModelProperty(value = "老师")
    @TableField("tname")
    private String tname;

    /**
     * 课程
     */
    @ApiModelProperty(value = "课程")
    @TableField("course")
    private String course;

    /**
     * 班长
     */
    @ApiModelProperty(value = "班长")
    @TableField("monitor")
    private String monitor;

    /**
     * 头像
     */
    @ApiModelProperty(value = "头像")
    @TableField("head")
    private String head;

    /**
     * 手机
     */
    @ApiModelProperty(value = "手机", required = true)
    @Excel(name = "手机号码")
    @TableField("mobile")
    private String mobile;

}
