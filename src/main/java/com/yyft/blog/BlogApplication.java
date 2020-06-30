package com.yyft.blog;

import com.yyft.blog.tools.filter.NoCacheFilter;
import com.yyft.blog.tools.filter.RedirectFilter;
import com.yyft.blog.tools.filter.SelfDefineInvalidCharacterFilter;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@MapperScan("com.yyft.blog.mapper")
public class BlogApplication {

    @Bean
    public FilterRegistrationBean<RedirectFilter> redirectFilter() {
        FilterRegistrationBean<RedirectFilter> registrationBean = new FilterRegistrationBean<>(new RedirectFilter());
        registrationBean.addUrlPatterns("/*");
        registrationBean.setName("redirectFilter");
        registrationBean.setOrder(1);
        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean<SelfDefineInvalidCharacterFilter> selfDefineInvalidCharacterFilter() {
        FilterRegistrationBean<SelfDefineInvalidCharacterFilter> registrationBean = new FilterRegistrationBean<>(new SelfDefineInvalidCharacterFilter());
        registrationBean.addUrlPatterns("/*");
        registrationBean.setName("selfDefineInvalidCharacterFilter");
        registrationBean.setOrder(2);
        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean<NoCacheFilter> noCacheFilter() {
        FilterRegistrationBean<NoCacheFilter> registrationBean = new FilterRegistrationBean<>(new NoCacheFilter());
        registrationBean.addUrlPatterns("/*");
        registrationBean.setName("noCacheFilter");
        registrationBean.setOrder(3);
        return registrationBean;
    }


    public static void main(String[] args) {
        SpringApplication.run(BlogApplication.class, args);
    }

}
