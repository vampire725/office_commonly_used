package com.sinosoft.ap.common.aop.handlepoint;

import java.lang.reflect.Method;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import com.sinosoft.ap.common.aop.annotation.Info;

/**
 * 处理@Info注解
 * @author Gaoxh
 * @Time 2017年6月3日
 * @Version 1.0.0-Beta
 * JDK version used: 1.8.0_121
 *
 */
@Aspect
@Component
public class HandleInfoAnnotation {
	
	static Logger log = LogManager.getLogger(HandleInfoAnnotation.class);

	@Pointcut("@annotation(com.sinosoft.ap.common.aop.annotation.Info)")
	public void annotationPointCut(){};
	
	@After("annotationPointCut()")
	public void after(JoinPoint joinPoint) {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();
		Info info = method.getAnnotation(Info.class);
		log.debug("# # # # # # # # # # # # # # # # # # # # # # 退出"+info.value()+method+"# # # # # # # # # # # # # # # # # # # # # # ");
	}
	
	@Before(value = "annotationPointCut()")
	public void Before(JoinPoint joinPoint) {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();
		Info info = method.getAnnotation(Info.class);
		log.debug("# # # # # # # # # # # # # # # # # # # # # # 进入"+info.value()+"# # # # # # # # # # # # # # # # # # # # # # ");
		log.debug(method);
	}
}
