package cn.cjf.netty.server.handler;

import cn.cjf.netty.utils.ChannelBindKeyUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author CJF
 */
@ChannelHandler.Sharable
public class AuthHandler extends ChannelInboundHandlerAdapter {
    public static final AuthHandler INSTANCE = new AuthHandler();
    private AuthHandler() {

    }
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (ChannelBindKeyUtil.hasLogin(ctx.channel())) {
            //当前已登录,移除登录验证
            ctx.pipeline().remove(this);
            super.channelRead(ctx, msg);
        } else {
            //提示未登录
            ctx.channel().close();
        }
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) {
        if (ChannelBindKeyUtil.hasLogin(ctx.channel())) {
            System.out.println("当前连接登录验证完毕，无需再次验证, AuthHandler 被移除");
        } else {
            System.out.println("无登录验证，强制关闭连接!");
        }
    }
}