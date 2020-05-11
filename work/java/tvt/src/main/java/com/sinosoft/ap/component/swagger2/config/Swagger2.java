//package com.sinosoft.ap.component.swagger2.config;
//
//import com.sinosoft.ap.component.swagger2.domain.SwaggerEntity;
//import com.sinosoft.ap.util.string.StringUtil;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import springfox.documentation.builders.ApiInfoBuilder;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.service.ApiInfo;
//import springfox.documentation.service.Contact;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;
//
///**
// * Swagger API生成工具
// *
// * @author HongyanShan
// * @date 2017/10/24 13:49
// */
//@Configuration
//@EnableSwagger2
//public class ApSystemSwagger2 {
//    SwaggerEntity sw = new SwaggerEntity();
//
//    @Bean
//    public Docket createRestApi() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .apiInfo(apiInfo())
//                .select()
//                .apis(RequestHandlerSelectors.basePackage(StringUtil.returnString(sw.getBasePackage())))
//                .paths(PathSelectors.any())
//                .build();
//    }
//
//    private ApiInfo apiInfo() {
//        return new ApiInfoBuilder()
//                .title(StringUtil.returnString(sw.getTitle()))
//                .description(StringUtil.returnString(sw.getDesctription()))
//                .contact(new Contact(StringUtil.returnString(sw.getAuthor()), StringUtil.returnString(sw.getUrl()), StringUtil.returnString(sw.getEmail())))
//                .version("0.0.1-Beta")
//                .build();
//    }
//}
