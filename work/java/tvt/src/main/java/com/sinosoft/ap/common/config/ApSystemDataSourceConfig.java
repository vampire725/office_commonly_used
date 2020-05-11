//package com.sinosoft.ap.common.config;
//
//
//import com.alibaba.druid.pool.DruidDataSource;
//import com.sinosoft.ap.util.string.StringUtil;
//
//import org.apache.commons.lang.ArrayUtils;
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.mybatis.spring.SqlSessionFactoryBean;
//import org.mybatis.spring.annotation.MapperScan;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.core.io.Resource;
//import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
//import org.springframework.jdbc.datasource.DataSourceTransactionManager;
//
//import javax.sql.DataSource;
///**
// * 用于配置数据源
// *
// */
//
//@Configuration
//@MapperScan(basePackages = ApSystemDataSourceConfig.PACKAGE, sqlSessionFactoryRef = "apsystemSqlSessionFactory")// 扫描 Mapper 接口并容器管理
//public class ApSystemDataSourceConfig {
//
//	@Value("${mapper.location}")
//	private String mapperLocation;
//	
//    //精确到apsystem目录，以便跟其他数据源隔离
//    static final String PACKAGE = "classpath*:/com.sinosoft.ap.**.domain";
//    static final String MAPPER_LOCATION = "classpath*:/com/sinosoft/ap/**/mapper/*.xml";
//    @Bean(name = "apsystemDataSource")
//    @Primary
//    @ConfigurationProperties(prefix = "spring.datasource")
//    public DataSource apSystemDataSource() {
//        DruidDataSource dataSource = new DruidDataSource();
//        return dataSource;
//    }
//
//    @Bean(name = "apsystemTransactionManager")
//    @Primary
//    public DataSourceTransactionManager apsystemTransactionManager() {
//        return new DataSourceTransactionManager(apSystemDataSource());
//    }
//
//    @Bean(name = "apsystemSqlSessionFactory")
//    @Primary
//    public SqlSessionFactory apSystemSqlSessionFactory(@Qualifier("apsystemDataSource") DataSource masterDataSource)
//            throws Exception {
//        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
//        sessionFactory.setDataSource(masterDataSource);
//        Resource[] resource1 = new PathMatchingResourcePatternResolver().getResources(ApSystemDataSourceConfig.MAPPER_LOCATION);
//        if(!StringUtil.checkNull(mapperLocation)) {
//        	Resource[] resource2 = new PathMatchingResourcePatternResolver().getResources(mapperLocation);
//        	Resource[] resources = (Resource[]) ArrayUtils.addAll(resource1, resource2);
//        	sessionFactory.setMapperLocations(resources);
//        }else{
//        	sessionFactory.setMapperLocations(resource1);
//        }
//        return sessionFactory.getObject();
//    }
//
//
//}
