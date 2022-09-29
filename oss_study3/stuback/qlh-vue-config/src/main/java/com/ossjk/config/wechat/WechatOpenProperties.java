package com.ossjk.config.wechat;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Copyright © 2021 QLH. Tech Ltd. All rights reserved.
 *
 * @Package: com.ossjk.config.wechat
 * @ClassName: WechatOpenProperties
 * @Description: 微信登录Properties
 * @author: chair
 * @date: 2021/8/4 22:31
 */
@ConfigurationProperties(prefix = "wechat.open")
@Data
public class WechatOpenProperties {
	/**
	 * 设置微信三方平台的appid
	 */
	private String appId;

	/**
	 * 设置微信三方平台的app secret
	 */
	private String secret;

	/**
	 * 微信扫码成功后重定向地址
	 */
	private String redirectUri;
	/**
	 * 没有绑定账号的回调地址
	 */
	private String bingCallBackUri;

	/**
	 * 微信和已有账号登录成功的回调地址
	 */
	private String webCallBackUri;

	/**
	 * 微信和已有账号登录失败的回调地址（如：帐号禁用情况）
	 */
	private String failCallBackUri;

}
