package org.chatroom;

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
        var group = new MultiThreadIoEventLoopGroup(NioIoHandler.newFactory());
        try {
            var server = new ServerBootstrap()
                    .group(group)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<>(){
                        @Override
                        protected void initChannel(Channel ch) {
                            ch.pipeline().addLast(new ServerHandler(){});
                        }
                    });

            var channel = server.bind(host, port).sync().channel();


            System.out.println("Server started on port:"+port);

            channel.closeFuture().sync();
        } catch (InterruptedException e) {
            System.out.printf("Cannot start server on %s:%d\n", host, port);
        } finally  {
            group.shutdownGracefully();

        }
    }

}
