package com.ossjk.qlh.exams.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@Data
public class ExamsEchVo {

    /**
     * 学生名字数组
     */
    @TableField(exist = false)
    private String sid;

    /**
     * 考试人数
     */
    @TableField(exist = false)
    private String couex;

    /**
     * 最高分
     */
    @TableField(exist = false)
    private String maxscore;

    /**
     * 最低分
     */
    @TableField(exist = false)
    private String minscore;

    /**
     * 平均分
     */
    @TableField(exist = false)
    private String avgscore;

    /**
     * 及格人数
     */
    @TableField(exist = false)
    private String jige;

    /**
     * 学生名字
     */
    @TableField(exist = false)
    private String name;

    /**
     * 学生分数
     */
    @TableField(exist = false)
    private String score;

}
