package com.yyft.blog.tools.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelId;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author fzc
 * @version 1.0
 * @description
 * @date 2021/4/11 16:33
 */
@Slf4j
public class ChannelSupervise {
    private static ChannelGroup globeGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    private static ConcurrentHashMap<String, ChannelId> channelMap = new ConcurrentHashMap<>();

    public static void addChannel(Channel channel) {
        globeGroup.add(channel);
        channelMap.put(channel.id().asShortText(), channel.id());
    }

    public static void removeChannel(Channel channel) {
        globeGroup.remove(channel);
        channelMap.remove(channel.id().asShortText());
    }

    public static Channel findChannel(String id) {
        return globeGroup.find(channelMap.get(id));
    }

    public static void send2All(TextWebSocketFrame tws) {
        globeGroup.writeAndFlush(tws);
    }

    public static void sendCId(String channleId, TextWebSocketFrame tws) {
        findChannel(channleId).writeAndFlush(tws);
    }
}
