package com.ossjk.core.system.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Copyright © 2021 QLH. Tech Ltd. All rights reserved.
 *
 * @Package: com.ossjk.qlh.system.vo
 * @ClassName: WechatBindDto
 * @Description: 微信账号绑定Dto
 * @author: chair
 * @date: 2021/8/5 10:58
 */
@ApiModel("微信账号绑定Dto")
@Data
public class WechatBindDto implements Serializable {

    @ApiModelProperty(value = "账号")
    private String name;
    @ApiModelProperty(value = "密码")
    private String pwd;


    @ApiModelProperty(value = "微信openid")
    private String openid;
    @ApiModelProperty(value = "微信unionid")
    private String unionid;

}
