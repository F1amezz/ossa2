package com.ossjk.qlh.study.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.ossjk.qlh.study.entity.Selfevaluation;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "自我评价VO")
@Data
public class SelfevaluationVo extends Selfevaluation {
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
}
