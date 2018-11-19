package cn.cjf.gateway.domain.param.weixin;

import cn.cjf.gateway.annotation.RequiredAnnotation;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.ToString;

/**
 * <pre>
 *     提交付款码支付微信文档地址
 *     https://pay.weixin.qq.com/wiki/doc/api/micropay.php?chapter=9_10&index=1
 * </pre>
 *
 *  提交付款码支付请求参数
 */
@Data
@ToString
public class WeiXinMicroPayRequest {
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
     *    符合ISO 4217标准的三位字母代码，默认人民币：CNY
     * </pre>
     *  标价币种
     */
    @JSONField(name = "fee_type")
    private String feeType;
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
     *    WXG	订单优惠标记，使用代金券或立减优惠功能时需要的参数，说明详见代金券或立减优惠
     * </pre>
     *   订单优惠标记
     */
    @JSONField(name = "goods_tag")
    private String goodsTag;
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
     *    码支付授权码，设备读取用户微信中的条码或者二维码信息
     *    （注：用户付款码条形码规则：18位纯数字，以10、11、12、13、14、15开头）
     *    String(128)
     * </pre>
     *   授权码
     */
    @RequiredAnnotation
    @JSONField(name = "auth_code")
    private String authCode;
    /**
     * <pre>
     *    该字段用于上报场景信息，目前支持上报实际门店信息。
     *    该字段为JSON对象数据，
     *    对象格式为{"store_info":{"id": "门店ID","name": "名称","area_code": "编码","address": "地址" }} ，
     *    字段详细说明请点击行前的+展开
     *    String(256)
     * </pre>
     *   场景信息
     */
    @JSONField(name = "scene_info")
    private String sceneInfo;
}
