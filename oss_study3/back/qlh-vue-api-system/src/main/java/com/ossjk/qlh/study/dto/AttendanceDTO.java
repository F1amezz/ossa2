package com.ossjk.qlh.study.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.ossjk.qlh.study.entity.Attendance;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@ApiModel(value = "考勤DTO")
@Data
public class AttendanceDTO extends Attendance {

    @ApiModelProperty(value = "学生姓名")
    @TableField("stuname")
    private String stuname;

    @ApiModelProperty(value = "班级名称")
    @TableField("clzname")
    private String clzname;

    @ApiModelProperty(value = "考勤日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JSONField(format = "yyyy-MM-dd")
    @TableField("kqdate")
    private Date kqdate;
}
