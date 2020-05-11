package com.sinosoft.ap.common.exception;

public class NullAttributeException extends RuntimeException {
	
	/**
	 * 自定义参数为空异常
	 */
	private static final long serialVersionUID = 8114351526013581994L;

	public NullAttributeException(String str) {
		super(str);
	}

}
