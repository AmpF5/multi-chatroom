package org.chatroom.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.util.CharsetUtil;


public class StringEncoder extends MessageToByteEncoder<String> {
    protected void encode(ChannelHandlerContext channelHandlerContext, String s, ByteBuf byteBuf) {
        byteBuf.writeBytes(s.getBytes(CharsetUtil.UTF_8));
    }
}
