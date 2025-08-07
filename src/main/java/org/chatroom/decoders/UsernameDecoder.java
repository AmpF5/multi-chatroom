package org.chatroom.decoders;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import org.chatroom.server.MessageAttributes;

import java.util.List;
import java.util.Objects;

public class UsernameDecoder extends MessageToMessageDecoder<String> {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, String s, List<Object> list) {
        var username = Objects.requireNonNullElse(channelHandlerContext.channel().attr(MessageAttributes.USER).get(), "");
        if (username.isEmpty()) {
            list.add(s);
            return;
        }
        var message = username + ": " + s;
        list.add(message);
    }
}
