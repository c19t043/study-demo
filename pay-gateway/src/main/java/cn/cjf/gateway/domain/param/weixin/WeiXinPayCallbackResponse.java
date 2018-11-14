package cn.cjf.gateway.domain.param.weixin;

import cn.cjf.gateway.annotation.RequiredAnnotation;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.ToString;

/**
 * <pre>
 *     支付结果通知微信文档地址
 *     https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_8
 * </pre>
 * 支付结果通知响应参数
 */
@Data
@ToString
public class WeiXinPayCallbackResponse {
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
}
