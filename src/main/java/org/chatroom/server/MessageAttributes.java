package org.chatroom.server;

import io.netty.util.AttributeKey;

public abstract class MessageAttributes {
    public static final AttributeKey<String> USER = AttributeKey.valueOf("user");
}
