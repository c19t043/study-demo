package cn.cjf.wx.config;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created on 2017/3/13.
 *
 * @author StormMma
 *
 * @Description: 微信相关常量
 */
@Component
@ConfigurationProperties(locations = {"classpath:config/wechat.properties"}, prefix = "wechat")
@Data
@ToString
public class WeChatConfigBean {

    /**
     * token
     */
    private String token;

    /**
     * app id
     */
    private String appId;

    /**
     * app secret
     */
    private String appSecret;

    /**
     * call back url
     */
    private String callBackUrl;

    /**
     * 静默授权回调地址
     */
    private String callBackSlientUrl;

    /**
     * 商户id
     */
    private String MCHID;

    /**
     * 异步回调地址
     */
    private String NOTIFYURL;

    /**
     * 微信同意下单地址
     */
    private String wxorder;

    /**
     * key
     */
    private String KEY;
}
