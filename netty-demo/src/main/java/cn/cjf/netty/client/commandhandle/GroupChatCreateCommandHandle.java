package cn.cjf.chat.client.commandhandle;

import cn.cjf.chat.config.CommandEnum;
import cn.cjf.chat.domain.MessagePacket;
import cn.cjf.chat.domain.Packet;
import cn.cjf.chat.utils.ConsoleUtil;
import cn.cjf.chat.utils.MessageUtil;
import io.netty.channel.Channel;

/**
 * @author CJF
 */
public class GroupChatCreateCommandHandle implements BaseHandle {

    /**
     * 单聊开始命令：
     * 1、客户端接受到单聊命令，进入单聊处理逻辑
     * 2、客服端提示：请输入要聊天的内容；格式：要聊天的客户【userId:[用户id]:消息】
     * 4、客户端接收到内容，向服务端发送消息
     * 5、服务端查看何种命令，将受到的消息转发给指定的客户端
     * 6、指定客户端收到消息后，打印消息到控制台
     * 7、客户端控制台录入回复消息：格式：【!@userId:[要回复消息的userId]:消息】
     * 8、重复循环【6、7】
     *
     * @param channel 客户端通道
     * @param o       要发送的消息：为null
     */
    @Override
    public void execute(Channel channel, String o) {
        //1、客户端接受到单聊命令，向服务端发送单聊命令
//        Packet packet = new MessagePacket(o);
//        MessageUtil.clientSendMsg2ServerWithSync(channel, packet);

        //绑定命令
//        ChannelBindKeyUtil.bindKey(channel, Attributes.COMMAND, CommandEnum.SINGLE_CHAT_START.toString());

        //3、客户端收到响应答应消息，客服端提示：请输入要聊天的内容；格式：要聊天的客户【userId:消息】
        String hintInfo = "请输入要聊天的内容；格式：要聊天的用【userId:[用户id]:消息】，回复消息格式【!@userId:[用户id]:消息】";
        String[] messages = ConsoleUtil.consoleInAndParse(hintInfo);

        while (!messages[0].equals(CommandEnum.SINGLE_CHAT_QUIT.toString())) {
            //4、客户端接收到内容，向服务端发送消息
            Packet packet = new MessagePacket(messages[1]);
            MessageUtil.clientSendMsg2ServerWithSync(channel, packet);

            messages = ConsoleUtil.consoleInAndParse(hintInfo);
        }

        //退出单聊模式
        System.out.println("已退出单聊模式");
    }
}
