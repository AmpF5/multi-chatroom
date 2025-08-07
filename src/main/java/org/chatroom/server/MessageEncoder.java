package org.chatroom.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

public class MessageEncoder extends MessageToMessageEncoder<String> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, String s, List<Object> list) {
        System.out.println("MessageEncoder");
        list.add(s);
    }
}
