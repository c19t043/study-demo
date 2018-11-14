package cn.cjf.gateway.config.weixin;

public interface WeiXinConfig {
    /**
     * 统一下单接口地址
     */
    String UNIFIED_ORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
    /**
     * 查询订单
     */
    String QUERY_ORDER_URL = "https://api.mch.weixin.qq.com/pay/orderquery";
    /**
     *关闭订单
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
}
