package com.ossjk.qlh.system.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Copyright © 2021 QLH. Tech Ltd. All rights reserved.
 *
 * @Package: com.ossjk.qlh.system.dto
 * @ClassName: WechatOauth2Dto
 * @Description:微信二维码登录信息
 * @author: chair
 * @date: 2021/8/4 22:44
 */
@ApiModel("微信二维码登录信息")
@Data
public class WechatLoginInfo implements Serializable {
    /**
     * 设置微信三方平台的appid
     */
    @ApiModelProperty(value = "设置微信三方平台的appid")
    private String appId;


    /**
     * 重定向地址
     */
    @ApiModelProperty(value = "重定向地址")
    private  String redirectUri;
}
