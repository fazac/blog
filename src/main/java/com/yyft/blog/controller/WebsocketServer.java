package com.yyft.blog.controller;

import com.yyft.blog.tools.handler.WebsocketChannelInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * @author fzc
 * @version 1.0
 * @description
 * @date 2021/4/11 16:15
 */
@Slf4j
public class WebsocketServer {
    public void init() {
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(boss, worker);
            bootstrap.channel(NioServerSocketChannel.class);
            bootstrap.childHandler(new WebsocketChannelInitializer());
            Channel channel = bootstrap.bind(8081).sync().channel();
            channel.closeFuture().sync();
            log.info("websocket服务器启动");
        } catch (InterruptedException e) {
            log.info("websocket初始化出错", e);
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }
}
