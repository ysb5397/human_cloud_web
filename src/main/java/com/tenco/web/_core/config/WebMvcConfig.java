package com.tenco.web._core.config;

import com.tenco.web._core.errors.interceptor.LoginInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RequiredArgsConstructor
@Configuration // IoC 처리 - 싱글톤 패턴 관리
public class WebMvcConfig implements WebMvcConfigurer {

    private final LoginInterceptor loginInterceptor;


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/board/**", "/user/**", "/community/**")
                .excludePathPatterns("/?",
                        "/user/login-form",
                        "/user/signup-form",
                        "/user/naver-login",
                        "/company/login-form",
                        "/company/signup-form"
                );
    }
}
