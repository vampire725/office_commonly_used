package com.sinosoft.ap.common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.sinosoft.ap.util.visitweb.VisitInfo;


@WebFilter
public class CrossFilter implements Filter{


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    /**
     * 使跨域请求能够接收到返回参数
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    	HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        String ip =  VisitInfo.getIpAddr(httpServletRequest);
        String origin = httpServletRequest.getHeader("Origin");
        if (httpServletResponse.getHeader("Access-Control-Allow-Credentials")==null||!httpServletResponse.getHeader("Access-Control-Allow-Credentials").equals("true")) {
        	httpServletResponse.setHeader("Access-Control-Allow-Origin", origin);
        	httpServletResponse.addHeader("Access-Control-Allow-Credentials", "true");
        	httpServletResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE, PATCH");
        	httpServletResponse.setHeader("Access-Control-Max-Age", "3600");
        	httpServletResponse.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, authorization");
        	httpServletResponse.setContentType("application/x-www-form-urlencoded;charset=utf-8"); 
        }
//        httpServletResponse.setHeader("Access-Control-Allow-Origin", origin);
//        httpServletResponse.addHeader("Access-Control-Allow-Credentials", "true");
//        httpServletResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE, PATCH");
//        httpServletResponse.setHeader("Access-Control-Max-Age", "3600");
//        httpServletResponse.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, authorization");
//        httpServletResponse.setContentType("application/x-www-form-urlencoded;charset=utf-8"); 
        chain.doFilter(request, response);

    }


    @Override
    public void destroy() {
        System.out.println("destroy method");
    }

}
