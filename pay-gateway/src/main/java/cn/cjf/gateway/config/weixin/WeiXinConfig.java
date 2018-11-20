package cn.cjf.gateway.config.weixin;

public interface WeiXinConfig {
    /**
     * 微信公众号的id：app_id
     */
    String APP_ID = "wx123";
    /**
     * 商户id:mch_id
     */
    String MCH_ID = "qwe";
    /**
     * api秘钥：api_key
     */
    String API_KEY = "qwe123";
    /**
     * create_ip
     */
    String CREATE_IP = "";
    /**
     * 回调地址
     */
    String NOTIFY_URL = "";
    /**
     * 统一下单接口地址
     */
    String UNIFIED_ORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
    /**
     * 查询订单
     */
    String QUERY_ORDER_URL = "https://api.mch.weixin.qq.com/pay/orderquery";
    /**
     * 关闭订单
     */
    String CLOSE_ORDER_URL = "https://api.mch.weixin.qq.com/pay/closeorder";
    /**
     * 申请退款
     */
    String REFUND_URL = "https://api.mch.weixin.qq.com/secapi/pay/refund";
    /**
     * 查询退款
     */
    String QUERY_REFUND_URL = "https://api.mch.weixin.qq.com/pay/refundquery";
    /**
     * 提交付款码支付
     */
    String MICRO_PAY_URL = "https://api.mch.weixin.qq.com/pay/micropay";
    /**
     * 撤销订单
     */
    String REVERSE_URL = "https://api.mch.weixin.qq.com/secapi/pay/reverse";
}
