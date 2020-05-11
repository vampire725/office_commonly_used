package com.sinosoft;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
//import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
//import org.springframework.cloud.client.loadbalancer.LoadBalanced;
//import org.springframework.context.annotation.Bean;
//import org.springframework.web.client.RestTemplate;

/**
 * ap入口
 *
 */
//@EnableDiscoveryClient
@SpringBootApplication // same as @Configuration @EnableAutoConfiguration @ComponentScan
@MapperScan("com.sinosoft.**.domain")
@EnableTransactionManagement(proxyTargetClass = true)
public class Application {
	
	private static Logger logger = LogManager.getLogger(Application.class);
	
//    @Bean
//    @LoadBalanced
//    RestTemplate restTemplate() {
//        return new RestTemplate();
//    }
//	
	
	/**
	 * 说明：springboot程序启动:初始化Spring环境及组件,启动嵌入式的Tomcat
	 * @param args
	 */
	public static void main(String[] args) {
		logger.info("项目启动！");
	    SpringApplication.run(Application.class, args);
	}
}
