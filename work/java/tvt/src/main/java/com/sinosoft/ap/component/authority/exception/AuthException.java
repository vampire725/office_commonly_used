package com.sinosoft.ap.component.authority.exception;

public class AuthException extends RuntimeException{

	/**
	 * 自定义身份验证异常
	 */
	private static final long serialVersionUID = 7679571171540821554L;
	
	public AuthException(String msg){
		super(msg);
	}

}
