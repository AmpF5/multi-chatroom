package org.chatroom.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.MultiThreadIoEventLoopGroup;
import io.netty.channel.nio.NioIoHandler;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class ChatServer {
    private final String host = "localhost";
    private final int port;

    public ChatServer(int port) {
        this.port = port;
    }

    public void run() throws Exception {
        var bossGroup = new MultiThreadIoEventLoopGroup(1, NioIoHandler.newFactory());
        var workersGroup = new MultiThreadIoEventLoopGroup(NioIoHandler.newFactory());
        try {
            var server = new ServerBootstrap()
                    .group(bossGroup, workersGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<>(){
                        @Override
                        protected void initChannel(Channel ch) {
                            ch.pipeline().addLast(new ServerHandler(){});
                        }
                    });

            var f=  server.bind(host, port).sync();
            System.out.println("Server started on port:"+port);

            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            System.out.printf("Cannot start server on %s:%d\n", host, port);
        } finally  {
            bossGroup.shutdownGracefully();
            workersGroup.shutdownGracefully();
        }
    }

}
