package com.sinosoft.ap.common.aop.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 启用日志
 * <br><p>
 * @author Gaoxh
 * @version 1.0.0-Beta
 * @since 2017年5月17日11:29:08
 * JDK version used: 1.8.0_121
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target( { ElementType.METHOD })
@Documented
public @interface Info {

	//方法名
	String value() default "";
}
