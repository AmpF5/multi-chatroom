package org.chatroom.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.MultiThreadIoEventLoopGroup;
import io.netty.channel.nio.NioIoHandler;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class ChatClient {
    private final String host = "localhost";
    private final int port;

    public ChatClient(int port) {
        this.port = port;
    }

    public void connect() throws Exception {
        var workersGroup = new MultiThreadIoEventLoopGroup(NioIoHandler.newFactory());

        try {
            var client = new Bootstrap()
                    .group(workersGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new ClientHandler(){});
                        }
                    });

            var f = client.connect(host, port).sync();
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            System.out.println("Cannot active the client");
        } finally {
            workersGroup.shutdownGracefully();
        }
    }
}
