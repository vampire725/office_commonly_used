//package com.sinosoft.ap.common.exception;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.bind.annotation.ResponseStatus;
//
//import com.sinosoft.ap.util.token.TokenOutTimeException;
//
///**
// * @description:全局异常处理
// */
//@ControllerAdvice
//@ResponseBody
//public class HandleTokenOutTimeException {
//  /**
//   * 400 - Bad Request
//   */
//  @ResponseStatus(HttpStatus.BAD_REQUEST)
//  @ExceptionHandler(TokenOutTimeException.class)
//  public String handleValidationException(TokenOutTimeException e) {
//    return "validation_exception";
//  }
//}
