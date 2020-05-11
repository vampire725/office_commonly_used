package com.sinosoft.ap.system.loginmanage.web;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.sinosoft.ap.common.aop.annotation.Info;
import com.sinosoft.ap.common.constant.LogConstant;
import com.sinosoft.ap.util.result.Result;
import com.sinosoft.ap.util.result.ResultConstant;
import com.sinosoft.ap.util.token.JavaWebToken;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/ap-system")
@Api(value="用户退出管理",description="用户退出管理")
public class UserLogoutController {
	
	@Autowired
	private JavaWebToken javaWebToken;

	
	/**
	 * 登录用户退出接口，推迟成功时销毁session
	 * @param request
	 * @return
	 */
	@Info("登录用户退出接口")
	@RequestMapping("/logout")
	@ResponseBody
	@ApiOperation(value="登录用户退出接口",response=Result.class,httpMethod="POST")
	public Result logout(ServletRequest request){
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		String token = httpServletRequest.getHeader(LogConstant.AUTHORIZATION);
		try {
			this.javaWebToken.delete(token);
			return new Result(ResultConstant.SUCCESS_STATU, ResultConstant.LOGOUT_SUCCESS);
		} catch (Exception e) {
			return new Result(ResultConstant.SUCCESS_STATU, ResultConstant.LOGOUT_SUCCESS);
		}
	}
}
