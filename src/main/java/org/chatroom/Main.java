package org.chatroom;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.MultiThreadIoEventLoopGroup;
import io.netty.channel.nio.NioIoHandler;
import io.netty.channel.socket.nio.NioServerSocketChannel;


public class Main {
    public static final String HOST = "127.0.0.1";
    public static final int PORT = 8007;

    public static void main(String[] args) {
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

            var channel = server.bind(HOST,PORT).sync();

            System.out.println("Server started on port:"+PORT);


        } catch (InterruptedException e) {
            System.out.printf("Cannot start server on %s:%d\n", HOST, PORT);
        } finally {
            group.shutdownGracefully();
        }


    }
}