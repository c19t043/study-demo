package cn.cjf.wx.auth;

import cn.cjf.wx.util.HttpClientUtil;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author chenjunfan
 * @date 2019/5/8
 */
@RequestMapping("/auth")
public class AuthController {
    private static Logger logger = LoggerFactory.getLogger(AuthController.class);
    String appid = "wx1604db3cf112bc83";
    String appSecret = "213858a8266e6859a0f5673831f6d9de";

    /**
     * 请求授权
     */
    @RequestMapping("/askAuth")
    public void auth(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String redirecturl = "http://zeihi9.natappfree.cc/appserverweb/api/user/info";
        String scope = "snsapi_userinfo";
        String url = String.format("https://open.weixin.qq.com/connect/oauth2/authorize?" +
                        "appid=%s&redirect_uri=%s&response_type=code&scope=%s&state=%s#wechat_redirect",
                appid, redirecturl, scope, "auth");
        response.sendRedirect(url);
    }

    /**
     * 认证，获取用户access_token，openid
     */
    @RequestMapping(value = "/accessToken", method = RequestMethod.GET)
    public void getUserToken(String code, String state, HttpServletResponse response) throws IOException {
        if (StringUtils.isEmpty(code)) {
            return;
        }
        String redirectUrl;
        try {
            String authOpenId = getOpenIdByCode(code, appid, appSecret);
            redirectUrl = afterGetAuthOpenId(authOpenId);
        } catch (Exception e) {
            logger.error("获取授权信息失败，原因：", e);
            redirectUrl = "/auth/askAuth";
        }
        response.sendRedirect(redirectUrl);
    }


    private String getOpenIdByCode(String code, String appid, String secret) {
        try {
            //通过code换取网页授权access_token
            JSONObject json = getUserInfoAccessToken(code, appid, secret);
            //检验token是否有效
            boolean flag = checkToken(json.getString("access_token"), json.getString("openid"));
            if (!flag) {
                //如果无效刷新token
                json = refreshToken(json.getString("refresh_token"), appid);
            }
            return json.getString("openid");
        } catch (Exception e) {
            logger.error("微信授权失败", e);
        }
        return null;
    }

    /**
     * 根据code获取access_token和openid
     *
     * @param code
     * @return
     * @author: lcz
     * @time 2017年10月20日
     */
    public static JSONObject getUserInfoAccessToken(String code, String appid, String secret) {
        JSONObject json = null;
        try {
            String url = "https://api.weixin.qq.com/sns/oauth2/access_token";
            Map<String, Object> params = new HashMap<>();
            params.put("appid", appid);
            params.put("secret", secret);
            params.put("code", code);
            params.put("grant_type", "authorization_code");
            logger.info("根据code获取token 请求地址==" + url);
            logger.info("根据code获取token 请求参数==" + params.toString());
            String jsonStr = HttpClientUtil.get(url, params);
            logger.info("根据code获取token 返回结果" + jsonStr);
            json = JSONObject.parseObject(jsonStr);
        } catch (Exception ex) {
            logger.error("fail to request wechat access token. [error={}]", ex);
        }
        return json;
    }

    public static boolean checkToken(String token, String openId) {
        JSONObject json = null;
        boolean flag = false;
        try {
            String url = "https://api.weixin.qq.com/sns/auth";
            Map<String, Object> params = new HashMap<>();
            params.put("access_token", token);
            params.put("openid", openId);
            String jsonStr = HttpClientUtil.get(url, params);
            json = JSONObject.parseObject(jsonStr);
            if (json.getString("errcode").equals("0")) {
                flag = true;
            }

        } catch (Exception ex) {
            logger.error("fail to request wechat access token. [error={}]", ex);
        }
        return flag;
    }

    /**
     * 检验token是否有效
     *
     * @return
     * @author: lcz
     * @time 2017年10月28日
     */
    public static JSONObject refreshToken(String refreshToken, String appid) {
        JSONObject json = null;
        try {
            String url = "https://api.weixin.qq.com/sns/oauth2/refresh_token";
            Map<String, Object> params = new HashMap<>();
            params.put("appid", appid);
            params.put("grant_type", "refresh_token");
            params.put("refresh_token", refreshToken);
            String jsonStr = HttpClientUtil.get(url, params);
            json = JSONObject.parseObject(jsonStr);
        } catch (Exception ex) {
            logger.error("fail to request wechat access token. [error={}]", ex);
        }
        return json;
    }

    private String afterGetAuthOpenId(String authOpenId) {
        setAuthOpenId(authOpenId);
        return "/";
    }

    public String getAuthOpenId() {
        return (String) getCurrentRequest().getSession().getAttribute("authOpenId");
    }

    public void setAuthOpenId(String authOpenId) {
        getCurrentRequest().getSession().setAttribute("authOpenId", authOpenId);
    }

    /**
     * @param
     * @return
     * @throws IllegalStateException 当前线程不是web请求抛出此异常.
     * @Description 获取  HttpServletRequest 对象
     * @Author wangwei
     * @Date 2018/10/11 0011 下午 2:49
     */
    private static HttpServletRequest getCurrentRequest() throws IllegalStateException {
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attrs == null) {
            throw new IllegalStateException("当前线程中不存在 Request 上下文");
        }
        return attrs.getRequest();
    }
}
