package cn.cjf.chat.client.commandhandle;

import io.netty.channel.Channel;
/**
 * @author CJF
 */
public interface BaseHandle {
    void execute(Channel channel, String o);
}
