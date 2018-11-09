package cn.cjf.wx.utils;

import cn.cjf.common.util.HttpClientUtil;
import cn.cjf.common.util.MD5Util;
import cn.cjf.wx.config.WeChatConfigBean;
import cn.cjf.wx.model.WeChatOauthToken;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;


/**
 * <p>Created on 2017/3/14.</p>
 *
 * @author StormMa
 *
 * @Description: 请求相关的工具类，与微信服务器的交互。
 */
@Component
public class WxApiUtil {

    @Autowired
    private WeChatConfigBean weChatConfigBean;

    private static final Logger logger = LoggerFactory.getLogger(WxApiUtil.class);

    /**
     * 获得网页凭证
     * @param code
     */
    public WeChatOauthToken getOAuthAccessToken(String code) {
        WeChatOauthToken weChatOauthToken = null;
        String getAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?" +
                "appid=" + weChatConfigBean.getAppId() +
                "&secret=" + weChatConfigBean.getAppSecret() +
                "&code=" + code +
                "&grant_type=authorization_code";

        JSONObject jsonObject = HttpClientUtil.doGetUrl(getAccessTokenUrl);
        if (jsonObject != null) {
            weChatOauthToken = new WeChatOauthToken();
            weChatOauthToken.setAccess_token(jsonObject.getString("access_token"));
            weChatOauthToken.setExpires_in(jsonObject.getInteger("expires_in"));
            weChatOauthToken.setOpenId(jsonObject.getString("openid"));
            weChatOauthToken.setRefresh_token(jsonObject.getString("refresh_token"));
            weChatOauthToken.setScode(jsonObject.getString("scope"));
        }
        return weChatOauthToken;
    }

    /**
     * 获取验证微信的url（拉取详细的用户信息）
     * @return
     */
    public String getWeChatAuthorizationUrl() {
         return "https://open.weixin.qq.com/connect/oauth2/authorize?" +
                "appid=" + weChatConfigBean.getAppId() +
                "&redirect_uri=" + URLEncoder.encode(weChatConfigBean.getCallBackUrl())+
                "&response_type=code" +
                "&scope=snsapi_userinfo" +
                "&state=STATE#wechat_redirect";
    }

    /**
     * <p>基础授权</p>
     * @return
     */
    public String getWeChatAuthorizationUrlSocpeIsBase() {
        return "https://open.weixin.qq.com/connect/oauth2/authorize?" +
                "appid=" + weChatConfigBean.getAppId() +
                "&redirect_uri=" + URLEncoder.encode(weChatConfigBean.getCallBackUrl())+
                "&response_type=code" +
                "&scope=snsapi_base" +
                "&state=STATE#wechat_redirect";
    }

    /**
     * 获取基础支持的access_token，给前端js使用
     * @return
     */
    public String getFrontAccessToken() {
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential" +
                "&appid=" + weChatConfigBean.getAppId() +
                "&secret=" + weChatConfigBean.getAppSecret();

        JSONObject jsonObject = HttpClientUtil.doGetUrl(url);
        return jsonObject.getString("access_token");
    }


    /**
     * 根据access_token获得jsapi_ticket
     * @param access_token
     * @return
     */
    public String getJsApiTicket(String access_token) {
        String url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=" + access_token +
                "&type=jsapi";

        JSONObject jsonObject = HttpClientUtil.doGetUrl(url);
        return jsonObject.getString("ticket");
    }


    /**
     * 根据jsapi_ticket
     * @param jsapi_ticket
     * @param url
     * @return
     */
    public Map<String, String> sign(String jsapi_ticket, String url) {
        Map<String, String> ret = new HashMap<>();
        String nonce_str = WXUtil.getNonceStr();
        String timestamp = WXUtil.getTimeStamp();
        String string1;
        String signature = "";

        //注意这里参数名必须全部小写，且必须有序
        string1 = "jsapi_ticket=" + jsapi_ticket +
                "&noncestr=" + nonce_str +
                "&timestamp=" + timestamp +
                "&url=" + url;
        System.out.println(string1);

        try
        {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(string1.getBytes("UTF-8"));
            signature = MD5Util.byteToHex(crypt.digest());
        } catch (Exception e) {
            logger.error("获取加密方式失败");
        }
        ret.put("url", url);
        ret.put("jsapi_ticket", jsapi_ticket);
        ret.put("nonceStr", nonce_str);
        ret.put("timestamp", timestamp);
        ret.put("signature", signature);

        return ret;
    }

    /**
     * <p>获得静默授权的 url</p>
     * @return
     */
    public String getSlientUrl() {
        String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + weChatConfigBean.getAppId() +
                "&redirect_uri=" + URLEncoder.encode(weChatConfigBean.getCallBackSlientUrl()) +
                "&response_type=code" +
                "&scope=snsapi_base" +
                "&state=STATE#wechat_redirect";

        return url;
    }

    /**
     * <p>静默授权获得openId</p>
     * @param code
     * @return
     */
    public String getOpenIdBySlientAuthy(String code) {

        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + weChatConfigBean.getAppId() +
                "&secret=" + weChatConfigBean.getAppSecret() +
                "&code=" + code +
                "&grant_type=authorization_code";

        JSONObject jsonObject = HttpClientUtil.doGetUrl(url);

        return jsonObject.getString("openid");
    }
}
