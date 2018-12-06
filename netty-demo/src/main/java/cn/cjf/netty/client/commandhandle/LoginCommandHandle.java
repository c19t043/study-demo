package cn.cjf.chat.client.commandhandle;

import cn.cjf.chat.domain.LoginPacket;
import cn.cjf.chat.domain.MessagePacket;
import cn.cjf.chat.domain.Packet;
import cn.cjf.chat.utils.ChannelBindKeyUtil;
import cn.cjf.chat.utils.ConsoleUtil;
import cn.cjf.chat.utils.MessageUtil;
import io.netty.channel.Channel;

import java.util.UUID;

/**
 * @author CJF
 */
public class LoginCommandHandle implements BaseHandle {

    /**
     * 登录命令处理：
     * 1、所有输入命令，只有命令，没有消息
     * 2、构造登录认证数据包
     * 3、发送消息，服务端绑定通道登录认证执行命令组
     * 4、同步等待
     * 5、服务端返回消息后，提示输入登录用户名
     * 6、控制台录入用户名，发送消息到服务端，服务端收到消息后，解除绑定,控制台可直接录入其他命令组
     *
     * @param channel 客户端通道
     * @param o       要发送的消息：为null
     */
    @Override
    public void execute(Channel channel, String o) {
        String[] messages = ConsoleUtil.consoleInAndParse("请输入用户名：--》");

        String userId = UUID.randomUUID().toString();
        String userName = messages[1];

        //通道绑定userId
        ChannelBindKeyUtil.bindLoginUserId(channel, userId);

        Packet packet = new LoginPacket(userId, userName);
        MessageUtil.clientSendMsg2ServerWithSync(channel, packet);
    }
}
