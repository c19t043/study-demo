package demo;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.apache.commons.lang.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;


public class Test {
    private static Logger log = LoggerFactory.getLogger(Test.class);
    HttpSession session;

    /**
     * 算出签名
     * @param jsapi_ticket
     * @param url 业务中调用微信js的地址
     * @return
     */
    public static Signature sign(String jsapi_ticket, String url) {
        String nonce_str = CommonUtil.create_nonce_str();
        String timestamp = CommonUtil.create_timestamp();
        String string1;
        String signature = "";

        //注意这里参数名必须全部小写，且必须有序
        string1 = "jsapi_ticket=" + jsapi_ticket +
                "&noncestr=" + nonce_str +
                "&timestamp=" + timestamp +
                "&url=" + url;
        try
        {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(string1.getBytes("UTF-8"));
            signature = CommonUtil.byteToStr(crypt.digest());
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }

        Signature result = new Signature();
        result.setUrl(url);
        result.setJsapi_ticket(jsapi_ticket);
        result.setNonceStr(nonce_str);
        result.setTimestamp(timestamp);
        result.setSignature(signature);

        return result;
    }

    public static Ticket getTicket() {
        String requestUrl = Constants.ticket_url.replace("ACCESS_TOKEN", TokenThread.accessToken.getAccessToken());
        // 发起GET请求获取凭证
        JSONObject jsonObject = CommonUtil.httpsRequestToJsonObject(requestUrl, "GET", null);
        Ticket ticket = null;
        String jsapi_ticket = "";
        int expires_in = 0;
        if (null != jsonObject) {
            try {
                jsapi_ticket = jsonObject.getString("ticket");
                expires_in = jsonObject.getInt("expires_in");
                ticket = new Ticket();
                ticket.setTicket(jsapi_ticket);
                ticket.setExpiresIn(expires_in);
            } catch (JSONException e) {
                // 获取失败
                log.error("获取jsapi_ticket失败"+jsonObject.getInt("errcode")+"，"+jsonObject.getString("errmsg"));
            }
        }
        return ticket;
    }

    public static Token getToken(String appid, String appsecret) {
        Token token = null;
        String requestUrl = Constants.token_url.replace("APPID", appid).replace("APPSECRET", appsecret);
        // 发起GET请求获取凭证
        JSONObject jsonObject = CommonUtil.httpsRequestToJsonObject(requestUrl, "GET", null);

        if (null != jsonObject) {
            try {
                token = new Token();
                token.setAccessToken(jsonObject.getString("access_token"));
                token.setExpiresIn(jsonObject.getInt("expires_in"));
            } catch (JSONException e) {
                token = null;
                // 获取token失败
                log.error("获取token失败 "+jsonObject.getInt("errcode")+"，"+jsonObject.getString("errmsg"));
            }
        }
        return token;
    }
    /**
     * 创建统一下单的xml的java对象
     * @param bizOrder 系统中的业务单号
     * @param ip 用户的ip地址
     * @param openId 用户的openId
     * @return
     */
    public PayInfo createPayInfo(BizOrder bizOrder,String ip,String openId) {
        PayInfo payInfo = new PayInfo();
        payInfo.setAppid(Constants.appid);
        payInfo.setDevice_info("WEB");
        payInfo.setMch_id(Constants.mch_id);
        payInfo.setNonce_str(CommonUtil.create_nonce_str().replace("-", ""));
        payInfo.setBody("这里是某某白米饭的body");
        payInfo.setAttach(bizOrder.getId());
        payInfo.setOut_trade_no(bizOrder.getOrderCode().concat("A").concat(DateFormatUtils.format(new Date(), "MMddHHmmss")));
        payInfo.setTotal_fee((int)bizOrder.getFeeAmount());
        payInfo.setSpbill_create_ip(ip);
        payInfo.setNotify_url(Constants.notify_url);
        payInfo.setTrade_type("JSAPI");
        payInfo.setOpenid(openId);
        return payInfo;
    }



    /**
     * 获取用户的openId，并放入session
     *
     * @param code 微信返回的code
     */
    private void setOpenId(String code) {
        session.setAttribute("code", code);
        String oauth2_url = Constants.oauth2_url
                .replace("APPID", Constants.appid)
                .replace("SECRET", Constants.appsecret)
                .replace("CODE", String.valueOf(session.getAttribute("code")));
        log.info("oauth2_url:" + oauth2_url);
        JSONObject jsonObject = CommonUtil.httpsRequestToJsonObject(oauth2_url, "POST", null);
        log.info("jsonObject:" + jsonObject);
        Object errorCode = jsonObject.get("errcode");
        if (errorCode != null) {
            log.info("code不合法");
        } else {
            String openId = jsonObject.getString("openid");
            log.info("openId:" + openId);
            session.setAttribute("openId", openId);
        }

//        oauth2_url返回的格式是：
//　　      {
//　　　       "access_token":"ACCESS_TOKEN",
//　　　       "expires_in":7200,
//           "refresh_token":"REFRESH_TOKEN",
//           "openid":"OPENID", "scope":"SCOPE",
//            "unionid": "o6_bmasdasdsad6_2sgVt7hMZOPfL"
//        }
//        Code无效时：
//　　    {
    //　　  "errcode":40029
    //　　　,"errmsg":"invalid code"
    //    }
    }


}
