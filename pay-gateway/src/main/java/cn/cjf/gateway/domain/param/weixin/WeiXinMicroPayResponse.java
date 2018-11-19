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
 * <p>
 * 提交付款码支付响应参数
 */
@Data
@ToString
public class WeiXinMicroPayResponse {
    /**
     * <pre>
     *      此字段是通信标识，非交易标识，交易是否成功需要查看result_code来判断
     *      必填
     *      SUCCESS/FAIL
     * </pre>
     * 返回状态码
     */
    @RequiredAnnotation
    @JSONField(name = "return_code")
    private String returnCode;
    /**
     * <pre>
     *      当return_code为FAIL时返回信息为错误原因 ，例如签名失败,参数格式校验错误
     *      必填
     * </pre>
     * 返回信息
     */
    @RequiredAnnotation
    @JSONField(name = "return_msg")
    private String returnMsg;
    /**
     * =========以下字段在return_code为SUCCESS的时候有返回==========
     */
    /**
     * <pre>
     *      调用接口提交的公众账号ID
     *      必填
     * </pre>
     * 公众账号ID
     */
    @RequiredAnnotation
    @JSONField(name = "appid")
    private String appId;
    /**
     * <pre>
     *      调用接口提交的商户号
     *      必填
     * </pre>
     * 商户号
     */
    @RequiredAnnotation
    @JSONField(name = "mch_id")
    private String mchId;
    /**
     * <pre>
     *      自定义参数，可以为请求支付的终端设备号等
     * </pre>
     * 设备号
     */
    @JSONField(name = "device_info")
    private String deviceInfo;
    /**
     * <pre>
     *      微信返回的随机字符串
     *      必填
     * </pre>
     * 随机字符串
     */
    @RequiredAnnotation
    @JSONField(name = "nonce_str")
    private String nonceStr;
    /**
     * <pre>
     *      微信返回的签名值，详见签名算法
     *      必填
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
    /**
     * <pre>
     *      必填
     *      SUCCESS/FAIL
     * </pre>
     * 业务结果
     */
    @RequiredAnnotation
    @JSONField(name = "result_code")
    private String resultCode;
    /**
     * <pre>
     *      当result_code为FAIL时返回错误代码，详细参见下文错误列表
     * </pre>
     * 错误代码
     */
    @JSONField(name = "err_code")
    private String errCode;
    /**
     * <pre>
     *      当result_code为FAIL时返回错误描述，详细参见下文错误列表
     * </pre>
     * 错误代码描述
     */
    @JSONField(name = "err_code_des")
    private String errCodeDes;
    /**
     * ==================以下字段在return_code 和result_code都为SUCCESS的时候有返回
     */
    /**
     * <pre>
     *    此参数为微信用户在商户对应appid下的唯一标识，
     *    必填
     * </pre>
     * 用户标识
     */
    @RequiredAnnotation
    @JSONField(name = "openid")
    private String openId;
    /**
     * <pre>
     *    用户是否关注公众账号，Y-关注，N-未关注
     *    必填
     * </pre>
     * 是否关注公众账号
     */
    @RequiredAnnotation
    @JSONField(name = "is_subscribe")
    private String isSubscribe;
    /**
     * <pre>
     *      JSAPI--JSAPI支付（或小程序支付）、NATIVE--Native支付、APP--app支付，MWEB--H5支付，
     *      必填
     * </pre>
     * 交易类型
     */
    @RequiredAnnotation
    @JSONField(name = "trade_type")
    private String tradeType;
    /**
     * <pre>
     *      CMC	银行类型，采用字符串类型的银行标识
     *      必填
     * </pre>
     * 付款银行
     */
    @RequiredAnnotation
    @JSONField(name = "bank_type")
    private String bankType;
    /**
     * <pre>
     *    符合ISO 4217标准的三位字母代码，默认人民币：CNY
     * </pre>
     * 标价币种
     */
    @JSONField(name = "fee_type")
    private String feeType;
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
     *    当订单使用了免充值型优惠券后返回该参数，应结订单金额=订单金额-免充值优惠券金额。
     * </pre>
     * 应结订单金额
     */
    @JSONField(name = "settlement_total_fee")
    private String settlementTotalFee;
    /**
     * <pre>
     *    “代金券”金额<=订单金额，订单金额-“代金券”金额=现金支付金额，详见支付金额
     * </pre>
     * 代金券金额
     */
    @JSONField(name = "coupon_fee")
    private int couponFee;
    /**
     * <pre>
     *      现金支付金额订单现金支付金额，详见支付金额
     *      必填
     * </pre>
     * 现金支付金额
     */
    @RequiredAnnotation
    @JSONField(name = "cash_fee")
    private String cashFee;
    /**
     * <pre>
     *      CNY	货币类型，符合ISO 4217标准的三位字母代码，默认人民币：CNY，其他值列表详见货币类型
     * </pre>
     * 现金支付币种
     */
    @JSONField(name = "cash_fee_type")
    private String cashFeeType;
    /**
     * <pre>
     *    商户系统内部订单号，要求32个字符内，只能是数字、大小写字母_-|* 且在同一个商户号下唯一。
     *    必填
     * </pre>
     * 商户订单号
     */
    @RequiredAnnotation
    @JSONField(name = "out_trade_no")
    private String outTradeNo;
    /**
     * <pre>
     *    附加数据，原样返回
     * </pre>
     * 附加数据
     */
    @JSONField(name = "attach")
    private String attach;
    /**
     * <pre>
     *    订单支付时间，格式为yyyyMMddHHmmss，如2009年12月25日9点10分10秒表示为20091225091010。其他详见时间规则
     *    必填
     * </pre>
     * 支付完成时间
     */
    @RequiredAnnotation
    @JSONField(name = "time_end")
    private String timeEnd;
    /**
     * <pre>
     *    新增返回，单品优惠功能字段，需要接入请见详细说明
     *    String(6000)
     * </pre>
     * 营销详情
     */
    @JSONField(name = "promotion_detail")
    private String promotionDetail;
}
