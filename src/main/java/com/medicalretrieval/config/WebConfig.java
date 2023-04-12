package com.medicalretrieval.config;

import com.medicalretrieval.Interceptor.MyInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //不拦截的页面： 登录  搜索
        registry.addInterceptor(new MyInterceptor()).excludePathPatterns("/login","/search");
    }
}
