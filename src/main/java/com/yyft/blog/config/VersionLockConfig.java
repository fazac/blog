package com.yyft.blog.config;

import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author fzc
 * @version 1.0
 * @description
 * @date 2021/4/2 14:30
 */
@Configuration
public class VersionLockConfig {
    @Bean
    public OptimisticLockerInterceptor optimisticLockerInterceptor() {
        return new OptimisticLockerInterceptor();
    }

}

