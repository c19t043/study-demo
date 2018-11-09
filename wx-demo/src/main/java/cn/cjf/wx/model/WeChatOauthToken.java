package cn.cjf.wx.model;

import lombok.Data;
import lombok.ToString;

/**
 * <p>Created on 2017/3/14.</p>
 *
 * @author StormMa
 *
 * @Description: 网页授权代表对象
 */
@Data
@ToString
public class WeChatOauthToken {

    /**
     * 调用接口的凭证
     */
    private String access_token;

    /**
     * 过期日期
     */
    private Integer expires_in;

    /**
     * 刷新token
     */
    private String refresh_token;

    /**
     * 用户的唯一openId
     */
    private String openId;

    /**
     * scope
     */
    private String scode;
}
