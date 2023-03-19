package com.tplink.blog.config;

import com.tplink.blog.handler.LoginInterceptor;
import com.tplink.blog.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author xyd
 * @create 2022-10-10 10:17
 */
@Configuration
public class WebMVCConfig implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptor loginInterceptor;

    @Override
    public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/**").allowedOrigins("https://degeblog.cn","https://www.degeblog.cn",
                "http://localhost:8080","https://appstream", "https://app:8888");

    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/test")
                .addPathPatterns("/comments/create/change")
                .addPathPatterns("/articles/publish");
    }

}
