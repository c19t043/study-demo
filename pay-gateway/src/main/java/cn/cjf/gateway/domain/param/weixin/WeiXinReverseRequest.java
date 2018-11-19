package cn.cjf.gateway.domain.param.weixin;

import cn.cjf.gateway.annotation.RequiredAnnotation;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.ToString;

/**
 * <pre>
 *     撤销订单微信文档地址
 *     https://pay.weixin.qq.com/wiki/doc/api/micropay.php?chapter=9_11
 * </pre>
 * <p>
 * 撤销订单请求参数
 */
@Data
@ToString
public class WeiXinReverseRequest {
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
}
