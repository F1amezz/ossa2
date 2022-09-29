package com.ossjk.config.jwttoken;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * Copyright © 2021 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.config.jwttoken
 * @ClassName: JwtProperties
 * @Description: Jwt配置
 * @author: chair
 * @date: 2021年2月24日 上午11:54:47
 */
@ConfigurationProperties(prefix = "jwt")
@Data
public class JwtProperties {
	/**
	 * 密钥（4位或以上）
	 */
	private String secret;

	/**
	 * 时效
	 */
	private Long duration = 7200000L;

	/**
	 * 刷新时间
	 */
	private Long flush = 1800000L;

}
