package com.sinosoft.ap.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.sinosoft.ap.util.result.ResultConstant;


@ControllerAdvice
@ResponseBody
public class HandleNullAttributeException {

	  /**
	   * 参数为空
	   * @param e
	   * @return
	   */
	  @ResponseStatus(HttpStatus.FORBIDDEN)
	  @ExceptionHandler(NullAttributeException.class)
	  public String handleNullAttributeException(NullAttributeException e){
		  return ResultConstant.NULL_ATTRIBUTE;
	  }
}
