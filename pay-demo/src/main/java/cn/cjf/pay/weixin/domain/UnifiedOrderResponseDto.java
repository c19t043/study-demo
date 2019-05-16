package cn.cjf.pay.weixin.domain;

import lombok.Data;

/**
 * @author chenjunfan
 * @date 2019/5/16
 */
@Data
public class UnifiedOrderResponseDto {
    /**
     * 返回状态码
     * return_code
     * SUCCESS/FAIL
     * 此字段是通信标识，非交易标识，交易是否成功需要查看result_code来判断
     * String(16)
     * 是
     */
    private String returnCode;

    /**
     * 返回信息
     * return_msg
     * 当return_code为FAIL时返回信息为错误原因 ，例如
     * 签名失败
     * 参数格式校验错误
     * String(128)
     * 是
     */
    private String returnMsg;

    //=====================================以下字段在return_code为SUCCESS的时候有返回
    /**
     * 公众账号ID
     * appid
     * 调用接口提交的公众账号ID
     * String(32)
     * 是
     */
    private String appid;
    /**
     * 商户号
     * mch_id
     * 调用接口提交的商户号
     * String(32)
     * 是
     */
    private String mchId;
    /**
     * 设备号
     * device_info
     * 自定义参数，可以为终端设备号(门店号或收银设备ID)，PC网页或公众号内支付可以传"WEB"
     * String(32)
     * 否
     */
    private String deviceInfo;
    /**
     * 随机字符串
     * nonce_str
     * 微信返回的随机字符串
     * String(32)
     * 是
     */
    private String nonceStr;
    /**
     * 签名
     * sign
     * 微信返回的签名值
     * String(32)
     * 是
     */
    private String sign;

    /**
     * 业务结果
     * result_code
     * SUCCESS/FAIL
     * String(16)
     * 是
     */
    private String resultCode;
    /**
     * 错误代码
     * err_code
     * 当result_code为FAIL时返回错误代码，详细参见下文错误列表
     * String(32)
     * 否
     */
    private String errCode;
    /**
     * 错误代码描述
     * err_code_des
     * 当result_code为FAIL时返回错误描述，详细参见下文错误列表
     * String(128)
     * 否
     */
    private String errCodeDes;

    //======================================以下字段在return_code 和result_code都为SUCCESS的时候有返回
    /**
     * 交易类型
     * trade_type
     * JSAPI -JSAPI支付
     * NATIVE -Native支付
     * APP -APP支付
     * String(16)
     * 是
     */
    private String tradeType;
    /**
     * 预支付交易会话标识
     * prepay_id
     * 微信生成的预支付会话标识，用于后续接口调用中使用，该值有效期为2小时
     * String(64)
     * 是
     */
    private String prepayId;
    /**
     * 二维码链接
     * code_url
     * trade_type=NATIVE时有返回，此url用于生成支付二维码，然后提供给用户进行扫码支付。
     * 注意：code_url的值并非固定，使用时按照URL格式转成二维码即可
     * String(64)
     * 否
     */
    private String codeUrl;

}
