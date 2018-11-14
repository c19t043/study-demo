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
 * 申请退款响应参数
 */
@Data
@ToString
public class WeiXinRefundResponse {
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
     * <pre>
     *     微信的订单号
     *     必填
     * </pre>
     * 微信订单号
     */
    @RequiredAnnotation
    @JSONField(name = "transaction_id")
    private String transactionId;
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
     *    微信退款单号
     *    必填
     * </pre>
     * 微信退款单号
     */
    @RequiredAnnotation
    @JSONField(name = "refund_id")
    private int refundId;
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
     *      去掉非充值代金券退款金额后的退款金额，退款金额=申请退款金额-非充值代金券退款金额，退款金额<=申请退款金额
     * </pre>
     * 应结退款金额
     */
    @JSONField(name = "settlement_refund_fee")
    private String settlementRefundFee;
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
     *    符合ISO 4217标准的三位字母代码，默认人民币：CNY
     * </pre>
     * 标价币种
     */
    @JSONField(name = "fee_type")
    private String feeType;
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
     *      现金退款金额，单位为分，只能为整数，详见支付金额
     * </pre>
     * 现金退款金额
     */
    @JSONField(name = "cash_refund_fee")
    private String cashRefundFee;
}
