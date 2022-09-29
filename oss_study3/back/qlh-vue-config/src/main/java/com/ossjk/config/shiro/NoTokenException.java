package com.ossjk.config.shiro;

import org.apache.shiro.authc.AuthenticationException;

/**
 * 
 * Copyright © 2021 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.config.shiro
 * @ClassName: NoTokenException 
 * @Description: 没有token异常
 * @author: chair
 * @date: 2021年3月7日 下午11:02:36
 */
public class NoTokenException extends AuthenticationException {

	public NoTokenException(String explanation) {
		super(explanation);
	}

	public NoTokenException() {
		super();
	}

	private static final long serialVersionUID = 3678497619904568096L;

}
