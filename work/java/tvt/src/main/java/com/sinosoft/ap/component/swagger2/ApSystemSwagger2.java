package com.sinosoft.ap.component.swagger2;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * API生成工具
 * @author 高秀和
 *
 */
@Configuration
@EnableSwagger2
public class ApSystemSwagger2 {

    @Value("${swaager.enable:false}")
    private boolean swaggerEnable;

	@Value("${swaager.base.path:system}")
	private String swaggerBasePath;

    @Value("${swaager.ap.symbol:false}")
    private boolean swaggerApSymbol;
	
    @Bean
    public Docket createRestApi() {
        String basePackage = "com.sinosoft.ap.swagger";
        if (swaggerEnable){
            if(!swaggerApSymbol && swaggerBasePath.equals("system")){
                basePackage = "com.sinosoft.ap.swagger";
            }else{
                basePackage = "com.sinosoft.ap."+swaggerBasePath;
            }
        }
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage(basePackage))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("API OF PO")
                .description("接口说明文档")
                .contact(new Contact("sinosoft_ap", null, ""))
                .version("1.0.0-Beta")
                .build();
    }
}
