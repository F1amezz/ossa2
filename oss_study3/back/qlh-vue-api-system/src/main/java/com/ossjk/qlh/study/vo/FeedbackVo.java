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
