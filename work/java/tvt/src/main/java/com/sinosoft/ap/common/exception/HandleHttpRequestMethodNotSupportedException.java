package com.sinosoft.ap.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;


@ControllerAdvice
@ResponseBody
public class HandleHttpRequestMethodNotSupportedException {

	  /**
	   * 405 - Method Not Allowed
	   */
	  @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
	  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	  public String handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
	    return "request_method_not_supported";
	  }
}
