//package com.sinosoft.ap.component.swagger2.config;
//
//import com.sinosoft.ap.component.swagger2.domain.SwaggerEntity;
//import com.sinosoft.ap.util.string.StringUtil;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
//import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
//
//@Configuration
//public class SwaggerConfig extends WebMvcConfigurationSupport {
//
//    SwaggerEntity sw = new SwaggerEntity();
//
//    @Configuration
//    public class ServletContextConfig extends WebMvcConfigurationSupport {
//
//        /**
//         * 发现如果继承了WebMvcConfigurationSupport，则在yml中配置的相关内容会失效。
//         * 需要重新指定静态资源
//         *
//         * @param registry
//         */
//        @Override
//        public void addResourceHandlers(ResourceHandlerRegistry registry) {
//            String state = StringUtil.returnString(sw.getState());
//            if (state.equals("1")) {
//                registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
//                registry.addResourceHandler("swagger-ui.html")
//                        .addResourceLocations("classpath:/META-INF/resources/");
//                registry.addResourceHandler("/webjars/**")
//                        .addResourceLocations("classpath:/META-INF/resources/webjars/");
//                super.addResourceHandlers(registry);
//            }
//        }
//
//        /**
//         * 配置servlet处理
//         */
//        @Override
//        public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
//            configurer.enable();
//        }
//
//    }
//}
