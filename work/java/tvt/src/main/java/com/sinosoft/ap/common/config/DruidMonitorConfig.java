//package com.sinosoft.ap.common.config;
//
//import org.springframework.aop.framework.autoproxy.BeanNameAutoProxyCreator;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.web.servlet.FilterRegistrationBean;
//import org.springframework.boot.web.servlet.ServletRegistrationBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import com.alibaba.druid.support.http.StatViewServlet;
//import com.alibaba.druid.support.http.WebStatFilter;
//import com.alibaba.druid.support.spring.stat.DruidStatInterceptor;
//
//@Configuration
//public class DruidMonitorConfig {
//
//	    @Value("${spring.datasource.logSlowSql}")
//	    private String logSlowSql;
//
//	    @Value("${druid.allow.ip}")
//	    private String allowIp;
//
//	   @Bean
//	    public ServletRegistrationBean druidServlet() {
//	        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean();
//	        servletRegistrationBean.setServlet(new StatViewServlet());
//	        servletRegistrationBean.addUrlMappings("/druid/*");
//			servletRegistrationBean.addInitParameter("allow",allowIp);
//			servletRegistrationBean.addInitParameter("loginUsername","zkrapplicationmonitor");
//			servletRegistrationBean.addInitParameter("loginPassword","ZKRmonitor");
//			servletRegistrationBean.addInitParameter("resetEnable","false");
//			servletRegistrationBean.addInitParameter("logSlowSql", logSlowSql);
//	        return servletRegistrationBean;
//	    }
//
//	    @Bean(name="APDruidStatInterceptor")
//	    public DruidStatInterceptor init(){
//	    	return new DruidStatInterceptor();
//	    }
//
//	    @Bean()
//	    public BeanNameAutoProxyCreator initBeanNameAutoProxyCreator() {
//	    	BeanNameAutoProxyCreator beanNameAutoProxyCreator = new BeanNameAutoProxyCreator();
//	    	beanNameAutoProxyCreator.setProxyTargetClass(true);
//	    	beanNameAutoProxyCreator.setBeanNames("com.sinosoft.ap.*");
//	    	beanNameAutoProxyCreator.setInterceptorNames("APDruidStatInterceptor");
//	    	return beanNameAutoProxyCreator;
//	    }
//
//
//
//	    @Bean
//	    public FilterRegistrationBean filterRegistrationBean() {
//	        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
//	        filterRegistrationBean.setFilter(new WebStatFilter());
//	        filterRegistrationBean.addUrlPatterns("/*");
//	        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
//	        filterRegistrationBean.addInitParameter("profileEnable", "true");
//	        return filterRegistrationBean;
//	    }
//}
