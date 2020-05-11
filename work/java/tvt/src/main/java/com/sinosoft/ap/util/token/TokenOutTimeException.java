package com.sinosoft.ap.util.token;

public class TokenOutTimeException extends RuntimeException {

	/**
	 * 自定义token超时错误
	 */
	private static final long serialVersionUID = -1271799137225774195L;
	
	public TokenOutTimeException (String str) {
		super(str);
	}
	
}
