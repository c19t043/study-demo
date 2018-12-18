package demo1;

/**
 * * 统一下单请求参数(必填)
 * * @author Y
 * *
 */
public class UnifiedOrderRequest {
    private String appid;   //公众账号ID
    private String mch_id;  //商户号
    private String nonce_str;   //随机字符串
    private String sign;//签名
    private String body;//商品描述
    private String out_trade_no;//<span style="white-space:pre"></span>//商户订单号
    private String total_fee;   //总金额
    private String spbill_create_ip;//<span style="white-space:pre"></span>//终端IP
    private String notify_url;  //通知地址
    private String trade_type;  //交易类型
}