package com.ossjk.config.shiro;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * 
 * Copyright © 2020 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.config.shiro
 * @ClassName: JwtToken 
 * @Description: Shiro token
 * @author: chair
 * @date: 2020年12月8日 下午8:57:01
 */
public class JwtToken implements AuthenticationToken {

	private String token;

	public JwtToken(String token) {
		this.token = token;
	}

	@Override
	public Object getPrincipal() {
		return token;
	}

	@Override
	public Object getCredentials() {
		return token;
	}
}
