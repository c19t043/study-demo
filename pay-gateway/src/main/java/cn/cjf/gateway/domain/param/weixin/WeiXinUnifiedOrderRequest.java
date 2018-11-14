package cn.cjf.gateway.domain.param.weixin;

import cn.cjf.gateway.annotation.RequiredAnnotation;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.ToString;

/**
 * <pre>
 *     统一下单微信文档地址
 *     https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_1
 * </pre>
 *
 *  统一下单请求参数
 */
@Data
@ToString
public class WeiXinUnifiedOrderRequest {
    /**
     * <pre>
     *  微信支付分配的公众账号ID（企业号corpid即为此appId）：必填
     * </pre>
     *
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
     *     自定义参数，自定义参数，可以为请求支付的终端设备号等
     * </pre>
     * 设备号
     */
    @JSONField(name = "device_info")
    private String deviceInfo;
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
     *  签名
     */
    @RequiredAnnotation
    @JSONField(name = "sign")
    private String sign;
    /**
     * <pre>
     *     MD5	签名类型，默认为MD5，支持HMAC-SHA256和MD5。
     * </pre>
     *  签名类型
     */
    @JSONField(name = "sign_type")
    private String signType;
    /**
     * <pre>
     *    商品简单描述，该字段请按照规范传递，具体请见参数规定: 必填
     *    比如：腾讯充值中心-QQ会员充值
     * </pre>
     *  商品描述
     */
    @RequiredAnnotation
    @JSONField(name = "body")
    private String body;

    /**
     * <pre>
     *    商品详细描述，对于使用单品优惠的商户，改字段必须按照规范上传，详见“单品优惠参数说明”
     * </pre>
     *  商品详情
     */
    @JSONField(name = "detail")
    private String detail;
    /**
     * <pre>
     *    附加数据，在查询API和支付通知中原样返回，可作为自定义参数使用。
     * </pre>
     *  附加数据
     */
    @JSONField(name = "attach")
    private String attach;
    /**
     * <pre>
     *    商户系统内部订单号，要求32个字符内，只能是数字、大小写字母_-|* 且在同一个商户号下唯一。
     *    必填
     * </pre>
     *  商户订单号
     */
    @RequiredAnnotation
    @JSONField(name = "out_trade_no")
    private String outTradeNo;
    /**
     * <pre>
     *    符合ISO 4217标准的三位字母代码，默认人民币：CNY
     * </pre>
     *  标价币种
     */
    @JSONField(name = "fee_type")
    private String feeType;
    /**
     * <pre>
     *    订单总金额，单位为分。
     *    必填
     * </pre>
     *  标价金额
     */
    @RequiredAnnotation
    @JSONField(name = "total_fee")
    private int totalFee;
    /**
     * <pre>
     *    APP和网页支付提交用户端ip，Native支付填调用微信支付API的机器IP
     *    必填
     * </pre>
     *  终端IP
     */
    @RequiredAnnotation
    @JSONField(name = "spbill_create_ip")
    private String spBillCreateIp;
    /**
     * <pre>
     *    订单生成时间，格式为yyyyMMddHHmmss，如2009年12月25日9点10分10秒表示为20091225091010。
     * </pre>
     *   交易起始时间
     */
    @JSONField(name = "time_start")
    private String timeStart;
    /**
     * <pre>
     *    订单失效时间，格式为yyyyMMddHHmmss，如2009年12月27日9点10分10秒表示为20091227091010。
     *    建议：最短失效时间间隔大于1分钟
     * </pre>
     *   订单失效时间
     */
    @JSONField(name = "time_expire")
    private String timeExpire;
    /**
     * <pre>
     *    WXG	订单优惠标记，使用代金券或立减优惠功能时需要的参数，说明详见代金券或立减优惠
     * </pre>
     *   订单优惠标记
     */
    @JSONField(name = "goods_tag")
    private String goodsTag;
    /**
     * <pre>
     *    异步接收微信支付结果通知的回调地址，通知url必须为外网可访问的url，不能携带参数。
     *    必填
     * </pre>
     *  通知地址
     */
    @RequiredAnnotation
    @JSONField(name = "notify_url")
    private String notifyUrl;
    /**
     * <pre>
     *     JSAPI--JSAPI支付（或小程序支付）、NATIVE--Native支付、APP--app支付，MWEB--H5支付，
     *     不同trade_type决定了调起支付的方式，请根据支付产品正确上传
     *     MICROPAY--付款码支付，付款码支付有单独的支付接口，所以接口不需要上传，该字段在对账单中会出现
     *    必填
     * </pre>
     *  交易类型
     */
    @RequiredAnnotation
    @JSONField(name = "trade_type")
    private String tradeType;
    /**
     * <pre>
     *    trade_type=NATIVE时（即Native支付），此参数必传。此参数为二维码中包含的商品ID，商户自行定义。
     * </pre>
     *   商品ID
     */
    @JSONField(name = "product_id")
    private String productId;
    /**
     * <pre>
     *    上传此参数no_credit--可限制用户不能使用信用卡支付
     * </pre>
     *   指定支付方式
     */
    @JSONField(name = "limit_pay")
    private String limitPay;
    /**
     * <pre>
     *    此参数为微信用户在商户对应appid下的唯一标识，
     *    openid如何获取，可参考【获取openid】。
     *    企业号请使用【企业号OAuth2.0接口】获取企业号内成员userid，再调用【企业号userid转openid接口】进行转换
     *    trade_type=JSAPI时（即JSAPI支付），此参数必传
     * </pre>
     *   用户标识
     */
    @JSONField(name = "openid")
    private String openId;
}
