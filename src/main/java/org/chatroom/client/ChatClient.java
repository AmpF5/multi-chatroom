package org.chatroom.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.MultiThreadIoEventLoopGroup;
import io.netty.channel.nio.NioIoHandler;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;
import org.chatroom.server.StringDecoder;
import org.chatroom.server.StringEncoder;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ChatClient {
    private final String host = "localhost";
    private final int port;
    private Channel channel;

    public ChatClient(int port) {
        this.port = port;
    }

    public void connect() {
        var workersGroup = new MultiThreadIoEventLoopGroup(NioIoHandler.newFactory());

        try {
            var client = new Bootstrap()
                    .group(workersGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) {
                            ch.pipeline()
                                    .addLast(new StringDecoder())
                                    .addLast(new StringEncoder())
                                    .addLast(new ClientHandler());
                        }
                    });

            var f = client.connect(host, port).sync();
            this.channel = f.channel();

            var stdinInput = new Thread(() -> {
                try {
                    readUserInputAndSend();
                } catch (Exception e) {
                    System.out.println("Error in reading thread: " + e.getMessage());
                }
            }, "stdin-input");
            stdinInput.start();

            channel.closeFuture().sync();
        } catch (InterruptedException e) {
            System.out.println("Cannot active the client");
        } finally {
            
            workersGroup.shutdownGracefully();
        }
    }

    private void send(String msg) {
        if(!(this.channel.isWritable()))
            return;

        this.channel.writeAndFlush(msg);
    }


    private void readUserInputAndSend() throws Exception {
        var reader = new BufferedReader(new InputStreamReader(System.in));

        while(true) {
            var msg = reader.readLine();
            send(msg);

            if (msg.equals("/quit")) { return; }
        }
    }
}
