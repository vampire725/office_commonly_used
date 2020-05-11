package com.sinosoft.ap.component.cross;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.convert.converter.Converter;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.sinosoft.ap.common.filter.CrossFilter;
import com.sinosoft.ap.util.string.StringUtil;

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages={"com.sinosoft.ap.*"})
@MapperScan("com.sinosoft.**.domain")
public class CrossFilterConfig {
	
	private static Logger logger = LogManager.getLogger(CrossFilterConfig.class);
	
	
	@Bean
	@Order(1)
    public FilterRegistrationBean  crossFilterRegistrationBean() {
		FilterRegistrationBean registrationBean = new FilterRegistrationBean();
		CrossFilter httpBasicFilter = new CrossFilter();
		registrationBean.setFilter(httpBasicFilter);
		List<String> urlPatterns = new ArrayList<>();
	    urlPatterns.add("/*");
		System.out.println("http跨域请求配置的url是::::::::::/*");
	    registrationBean.setUrlPatterns(urlPatterns);
	    return registrationBean;
    }	
	
	
	
	@Bean
	public Converter<String, Date> addDateConvert() {
		return new Converter<String, Date>() {
			@Override
			public Date convert(String source) {
				Date date = null;
				if(StringUtil.checkNull(source)) {
					return date;
				}
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				try {
					date = sdf.parse((String) source);
				} catch (ParseException e) {
					logger.error(e);
				}
				return date;
			}
		};
	}
	
	
	@Bean
	public Converter<String, String> addStringConvert() {
		return new Converter<String, String>() {
			@Override
			public String convert(String source) {
				return source.replace("<", "&lt;").replace(">", "&gt;");
			}
		};
	}
	
	
	@Bean
    public PlatformTransactionManager txManager(DataSource dataSource) throws SQLException {
        return new DataSourceTransactionManager(dataSource);
    }
}
