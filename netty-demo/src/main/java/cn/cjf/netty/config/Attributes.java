package cn.cjf.chat.config;

import io.netty.util.AttributeKey;

/**
 * @author CJF
 */
public interface Attributes {
    AttributeKey<Boolean> LOGIN = AttributeKey.newInstance("login");
    AttributeKey<String> COMMAND = AttributeKey.newInstance("command");
    AttributeKey<String> USER_ID = AttributeKey.newInstance("userId");
}
