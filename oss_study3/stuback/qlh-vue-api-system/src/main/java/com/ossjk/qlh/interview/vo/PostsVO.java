package com.ossjk.qlh.interview.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.ossjk.qlh.interview.entity.Posts;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "岗位VO")
@Data
public class PostsVO extends Posts {

    @ApiModelProperty(value = "公司名称")
    @TableField("cname")
    private String cname;

    @ApiModelProperty(value = "面试次数")
    @TableField("mtimes")
    private Integer mtimes;

    @ApiModelProperty(value = "公司名称")
    @TableField("offered")
    private String offered;

}
