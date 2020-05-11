package com.sinosoft.ap.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;


@ControllerAdvice
@ResponseBody
public class HandleBindException {
	  /**
	   * 400 - Bad Request
	   */
	  @ResponseStatus(HttpStatus.BAD_REQUEST)
	  @ExceptionHandler(BindException.class)
	  public String handleBindException(BindException e) {
	    BindingResult result = e.getBindingResult();
	    FieldError error = result.getFieldError();
	    String field = error.getField();
	    String code = error.getDefaultMessage();
	    String message = String.format("%s:%s", field, code);
	    return message;
	  }
	  
}