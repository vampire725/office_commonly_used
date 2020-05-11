package com.sinosoft.ap.component.verifycode.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.sinosoft.ap.component.verifycode.VerifyCode;


@Service
public class VerifyCodeServiceImpl  implements VerifyCodeService{

	@Override
	public void getVerifyCode(HttpServletRequest request, HttpServletResponse response, String namespace)throws Exception {
        response.setHeader("Pragma", "No-cache");  
        response.setHeader("Cache-Control", "no-cache");  
        response.setDateHeader("Expires", 0);  
        response.setContentType("image/jpeg");  
          
        //生成随机字串  
        String verifyCode = VerifyCode.generateVerifyCode(4);  
        //存入会话session  
        HttpSession session = request.getSession(true);  
        //删除以前的
        session.removeAttribute(namespace);
        session.setAttribute(namespace, verifyCode.toLowerCase());  
        //生成图片  
        int w = 100, h = 40;  
		VerifyCode.outputImage(w, h, response.getOutputStream(), verifyCode);
	}

}
