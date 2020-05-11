package com.sinosoft.ap.common.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.alibaba.druid.pool.DruidDataSource;

@Configuration
public class DruidConfig {

    private Logger logger = LogManager.getLogger(DruidConfig.class);

    @Value("${spring.datasource.url}")
    private String dbUrlRead;
    
    @Value("${spring.datasource.username}")
    private String usernameRead;

    @Value("${spring.datasource.password}")
    private String passwordRead;

    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;

    @Value("${spring.datasource.initial-size:20}")
    private int initialSize;

    @Value("${spring.datasource.min-idle:10}")
    private int minIdle;

    @Value("${spring.datasource.max-active:50}")
    private int maxActive;

    @Value("${spring.datasource.max-wait:10000}")
    private int maxWait;

    @Value("${spring.datasource.time-between-eviction-runs-millis:60000}")
    private int timeBetweenEvictionRunsMillis;

    @Value("${spring.datasource.min-evictable-idle-time-millis:540000}")
    private int minEvictableIdleTimeMillis;

    @Value("${spring.datasource.validation-query:select 1}")
    private String validationQuery;

    @Value("${spring.datasource.test-while-idle:true}")
    private boolean testWhileIdle;

    @Value("${spring.datasource.test-on-borrow:true}")
    private boolean testOnBorrow;

    @Value("${spring.datasource.test-on-return:true}")
    private boolean testOnReturn;

    @Value("${spring.datasource.filters:stat}")
    private String filters;

//    @Value("${mybatis.mapper-locations}")
//    private String mapperlocation;


    @Bean
    @Primary
    public DataSource druidDataSourceReadOnly() throws Exception {
        DruidDataSource datasource = new DruidDataSource();
        datasource.setUrl(dbUrlRead);
        datasource.setUsername(usernameRead);
        datasource.setPassword(passwordRead);
        datasource.setDriverClassName(driverClassName);
        datasource.setInitialSize(initialSize);
        datasource.setMinIdle(minIdle);
        datasource.setMaxActive(maxActive);
        datasource.setMaxWait(maxWait);
        datasource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        datasource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        datasource.setValidationQuery(validationQuery);
        datasource.setTestWhileIdle(testWhileIdle);
        datasource.setTestOnBorrow(testOnBorrow);
        datasource.setTestOnReturn(testOnReturn);
        datasource.setFilters(filters);
        return datasource;
    }
    
    @Bean
    @Primary
    public DataSourceTransactionManager initDataSourceTransactionManagerRead() throws Exception{
    	return new DataSourceTransactionManager(druidDataSourceReadOnly());
    }
    
	@Bean
	@Primary
	public SqlSessionFactory readSqlSessionFactory(DataSource masterDataSource)
			throws Exception {
		final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
		sessionFactory.setDataSource(masterDataSource);
		sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
				.getResources("classpath*:com/sinosoft/ap/**/*.xml"));
		return sessionFactory.getObject();
	}
	
}

