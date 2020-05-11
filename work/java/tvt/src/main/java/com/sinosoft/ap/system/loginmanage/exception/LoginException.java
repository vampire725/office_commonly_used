package com.sinosoft.ap.system.loginmanage.exception;

public class LoginException extends RuntimeException{

	
	/**
	 * 自定义用户未登录异常
	 */
	private static final long serialVersionUID = -124416782778556071L;

	public LoginException(String str) {
		super(str);
	}
}
