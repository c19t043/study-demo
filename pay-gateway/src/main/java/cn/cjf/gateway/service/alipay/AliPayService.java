package cn.cjf.gateway.service.alipay;

public interface AliPayService {
    /**
     * 支付宝移动支付后台通知响应
     */
    /**
     * 支付宝统一预下单接口
     */
    /**
     * <scene>
     *     用于交易创建后，用户在一定时间内未进行支付，可调用该接口直接将未付款的交易进行关闭。
     * </scene>
     * 交易关闭
     */
    Object close(Object o);
    /**
     * PC场景下单并支付
     */
}
