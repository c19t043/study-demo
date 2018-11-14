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
 * <p>
 * 统一下单响应参数
 */
@Data
@ToString
public class WeiXinUnifiedOrderResponse {
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
     *  签名类型
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
     *      微信生成的预支付会话标识，用于后续接口调用中使用，该值有效期为2小时
     *      必填
     * </pre>
     * 预支付交易会话标识
     */
    @RequiredAnnotation
    @JSONField(name = "prepay_id")
    private String prepayId;
    /**
     * <pre>
     *      trade_type为NATIVE时有返回，用于生成二维码，展示给用户进行Native支付
     * </pre>
     * 二维码链接
     */
    @JSONField(name = "code_url")
    private String codeUrl;
}
