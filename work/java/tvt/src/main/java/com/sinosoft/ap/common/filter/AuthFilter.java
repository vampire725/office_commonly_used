package com.sinosoft.ap.common.filter;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.sinosoft.ap.common.constant.LogConstant;
import com.sinosoft.ap.component.authority.exception.AuthException;
import com.sinosoft.ap.util.result.Result;
import com.sinosoft.ap.util.result.ResultConstant;
import com.sinosoft.ap.util.string.StringUtil;
import com.sinosoft.ap.util.token.JavaWebToken;
import com.sinosoft.ap.util.token.TokenOutTimeException;

import net.sf.json.JSONObject;

public class AuthFilter implements Filter{

	private static Logger logger = LogManager.getFormatterLogger(AuthFilter.class);
	
	@SuppressWarnings("unused")
	private static String key = "619d10d98e3468e2";
	
	@Value("${authFilter.claim:/ap-system/*}")
	private String passPath;

	@Autowired
	private JavaWebToken javaWebToken;
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		logger.info("__________initJavaWebToken:"+javaWebToken+"success________________");
	}

	
	private List<String> list = new ArrayList<>();
	
	@PostConstruct
	public void init(){
		logger.info(passPath);
		StringBuffer sb = new StringBuffer();
		sb.append("/UserLogin&");
		sb.append("/UserLoginVerifyCode&");
		sb.append("/UserLoginForChild&");
    	if(!StringUtil.checkNull(passPath)) {
    		sb.append(passPath);
    	}
    	this.list = Arrays.asList(sb.toString().split("&"));
    	logger.info(list.size());
	}
	
	/**
	 * 判断当前会话是否有效
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        String origin = httpServletRequest.getHeader("Origin");
        if (httpServletResponse.getHeader("Access-Control-Allow-Credentials")==null||!httpServletResponse.getHeader("Access-Control-Allow-Credentials").equals("true")) {
        	httpServletResponse.setHeader("Access-Control-Allow-Origin", origin);
        	httpServletResponse.addHeader("Access-Control-Allow-Credentials", "true");
        	httpServletResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE, PATCH");
        	httpServletResponse.setHeader("Access-Control-Max-Age", "3600");
        	httpServletResponse.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, authorization");
        	httpServletResponse.setContentType("application/x-www-form-urlencoded;charset=utf-8"); 
        }
        String url = httpServletRequest.getRequestURI().substring(httpServletRequest.getContextPath().length());
        String token = httpServletRequest.getHeader(LogConstant.AUTHORIZATION);
        /**
         * 判断当前会话是够有效
         */
        if(httpServletRequest.getMethod().equalsIgnoreCase("OPTIONS")) {
        	OutputStream outputStream = httpServletResponse.getOutputStream();
    		String data = getResultInfo(ResultConstant.UN_LOGIN);
    		byte[] dataByteArr = data.getBytes("UTF-8");
    		outputStream.write(dataByteArr);
    		outputStream.flush();
    		outputStream.close();
    		httpServletRequest.removeAttribute("");
        }else{
        	logger.info(url);
        	if(url.contains("UserLogin")){
        		logger.info(url+">>>>>>>>OK!");
        		httpServletResponse.setStatus(200);
        		chain.doFilter(request, response);
        	}else if(!StringUtil.checkNull(token)){
        		try {
//        			this.javaWebToken.parserJavaWebToken(token);
//        			logger.info("当前登录用户token为："+token);
    				chain.doFilter(request, response);
        		} catch (TokenOutTimeException e) {
        			e.printStackTrace();
        			httpServletResponse.setStatus(500);
        			OutputStream outputStream = httpServletResponse.getOutputStream();
        			String data = getResultInfo(LogConstant.TOKEN_OUT_TIME_EXCEPTION);
        			byte[] dataByteArr = data.getBytes("UTF-8");
        			outputStream.write(dataByteArr);
        			outputStream.flush();
        			outputStream.close();
        			throw new TokenOutTimeException(LogConstant.TOKEN_OUT_TIME_EXCEPTION);
        		} catch (Exception e) {
					e.printStackTrace();
					OutputStream outputStream = httpServletResponse.getOutputStream();
	        		String data = getResultInfo(ResultConstant.UN_LOGIN);
	        		byte[] dataByteArr = data.getBytes("UTF-8");
	        		outputStream.write(dataByteArr);
	        		outputStream.flush();
	        		outputStream.close();
	        		throw new AuthException(ResultConstant.UN_LOGIN);
				}
        	}else if(StringUtil.checkNull(token)){
        		OutputStream outputStream = httpServletResponse.getOutputStream();
        		String data = getResultInfo(ResultConstant.UN_LOGIN);
        		byte[] dataByteArr = data.getBytes("UTF-8");
        		outputStream.write(dataByteArr);
        		outputStream.flush();
        		outputStream.close();
        		throw new AuthException(ResultConstant.UN_LOGIN);
        	}
        }
	}

	@Override
	public void destroy() {
		
	}
	
	private boolean check(String url) {
		for(String path:list) {
			if (url.contains(path)) {
				return true;
			}
		}
		return false;
	}
	
	private String getResultInfo (String string) {
		Result result = new Result();
		result.setStatus(-1);
		result.setMessage(string);
		JSONObject jsonObject = new JSONObject();
		jsonObject = JSONObject.fromObject(result);
		return jsonObject.toString();
	}

}
