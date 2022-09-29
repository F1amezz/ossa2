package com.ossjk.qlh.edu.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.ossjk.qlh.edu.entity.Clazz;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "班级Vo")
@Data
public class ClazzVO extends Clazz {
    /**
     * 学生人数
     */
    @ApiModelProperty(value = "学生人数")
    @TableField("stucnt")
    private Integer stucnt;
}
