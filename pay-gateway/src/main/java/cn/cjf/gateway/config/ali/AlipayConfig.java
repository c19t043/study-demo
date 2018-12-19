package cn.cjf.gateway.config.ali;

public interface AlipayConfig {
    /**
     * 应用ID,收款账号既是您的APPID对应支付宝账号
     */
    String APP_ID = "";
    /**
     * 商户私钥，您的PKCS8格式RSA2私钥
     */
    String  MERCHANT_PRIVATE_KEY = "";
    /**
     * 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
     */
    String ALIPAY_PUBLIC_KEY = "";
    /**
     * 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
     */
    String NOTIFY_URL = "http://工程公网访问地址/alipay.trade.page.pay-JAVA-UTF-8/notify_url.jsp";
    /**
     * 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
     */
    String RETURN_URL = "http://工程公网访问地址/alipay.trade.page.pay-JAVA-UTF-8/return_url.jsp";
    /**
     * 签名方式
     */
    String SIGN_TYPE = "RSA2";
    /**
     * 字符编码格式
     */
    String CHARSET = "utf-8";
    /**
     * 支付宝网关:正式环境
     */
    String GATEWAY_URL = "https://openapi.alipay.com/gateway.do";
}
