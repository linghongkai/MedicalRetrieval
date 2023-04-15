package com.medicalretrieval.config;

import com.medicalretrieval.Interceptor.AdminInterceptor;
import com.medicalretrieval.Interceptor.MyInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //不拦截的页面： 登录  搜索
//        registry.addInterceptor(new MyInterceptor()).excludePathPatterns("/login","/search");

        //管理员的拦截器。用户不能进入管理员的领域
//        registry.addInterceptor(new AdminInterceptor());
    }
}
