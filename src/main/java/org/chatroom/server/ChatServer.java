package org.chatroom.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.MultiThreadIoEventLoopGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioIoHandler;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.GlobalEventExecutor;

public class ChatServer {
    private final String host = "localhost";
    private final int port;

    public ChatServer(int port) {
        this.port = port;
    }

    public void run() {
        var chatGroup = new MultiThreadIoEventLoopGroup(NioIoHandler.newFactory());
        var channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
        try {
            var server = new ServerBootstrap()
                    .group(chatGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<>(){
                        @Override
                        protected void initChannel(Channel ch) {
                            ch.pipeline()
                                    .addLast(new StringDecoder())
                                    .addLast(new StringEncoder())
                                    .addLast(new ClientInitHandler(channelGroup))
                                    .addLast(new ServerHandler(channelGroup));
                        }
                    });

            var f=  server.bind(host, port).sync();
            System.out.println("Server started on port:"+port);

            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            System.out.printf("Cannot start server on %s:%d\n", host, port);
        } finally  {
            chatGroup.shutdownGracefully();
        }
    }

}
