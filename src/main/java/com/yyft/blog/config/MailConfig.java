package com.yyft.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

/**
 * @author fzc
 * @version 1.0
 * @description
 * @date 2021/4/5 13:39
 */
@Configuration
public class MailConfig {

    @Bean
    public JavaMailSender javaMailService() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost("smtp.qq.com");
        javaMailSender.setPort(587);
        javaMailSender.setProtocol("smtp");
        javaMailSender.setDefaultEncoding("utf-8");
        javaMailSender.setUsername("1650031931@qq.com");
        javaMailSender.setPassword("mbsxinxdsbfmdccb");
        javaMailSender.setJavaMailProperties(getMailProperties());
        return javaMailSender;
    }

    private Properties getMailProperties() {
        Properties properties = new Properties();
//        properties.setProperty("mail.smtp.auth", "false");
        properties.setProperty("mail.stmp.ssl.enable", "true");
//        properties.setProperty("mail.protocol", "smtp");
//        properties.setProperty("mail.debug", "false");
//        properties.setProperty("mail.host", "smtp.qq.com");
//        properties.setProperty("mail.port", "587");
//        properties.setProperty("mail.default-encoding", "utf-8");
//        properties.setProperty("mail.username", "1650031931@qq.com");
//        properties.setProperty("mail.password", "mbsxinxdsbfmdccb");
        return properties;
    }
}
