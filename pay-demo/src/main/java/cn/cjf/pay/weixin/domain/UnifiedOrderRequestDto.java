package cn.cjf.pay.weixin.domain;

import lombok.Data;

/**
 * @author chenjunfan
 * @date 2019/5/16
 */
@Data
public class UnifiedOrderRequestDto {
    /**
     * 公众账号ID
     * appid
     * 微信支付分配的公众账号ID（企业号corpid即为此appId）
     * String(32)
     */
    private String appid;
    /**
     * 商户号
     * mch_id
     * 微信支付分配的商户号
     * String(32)
     */
    private String mchId;
    /**
     * 随机字符串
     * nonce_str
     * 随机字符串，长度要求在32位以内
     * String(32)
     */
    private String nonceStr;
    /**
     * 签名
     * 通过签名算法计算得出的签名值
     * String(32)
     */
    private String sign;
    /**
     * 商品描述
     * 商品简单描述
     * String(128)
     */
    private String body;
    /**
     * 商户订单号
     * 商户系统内部订单号，要求32个字符内，只能是数字、大小写字母_-|* 且在同一个商户号下唯一
     * String(32)
     */
    private String outTradeNo;
    /**
     * 标价金额
     * 订单总金额，单位为分
     * Int
     */
    private int totalFee;
    /**
     * 终端IP
     * 调用微信支付API的机器IP
     * String(64)
     */
    private String spbillCreateIp;
    /**
     * 通知地址
     * 异步接收微信支付结果通知的回调地址，通知url必须为外网可访问的url，不能携带参数。
     * String(256)
     */
    private String notifyUrl;
    /**
     * 交易类型
     * trade_type
     * JSAPI -JSAPI支付
     * NATIVE -Native支付
     * APP -APP支付
     * String(16)
     */
    private String tradeType;


    //===========================================不是必传参数

    /**
     * 设备号
     * device_info
     * 自定义参数，可以为终端设备号(门店号或收银设备ID)，PC网页或公众号内支付可以传"WEB"
     * String(32)
     */
    private String deviceInfo;

    /**
     * 签名类型
     * 默认为MD5，支持HMAC-SHA256和MD5
     * sign_type
     * String(32)
     */
    private String signType;
    /**
     * 商品详情
     * detail
     * 商品详细描述，对于使用单品优惠的商户，改字段必须按照规范上传
     * String(6000)
     */
    private String detail;

    /**
     * 附加数据
     * attach
     * 附加数据，在查询API和支付通知中原样返回，可作为自定义参数使用。
     * String(127)
     */
    private String attach;

    /**
     * 标价币种
     * fee_type
     * 符合ISO 4217标准的三位字母代码，默认人民币：CNY
     * String(16)
     */
    private String feeType;
    /**
     * 交易起始时间
     * time_start
     * 订单生成时间，格式为yyyyMMddHHmmss，如2009年12月25日9点10分10秒表示为20091225091010。
     * String(14)
     */
    private String timeStart;
    /**
     * 交易结束时间
     * time_expire
     * 订单失效时间，格式为yyyyMMddHHmmss，
     * 如2009年12月27日9点10分10秒表示为20091227091010。
     * 订单失效时间是针对订单号而言的，由于在请求支付的时候有一个必传参数prepay_id只有两小时的有效期，
     * 所以在重入时间超过2小时的时候需要重新请求下单接口获取新的prepay_id
     * String(14)
     */
    private String timeExpire;

    /**
     * 订单优惠标记
     * goods_tag
     * 订单优惠标记，使用代金券或立减优惠功能时需要的参数
     * String(32)
     */
    private String goodsTag;

    /**
     * 商品ID
     * product_id
     * trade_type=NATIVE时，此参数必传。此参数为二维码中包含的商品ID，商户自行定义。
     * String(32)
     */
    private String productId;

    /**
     * 指定支付方式
     * limit_pay
     * 上传此参数no_credit--可限制用户不能使用信用卡支付
     * String(32)
     */
    private String limitPay;

    /**
     * 用户标识
     * openid
     * trade_type=JSAPI时（即JSAPI支付），此参数必传，此参数为微信用户在商户对应appid下的唯一标识。openid如何获取
     * String(128)
     */
    private String openid;

    /**
     * 电子发票入口开放标识
     * receipt
     * 	Y，传入Y时，支付成功消息和支付详情页将出现开票入口。
     * 	需要在微信支付商户平台或微信公众平台开通电子发票功能，传此字段才可生效
     * String(8)
     */
    private String receipt;

    /**
     * 场景信息
     * scene_info
     * 该字段常用于线下活动时的场景信息上报，支持上报实际门店信息，商户也可以按需求自己上报相关信息。
     * 该字段为JSON对象数据，
     * 对象格式为{"store_info":{"id": "门店ID","name": "名称","area_code": "编码","address": "地址" }}
     * String(256)
     */
    private String sceneInfo;
}
