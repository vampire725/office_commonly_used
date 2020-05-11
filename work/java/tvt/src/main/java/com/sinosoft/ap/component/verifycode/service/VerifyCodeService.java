package com.sinosoft.ap.component.verifycode.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface VerifyCodeService {

	/**
	 * 验证码服务
	 * @param request
	 * @param response
	 * @param namespace
	 * 验证码存放名称
	 */
	void getVerifyCode(HttpServletRequest request, HttpServletResponse response, String namespace) throws Exception ;
}
