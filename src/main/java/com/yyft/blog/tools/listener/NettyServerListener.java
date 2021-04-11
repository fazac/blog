package com.yyft.blog.tools.listener;

/**
 * @author fzc
 * @version 1.0
 * @description
 * @date 2021/4/11 22:48
 */

import com.yyft.blog.controller.WebsocketServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * @ description 解决项目打war包发布在tomcat，端口被占用问题
 * 解决思路：给NettyServer分配一个独立的线程用于加载
 */
@Component
@Slf4j
public class NettyServerListener implements ApplicationListener<ContextRefreshedEvent> {


    /**
     * 当一个applicationContext被初始化或被刷新时触发
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (event.getApplicationContext().getParent() == null) {
            log.info("NettyServer Start Success");
            new Thread(() -> new WebsocketServer().init()).start();
        }
    }
}