package cn.cjf.gateway.domain.param.weixin;

import cn.cjf.gateway.annotation.RequiredAnnotation;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.ToString;

/**
 * <pre>
 *     申请退款微信文档地址
 *     https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_4
 * </pre>
 * <p>
 * 申请退款请求参数
 */
@Data
@ToString
public class WeiXinRefundRequest {
    /**
     * <pre>
     *  微信支付分配的公众账号ID（企业号corpid即为此appId）：必填
     * </pre>
     * <p>
     * 公众账号ID
     */
    @RequiredAnnotation
    @JSONField(name = "appid")
    private String appId;
    /**
     * <pre>
     *     微信支付分配的商户号：必填
     * </pre>
     * 商户号
     */
    @RequiredAnnotation
    @JSONField(name = "mch_id")
    private String mchId;
    /**
     * <pre>
     *     随机字符串，长度要求在32位以内。推荐随机数生成算法：必填
     * </pre>
     * 随机字符串
     */
    @RequiredAnnotation
    @JSONField(name = "nonce_str")
    private String nonceStr;
    /**
     * <pre>
     *     通过签名算法计算得出的签名值，详见签名生成算法：必填
     * </pre>
     * 签名
     */
    @RequiredAnnotation
    @JSONField(name = "sign")
    private String sign;
    /**
     * <pre>
     *     MD5	签名类型，默认为MD5，支持HMAC-SHA256和MD5。
     * </pre>
     * 签名类型
     */
    @JSONField(name = "sign_type")
    private String signType;
    //=====================微信订单号,商户订单号，二选一
    /**
     * <pre>
     *     微信的订单号，建议优先使用
     *     必填
     * </pre>
     * 微信订单号
     */
    @JSONField(name = "transaction_id")
    private String transactionId;
    /**
     * <pre>
     *    商户系统内部订单号，要求32个字符内，只能是数字、大小写字母_-|* 且在同一个商户号下唯一。
     *    必填
     * </pre>
     * 商户订单号
     */
    @JSONField(name = "out_trade_no")
    private String outTradeNo;
    //=====================
    /**
     * <pre>
     *    商户系统内部的退款单号，商户系统内部唯一，只能是数字、大小写字母_-|*@ ，同一退款单号多次请求只退一笔。
     *    必填
     * </pre>
     * 商户退款单号
     */
    @RequiredAnnotation
    @JSONField(name = "out_refund_no")
    private int outRefundNo;
    /**
     * <pre>
     *    订单总金额，单位为分。
     *    必填
     * </pre>
     * 标价金额
     */
    @RequiredAnnotation
    @JSONField(name = "total_fee")
    private int totalFee;
    /**
     * <pre>
     *    退款总金额，订单总金额，单位为分，只能为整数，详见支付金额
     *    必填
     * </pre>
     * 退款金额
     */
    @RequiredAnnotation
    @JSONField(name = "refund_fee")
    private int refundFee;
    /**
     * <pre>
     *    CNY	退款货币类型，需与支付一致，或者不填。符合ISO 4217标准的三位字母代码，
     *    默认人民币：CNY，其他值列表详见货币类型
     * </pre>
     * 退款货币种类
     */
    @JSONField(name = "refund_fee_type")
    private String refundFeeType;
    /**
     * <pre>
     *    若商户传入，会在下发给用户的退款消息中体现退款原因
     *    比如：商品已售完
     * </pre>
     * 退款原因
     */
    @JSONField(name = "refund_desc")
    private String refundDesc;

    /**
     * <pre>
     *    仅针对老资金流商户使用
     *    REFUND_SOURCE_UNSETTLED_FUNDS---未结算资金退款（默认使用未结算资金退款）
     *    REFUND_SOURCE_RECHARGE_FUNDS---可用余额退款
     * </pre>
     * 退款资金来源
     */
    @JSONField(name = "refund_account")
    private String refundAccount;


    /**
     * <pre>
     *    异步接收微信支付退款结果通知的回调地址，通知URL必须为外网可访问的url，不允许带参数
     *    如果参数中传了notify_url，则商户平台上配置的回调地址将不会生效。
     * </pre>
     * 退款结果通知url
     */
    @JSONField(name = "notify_url")
    private String notifyUrl;
}
