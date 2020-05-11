package com.sinosoft.ap.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;


@ControllerAdvice
@ResponseBody
public class HandleHttpMessageNotReadableException {
	  /**
	   * 400 - Bad Request
	   */
	  @ResponseStatus(HttpStatus.BAD_REQUEST)
	  @ExceptionHandler(HttpMessageNotReadableException.class)
	  public String handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
	    return "could_not_read_json";
	  }
}
