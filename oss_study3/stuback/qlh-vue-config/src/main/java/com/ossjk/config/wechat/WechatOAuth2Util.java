package com.ossjk.config.wechat;

import me.chanjar.weixin.common.bean.oauth2.WxOAuth2AccessToken;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.open.api.impl.WxOpenInMemoryConfigStorage;
import me.chanjar.weixin.open.api.impl.WxOpenOAuth2ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;


/**
 * 
 * Copyright © 2021 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.config.wechat
 * @ClassName: WechatOAuth2Util 
 * @Description: 微信登录工具
 * @author: chair
 * @date: 2021年8月8日 下午11:48:53
 */
@Component
@EnableConfigurationProperties({ WechatOpenProperties.class })
public class WechatOAuth2Util {
	@Autowired
	private WechatOpenProperties wechatOpenProperties;
	private WxOpenOAuth2ServiceImpl service;

	@PostConstruct
	public void init() {
		service = new WxOpenOAuth2ServiceImpl(wechatOpenProperties.getAppId(), wechatOpenProperties.getSecret());
		service.setWxOpenConfigStorage(new WxOpenInMemoryConfigStorage());
	}

	public WxOAuth2AccessToken getAccessToken(String code) throws WxErrorException {
		return service.getAccessToken(code);
	}

	public WechatOpenProperties getWechatOpenProperties(){
		return wechatOpenProperties;
	}
}
