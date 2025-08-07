package org.chatroom.handlers;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.group.ChannelGroup;
import org.chatroom.server.MessageAttributes;

public class ClientInitHandler extends ChannelInboundHandlerAdapter {
    private final ChannelGroup channelGroup;

    public ClientInitHandler(ChannelGroup channelGroup) {
        this.channelGroup = channelGroup;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        ctx.writeAndFlush("Welcome to MultiChat!\nSet your nickname:");
        channelGroup.add(ctx.channel());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ctx.channel().attr(MessageAttributes.USER).setIfAbsent((String) msg);

        var message = msg + " " + "has joined the chat";
        channelGroup.writeAndFlush(message);

        ctx.pipeline().remove(this);
    }
}
