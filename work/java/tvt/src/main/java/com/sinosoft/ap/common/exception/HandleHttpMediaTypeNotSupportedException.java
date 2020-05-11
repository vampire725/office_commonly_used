package com.sinosoft.ap.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;


@ControllerAdvice
@ResponseBody
public class HandleHttpMediaTypeNotSupportedException {
	  /**
	   * 415 - Unsupported Media Type
	   */
	  @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
	  @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
	  public String handleHttpMediaTypeNotSupportedException(Exception e) {
	    return "content_type_not_supported";
	  }
}
