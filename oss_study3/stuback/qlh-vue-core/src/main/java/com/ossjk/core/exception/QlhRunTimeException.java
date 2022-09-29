package com.ossjk.core.exception;

/**
 * 
 * Copyright © 2021 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.core.exception
 * @ClassName: QlhRunTimeException
 * @Description: Qlh运行时异常
 * @author: chair
 * @date: 2021年2月24日 下午12:03:02
 */
public class QlhRunTimeException extends RuntimeException {

	public QlhRunTimeException() {
	}

	public QlhRunTimeException(String message) {
		super(message);
	}

	public QlhRunTimeException(String message, Throwable cause) {
		super(message, cause);
	}

	public QlhRunTimeException(Throwable cause) {
		super(cause);
	}

	public QlhRunTimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
