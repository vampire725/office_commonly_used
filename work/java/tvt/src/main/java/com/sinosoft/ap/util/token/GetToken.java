package com.sinosoft.ap.util.token;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import com.google.common.annotations.GwtCompatible;
import com.sinosoft.ap.common.constant.LogConstant;


/**
 * 获取请求中的token
 * @author : 高秀和
 * @date : 2017年11月21日-上午12:23:58
 * @since : V1.0
 * @version : V1.0
 */
@GwtCompatible(emulated = true)
public final class GetToken {

	public static String analysis(ServletRequest request){
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		String token = httpServletRequest.getHeader(LogConstant.AUTHORIZATION);
		return token;
	}
}
