package com.sinosoft.ap.common.exception;

public class AlreadyExistException extends RuntimeException {

	/**
	 * 自定义数据重复错误
	 */
	private static final long serialVersionUID = -2312437927425449241L;

	public AlreadyExistException (String str) {
		super(str);
	}
	
}
