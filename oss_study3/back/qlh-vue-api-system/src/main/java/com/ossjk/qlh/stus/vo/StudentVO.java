package com.ossjk.qlh.stus.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@ApiModel(value = "学生Vo")
@Data
public class StudentVO implements Serializable {


    /**
     * id
     */
    @ApiModelProperty(value = "", required = true)
    @TableId("id")
    private String id;
    /**
     * 姓名
     */
    @ApiModelProperty(value = "name")
    @TableField("name")
    private String value;

}
