package com.sinosoft.ap.util.http;

/**
 * 自定义HTTP请求异常
 * @author 高秀和
 *
 */
public class HttpException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public HttpException(String msg){
		super(msg);
	}
}
