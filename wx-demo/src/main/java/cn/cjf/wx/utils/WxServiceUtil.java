package cn.cjf.wx.utils;

import cn.cjf.wx.config.WxConfig;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;

public final class WxServiceUtil {
    /**
     * 微信工具类
     */
    private static WxMpService wxService = new WxMpServiceImpl();

    static {
        //配置微信参数  获取wxService
        //微信配置参数
        WxMpInMemoryConfigStorage wxConfigProvider = new WxMpInMemoryConfigStorage();
        wxConfigProvider.setAppId(WxConfig.APP_ID);
        wxConfigProvider.setSecret(WxConfig.APP_SECRET);
        //注入token值
        wxConfigProvider.setToken(WxConfig.TOKEN);
        wxService.setWxMpConfigStorage(wxConfigProvider);
    }

    public static WxMpService getWxService(){
        return wxService;
    }
}
