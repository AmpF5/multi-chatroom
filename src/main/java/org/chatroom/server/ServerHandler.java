package org.chatroom.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.group.ChannelGroup;

public class ServerHandler extends ChannelInboundHandlerAdapter {
    private final ChannelGroup channelGroup;

    public ServerHandler(ChannelGroup channelGroup) {
        this.channelGroup = channelGroup;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        var username = ctx.channel().attr(MessageAttributes.USER).get();
        System.out.println("username" + username);
        channelGroup.writeAndFlush(msg);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        var username = ctx.channel().attr(MessageAttributes.USER).get();
        var message = username + " has lef the chat";
        channelGroup.writeAndFlush(message);

        channelGroup.remove(ctx.channel());
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        System.out.println("exceptionCaught from ServerHandler" +  cause.getMessage());
        channelGroup.remove(ctx.channel());
        ctx.close();
    }

}
