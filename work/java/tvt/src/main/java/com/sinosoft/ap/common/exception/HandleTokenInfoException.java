//package com.sinosoft.ap.common.exception;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.bind.annotation.ResponseStatus;
//
//import com.sinosoft.ap.system.loginmanage.exception.TokenInfoException;
//import com.sinosoft.ap.util.result.ResultConstant;
//
//@ControllerAdvice
//@ResponseBody
//public class HandleTokenInfoException {
//	  /**
//	   * 数据重复异常
//	   * @param e
//	   * @return
//	   */
//	  @ResponseStatus(HttpStatus.OK)
//	  @ExceptionHandler(TokenInfoException.class)
//	  public String handleAlreadyExistException(TokenInfoException e){
//		  return ResultConstant.ALREADY_EXIST;
//	  }
//	  
//}
