package com.ossjk.qlh.exams.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.ossjk.qlh.exams.entity.Question;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QuestionExampVo extends Question {
    @ApiModelProperty(value = "科目名称")
    @TableField("sbname")
    private String sbname;

    @ApiModelProperty(value = "试题名称")
    @TableField("name")
    private String qname;

    @ApiModelProperty(value = "试题类型")
    @TableField("type")
    private String qtype;

}
