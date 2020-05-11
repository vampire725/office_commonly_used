package com.sinosoft.ap.common.exception;

public class ServiceException extends RuntimeException {
	  /**
	 * 自定义服务器异常
	 */
	private static final long serialVersionUID = 5642849364412387581L;

	public ServiceException(String msg) {
	    super(msg);
	  }
	}