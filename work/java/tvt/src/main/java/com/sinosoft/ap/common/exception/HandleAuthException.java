package com.sinosoft.ap.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.sinosoft.ap.component.authority.exception.AuthException;
import com.sinosoft.ap.util.result.ResultConstant;


@ControllerAdvice
@ResponseBody
public class HandleAuthException {
	  /**
	   * 用户未登录
	   * @param e
	   * @return
	   */
	  @ResponseStatus(HttpStatus.FORBIDDEN)
	  @ExceptionHandler(AuthException.class)
	  @ResponseBody
	  public String handleAuthException(AuthException e){
		  return ResultConstant.UN_LOGIN;
	  }
	  
}
