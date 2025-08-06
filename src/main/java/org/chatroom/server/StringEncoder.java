package org.chatroom.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.util.CharsetUtil;

import java.util.List;

public class StringEncoder extends MessageToMessageEncoder<String>{
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, String msg, List<Object> out) throws Exception {
        out.add(channelHandlerContext.alloc().buffer().writeBytes(msg.getBytes(CharsetUtil.UTF_8)));
    }
}
