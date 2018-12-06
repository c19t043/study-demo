package cn.cjf.chat.client.commandhandle;

import cn.cjf.chat.domain.MessagePacket;
import cn.cjf.chat.domain.Packet;
import cn.cjf.chat.utils.MessageUtil;
import io.netty.channel.Channel;
/**
 * @author CJF
 */
public class SendMessageCommandHandle implements BaseHandle {
    
    @Override
    public void execute(Channel channel, String o) {
        Packet packet = new MessagePacket(o);
        MessageUtil.clientSendMsg2ServerWithSync(channel, packet);
    }
}
