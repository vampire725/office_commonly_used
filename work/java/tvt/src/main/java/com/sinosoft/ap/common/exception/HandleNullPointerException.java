package com.sinosoft.ap.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.sinosoft.ap.util.result.ResultConstant;


@ControllerAdvice
@ResponseBody
public class HandleNullPointerException {

	  /**
	   * 空指针异常
	   * @param e
	   * @return
	   */
	  @ResponseStatus(HttpStatus.FORBIDDEN)
	  @ExceptionHandler(NullPointerException.class)
	  public String handleNullPointerException(NullPointerException e){
		  return ResultConstant.NULL_ATTRIBUTE;
	  }
}
