package com.yyft.blog;

import com.yyft.blog.config.MailConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * @author fzc
 * @version 1.0
 * @description
 * @date 2021/4/5 13:44
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = BlogApplication.class)
@WebAppConfiguration
public class MailTest {

    @Autowired
    private JavaMailSender mailSender;


    @Test
    public void testSendMail() {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo("2495680464@qq.com");
        simpleMailMessage.setFrom("1650031931@qq.com");
        simpleMailMessage.setSubject("测试");
        simpleMailMessage.setText("测试发送邮件3");
        mailSender.send(simpleMailMessage);
    }

}
