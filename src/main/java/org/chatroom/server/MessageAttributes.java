package org.chatroom.server;

import io.netty.util.AttributeKey;
import io.netty.util.AttributeMap;

public abstract class MessageAttributes {
    public static final AttributeKey<String> USER = AttributeKey.valueOf("user");
}
