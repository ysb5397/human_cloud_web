package com.tenco.web._core.config;

import com.tenco.web._core.errors.interceptor.LoginInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RequiredArgsConstructor
@Configuration // IoC 처리 - 싱글톤 패턴 관리
public class WebMvcConfig implements WebMvcConfigurer {

    private final LoginInterceptor loginInterceptor;


    //    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(loginInterceptor)
//                .addPathPatterns("/board/**", "/user/**")
//                .excludePathPatterns("/?","/user/login-form");

        // \\d+ -> 정규 표현식으로, 1개 이상의 숫자를 의미

}
