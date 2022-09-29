package com.ossjk.core.exception;

/**
 * 
 * Copyright © 2020 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.core.exception
 * @ClassName: QlhException 
 * @Description: Qlh异常
 * @author: chair
 * @date: 2020年12月8日 下午8:56:06
 */
public class QlhException extends Exception {

	public QlhException() {
	}

	public QlhException(String message) {
		super(message);
	}

	public QlhException(String message, Throwable cause) {
		super(message, cause);
	}

	public QlhException(Throwable cause) {
		super(cause);
	}

	public QlhException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
