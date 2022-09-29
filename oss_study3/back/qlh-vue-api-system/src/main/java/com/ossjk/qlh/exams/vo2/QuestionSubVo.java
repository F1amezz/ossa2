package com.ossjk.qlh.exams.vo2;

import com.baomidou.mybatisplus.annotation.TableField;
import com.ossjk.qlh.exams.entity.Question;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "试题VO")
@Data
public class QuestionSubVo extends Question {
    /**
     * 
     */
    @ApiModelProperty(value = "科目名称")
    @TableField("sbname")
    private String sbname;
}
