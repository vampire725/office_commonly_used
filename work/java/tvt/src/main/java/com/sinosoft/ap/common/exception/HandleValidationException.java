package com.sinosoft.ap.common.exception;

import javax.validation.ValidationException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;


/**
 * @description:全局异常处理
 */
@ControllerAdvice
@ResponseBody
public class HandleValidationException {
  /**
   * 400 - Bad Request
   */
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(ValidationException.class)
  public String handleValidationException(ValidationException e) {
    return "validation_exception";
  }
}
