package com.sinosoft.ap.common.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.sinosoft.ap.util.result.ResultConstant;


@ControllerAdvice
@ResponseBody
public class HandleDataIntegrityViolationException {
	  /**
	   * 操作数据库出现异常:名称重复，外键关联
	   */
	  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	  @ExceptionHandler(DataIntegrityViolationException.class)
	  public String handleDataIntegrityViolationException(DataIntegrityViolationException e) {
	    return ResultConstant.DATABASE_ERROR;
	  }
}
