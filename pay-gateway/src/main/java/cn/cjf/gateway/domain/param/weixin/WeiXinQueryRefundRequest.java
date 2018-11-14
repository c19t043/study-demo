package cn.cjf.gateway.domain.param.weixin;

import cn.cjf.gateway.annotation.RequiredAnnotation;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.ToString;

/**
 * <pre>
 *     查询退款微信文档地址
 *     https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_5
 * </pre>
 * <p>
 * 查询退款请求参数
 */
@Data
@ToString
public class WeiXinQueryRefundRequest {
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
    //=====================微信订单号,商户订单号，商户退款单号，微信退款单号 四选一
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
    /**
     * <pre>
     *    商户系统内部的退款单号，商户系统内部唯一，只能是数字、大小写字母_-|*@ ，同一退款单号多次请求只退一笔。
     *    必填
     * </pre>
     * 商户退款单号
     */
    @JSONField(name = "out_refund_no")
    private int outRefundNo;
    /**
     * <pre>
     *    微信退款单号
     *    必填
     * </pre>
     * 微信退款单号
     */
    @JSONField(name = "refund_id")
    private int refundId;
    //=====================
    /**
     * <pre>
     *    偏移量，当部分退款次数超过10次时可使用，表示返回的查询结果从这个偏移量开始取记录
     * </pre>
     * 偏移量
     */
    @JSONField(name = "offset")
    private int offset;
}
