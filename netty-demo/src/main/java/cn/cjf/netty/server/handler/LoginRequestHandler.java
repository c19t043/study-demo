package cn.cjf.netty.server.handler;

import cn.cjf.netty.domain.LoginEchoPacket;
import cn.cjf.netty.domain.LoginPacket;
import cn.cjf.netty.server.session.SessionManager;
import cn.cjf.netty.server.session.SessionVo;
import cn.cjf.netty.utils.ChannelBindKeyUtil;
import cn.cjf.netty.utils.MessageUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author CJF
 */
@ChannelHandler.Sharable
public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginPacket> {
    public static final LoginRequestHandler INSTANCE = new LoginRequestHandler();
    private LoginRequestHandler() {

    }
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginPacket loginPacket) {
        Channel channel = ctx.channel();
        // 登录逻辑
        String userId = loginPacket.getUserId();
        String srcMessageId = loginPacket.getMessageId();


        SessionManager.getInstance().addSession(new SessionVo(userId, channel));
        SessionManager.getInstance().putUser(userId, loginPacket);
        ChannelBindKeyUtil.bindLoginUserId(channel, userId);

        LoginEchoPacket loginEchoPacket = new LoginEchoPacket("恭喜：[" + loginPacket.getUserName() + "]连接成功");
        MessageUtil.sendMessage(channel, loginEchoPacket, srcMessageId);
    }
}