package org.chatroom.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TimeEncoder extends MessageToMessageEncoder<String> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, String s, List<Object> list) {
        var now = LocalTime.ofInstant(Instant.now(), ZoneId.systemDefault());
        var formatted = now.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        var msg = String.format("%s\n%s", formatted, s);
        list.add(msg);
    }
}
