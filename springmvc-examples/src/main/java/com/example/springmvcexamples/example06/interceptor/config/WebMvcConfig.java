package com.example.springmvcexamples.example06.interceptor.config;

import com.example.springmvcexamples.example06.interceptor.interceptor.AdminInterceptor06;
import com.example.springmvcexamples.example06.interceptor.interceptor.LoginInterceptor06;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    private final LoginInterceptor06 loginInterceptor06;
    private final AdminInterceptor06 adminInterceptor06;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor06)
                .addPathPatterns("/api/example06/**")
                .excludePathPatterns("/api/example06/login");
        registry.addInterceptor(adminInterceptor06)
                .addPathPatterns("/api/example06/admin/**");
    }
}
