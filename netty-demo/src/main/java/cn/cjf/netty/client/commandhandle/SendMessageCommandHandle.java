package cn.cjf.netty.client.commandhandle;

import cn.cjf.netty.domain.MessagePacket;
import cn.cjf.netty.domain.Packet;
import cn.cjf.netty.utils.MessageUtil;
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
