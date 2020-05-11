package com.sinosoft.ap.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.sinosoft.ap.util.http.HttpException;
import com.sinosoft.ap.util.result.ResultConstant;

@ControllerAdvice
@ResponseBody
public class HandleHttpException {

	  /**
	   * Http请求异常
	   * @param e
	   * @return
	   */
	  @ResponseStatus(HttpStatus.BAD_REQUEST)
	  @ExceptionHandler(HttpException.class)
	  public String handletiHttpException(HttpException e){
		  return ResultConstant.NULL_ATTRIBUTE;
	  }
}
