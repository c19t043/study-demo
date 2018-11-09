package demo;

/**
 * 常量类
 *
 * @author rory.wu
 */
public class Constants {
    // 第三方用户唯一凭证
    public static String appid = "";
    // 第三方用户唯一凭证密钥
    public static String appsecret = "";
    //商户ID
    public static String mch_id = "";
    //获取openId
    public static String oauth2_url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";

    public static String ticket_url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";
    public static String key;
    public static String token_url;
    public static String notify_url;
}