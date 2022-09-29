package com.ossjk.qlh.study.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.ossjk.qlh.study.entity.Summarize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("每日反馈Vo")
@Data
public class SummarizeVo extends Summarize {
    @ApiModelProperty(value = "学生名字")
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
}
