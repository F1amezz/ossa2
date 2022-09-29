package com.ossjk.qlh.exams.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.ossjk.qlh.exams.entity.Exams;

import lombok.Data;

@Data
public class ExamsVo extends Exams {
    /**
     * 班级名称
     */

    @TableField("cname")
    private String cname;

    /**
     * 试卷名称
     */

    @TableField("exampName")
    private String exampName;

    /**
     * 出题人
     */
    @TableField("userName")
    private String userName;


    private int allNumber;

    private int hadNumber;
}
