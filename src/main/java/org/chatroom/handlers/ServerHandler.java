package org.chatroom.handlers;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.group.ChannelGroup;
import org.chatroom.server.MessageAttributes;

import java.util.Objects;

public class ServerHandler extends ChannelInboundHandlerAdapter {
    private final ChannelGroup channelGroup;

    public ServerHandler(ChannelGroup channelGroup) {
        this.channelGroup = channelGroup;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        channelGroup.writeAndFlush(msg);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        var username = Objects.requireNonNullElse(ctx.channel().attr(MessageAttributes.USER).get(), "");
        var message = username + "  has left the chat";
        channelGroup.writeAndFlush(message);

        channelGroup.remove(ctx.channel());
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        System.out.println("exceptionCaught from ServerHandler" + cause.getMessage());
        channelGroup.remove(ctx.channel());
        ctx.close();
    }

}
