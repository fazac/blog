package com.yyft.blog.config;

import com.yyft.blog.tools.intercept.JWTIntercept;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class TokenConfig implements WebMvcConfigurer {
    @Autowired
    private JWTIntercept jwtIntercept;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtIntercept)
                .addPathPatterns("/**")
                .excludePathPatterns("/login/**");
    }
}
