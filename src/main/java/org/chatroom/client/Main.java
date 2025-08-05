package org.chatroom.client;

public class Main {
    public static final int PORT = 8007;
    public static void main(String[] args) throws Exception {
        new ChatClient(PORT).connect();
    }
}
