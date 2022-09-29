package com.ossjk.qlh.study.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.ossjk.qlh.study.entity.Feedback;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("/名字vo")
@Data
public class FeedbackVo extends Feedback {
    @ApiModelProperty(value = "学生名称")
    @TableField("sname")
    private String sname;

    @ApiModelProperty(value = "班级名称")
    @TableField("cname")
    private String cname;

    @ApiModelProperty(value = "老师名称")
    @TableField("tname")
    private String tname;

    @ApiModelProperty(value = "课程名称")
    @TableField("course")
    private String course;

}
