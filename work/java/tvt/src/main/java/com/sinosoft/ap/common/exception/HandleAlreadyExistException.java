package com.sinosoft.ap.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.sinosoft.ap.util.result.ResultConstant;


@ControllerAdvice
@ResponseBody
public class HandleAlreadyExistException {
	  /**
	   * 数据重复异常
	   * @param e
	   * @return
	   */
	  @ResponseStatus(HttpStatus.BAD_REQUEST)
	  @ExceptionHandler(AlreadyExistException.class)
	  public String handleAlreadyExistException(AlreadyExistException e){
		  return ResultConstant.ALREADY_EXIST;
	  }
	  
}