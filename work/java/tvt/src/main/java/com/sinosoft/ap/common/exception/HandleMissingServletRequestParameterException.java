package com.sinosoft.ap.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;


@ControllerAdvice
@ResponseBody
public class HandleMissingServletRequestParameterException {

	  /**
	   * 400 - Bad Request
	   */
	  @ResponseStatus(HttpStatus.BAD_REQUEST)
	  @ExceptionHandler(MissingServletRequestParameterException.class)
	  public String handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
	    return "REQUIRED_PARAMETER_IS_NOT_PRESENT";
	  }
}
