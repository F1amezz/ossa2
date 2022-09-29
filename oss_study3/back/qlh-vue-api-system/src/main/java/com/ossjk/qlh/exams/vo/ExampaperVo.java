package com.ossjk.qlh.exams.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.ossjk.qlh.exams.entity.Exampaper;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ExampaperVo  extends Exampaper {
    /**
     * 创建人名称
     */
    @ApiModelProperty(value = "创建人名称")
    private String crerName;

}
