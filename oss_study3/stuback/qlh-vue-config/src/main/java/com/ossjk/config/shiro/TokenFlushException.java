package com.ossjk.config.shiro;

import org.apache.shiro.authc.AuthenticationException;
/**
 * 
 * Copyright © 2020 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.config.shiro
 * @ClassName: TokenFlushException 
 * @Description: token刷新异常
 * @author: chair
 * @date: 2020年12月14日 下午10:02:27
 */
public class TokenFlushException extends AuthenticationException {
	
	private static final long serialVersionUID = 1L;

	public TokenFlushException(String explanation) {
		super(explanation);
	}

	public TokenFlushException() {
		super();
	}

}
