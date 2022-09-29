package com.ossjk.qlh.interview.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.ossjk.qlh.interview.entity.Company;
import com.ossjk.qlh.interview.entity.Interview;
import com.ossjk.qlh.interview.entity.Posts;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import java.io.Serializable;
import java.util.Date;


@ApiModel(value = "添加面试记录")
@Data
public class InterviewDTO implements Serializable {

    private Interview intv;

    private Posts post;

    private Company cmp;

}
