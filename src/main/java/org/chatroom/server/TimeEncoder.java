package org.chatroom.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.time.Instant;
import java.util.List;

public class TimeEncoder extends MessageToMessageEncoder<String> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, String s, List<Object> list) throws Exception {
        System.out.println("TimeEncoder");
        var now = Instant.now();
        var msg = String.format("%s\n%s", now, s);
        list.add(msg);
    }
}
