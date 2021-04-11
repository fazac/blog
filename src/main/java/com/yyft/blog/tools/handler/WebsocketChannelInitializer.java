package com.yyft.blog.tools.handler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author fzc
 * @version 1.0
 * @description
 * @date 2021/4/11 16:25
 */
@Slf4j
public class WebsocketChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        socketChannel.pipeline().addLast("logging", new LoggingHandler("DEBUG"));//日志级别
        socketChannel.pipeline().addLast("http-codec", new HttpServerCodec());//解码器
        socketChannel.pipeline().addLast("aggregator", new HttpObjectAggregator(65536));//聚合器,websocket使用
        socketChannel.pipeline().addLast("http-chunked", new ChunkedWriteHandler());//大数据分区传输
        socketChannel.pipeline().addLast("handler", new WebsocketHandler());//自定义handler
    }
}
