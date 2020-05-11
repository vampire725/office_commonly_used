package com.sinosoft.ap.common.config;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import javax.servlet.Filter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sinosoft.ap.common.filter.AuthFilter;
import org.springframework.core.annotation.Order;

@Configuration
public class FilterConf {
	
	private static Logger logger = LogManager.getLogger(FilterConf.class);
	
	@Value("${authFilter.claim:/ap-system/*}")
	private String claim;
	
	@Bean
	public Filter getAuthFilter() {
		AuthFilter authFilter = new AuthFilter();
		logger.info("------------------------------初始化filter"+authFilter+"----------------------------");
		logger.info("------------------------------过滤器加载"+claim+"---------------------------------------");
		return authFilter;
	}
	
	@Bean
    public FilterRegistrationBean  anthFilterRegistrationBean() {
		FilterRegistrationBean registrationBean = new FilterRegistrationBean();
		registrationBean.setFilter(getAuthFilter());
		List<String> urlPatterns = new ArrayList<String>();
		String[] claims = claim.split("&");
		Stream.of(claims).forEach(n -> {
			urlPatterns.add(n);
		});
		registrationBean.setUrlPatterns(urlPatterns);
		return registrationBean;
    }
}
