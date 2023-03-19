package com.tplink.blog.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

/**
 * @author xyd
 * @create 2022-12-03 13:43
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private Logger logger = LoggerFactory.getLogger(SwaggerConfig.class);

//    @Value("${spring.domain}")
//    private String url;

    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("通用API接口文档")
                .apiInfo(swaggerApiInfo())
                .pathMapping("/")
                .select()
                /*
                RequestHandlerSelectors:配置扫描接口的方式
                basePackage: 扫描包的路径
                any（）：扫描全部
                none（）：不扫描
                withMethodAnnotation：扫描类上的注解，参数是一个注解的反射类
                withClassAnnotation： 扫描方法上的注解
                */
                .apis(RequestHandlerSelectors.basePackage("com.tplink.blog.controller"))//指向自己的controller即可
                .build();
    }

    private ApiInfo swaggerApiInfo() {
//        logger.info("接口文档地址：" + url + "/doc.html");
        return new ApiInfoBuilder()
                .contact(new Contact("博客系统Api", "http://localhost:8888", "2362568506@qq.com"))
                .title("博客系统Api文档")
                .description("这是博客系统Api")
                .version("1.0.0")
                .build();
    }



}
