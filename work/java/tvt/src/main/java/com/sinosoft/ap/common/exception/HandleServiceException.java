package com.sinosoft.ap.common.exception;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;


@ControllerAdvice
@ResponseBody
public class HandleServiceException {

	  /**
	   * 400 - Bad Request
	   */
	  @ResponseStatus(HttpStatus.BAD_REQUEST)
	  @ExceptionHandler(ConstraintViolationException.class)
	  public String handleServiceException(ConstraintViolationException e) {
	    Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
	    ConstraintViolation<?> violation = violations.iterator().next();
	    String message = violation.getMessage();
	    return "parameter:" + message;
	  }
}
