package cn.cjf.gateway.domain.param.ali;

import cn.cjf.gateway.annotation.RequiredAnnotation;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class CommonRequest {
    /**
     * <pre>
     *     必填
     *     String(32)
     * </pre>
     * 支付宝分配给开发者的应用ID
     */
    @RequiredAnnotation
    @JSONField(name = "app_id")
    private String appId;
    /**
     * <pre>
     *     必填
     *     String(128)
     *     例子：alipay.trade.page.pay
     * </pre>
     * 接口名称
     */
    @RequiredAnnotation
    @JSONField(name = "method")
    private String method;
    /**
     * <pre>
     *     仅支持JSON
     *     String(40)
     *     例子：JSON
     * </pre>
     * 数据格式
     */
    @JSONField(name = "format")
    private String format;
    /**
     * <pre>
     *     仅支持JSON
     *     String(256)
     *     例子：HTTP/HTTPS开头字符串 https://m.alipay.com/Gk8NF23
     * </pre>
     * 同步返回地址
     */
    @JSONField(name = "return_url")
    private String returnUrl;
    /**
     * <pre>
     *     必填
     *     String(10)
     *     例子：请求使用的编码格式，如utf-8,gbk,gb2312等	utf-8
     * </pre>
     * 编码格式
     */
    @RequiredAnnotation
    @JSONField(name = "charset")
    private String charset;
    /**
     * <pre>
     *     商户生成签名字符串所使用的签名算法类型，目前支持RSA2和RSA，推荐使用RSA2
     *     必填
     *     String(10)
     *     例子：RSA2
     * </pre>
     * 签名算法类型
     */
    @RequiredAnnotation
    @JSONField(name = "sign_type")
    private String signType;
    /**
     * <pre>
     *     商户请求参数的签名串
     *     必填
     *     String(256)
     *     例子：RSA2
     * </pre>
     * 签名串
     */
    @RequiredAnnotation
    @JSONField(name = "sign")
    private String sign;
    /**
     * <pre>
     *     发送请求的时间，格式"yyyy-MM-dd HH:mm:ss"
     *     必填
     *     String(19)
     *     例子：2014-07-24 03:07:50
     * </pre>
     * 发送请求的时间
     */
    @RequiredAnnotation
    @JSONField(name = "timestamp")
    private String timestamp;
    /**
     * <pre>
     *     调用的接口版本,固定为：1.0
     *     必填
     *     String(3)
     *     例子：1.0
     * </pre>
     * 调用的接口版本
     */
    @RequiredAnnotation
    @JSONField(name = "version")
    private String version;
    /**
     * <pre>
     *     支付宝服务器主动通知商户服务器里指定的页面http/https路径
     *     必填
     *     String(256)
     *     例子：https://api.xx.com/receive_notify.htm
     * </pre>
     * 回调url
     */
    @JSONField(name = "notify_url")
    private String notifyUrl;
    /**
     * <pre>
     *     最大长度不限，除公共参数外所有请求参数都必须放在这个参数中传递，
     *     必填
     * </pre>
     * 业务请求参数的集合
     */
    @RequiredAnnotation
    @JSONField(name = "biz_content")
    private String biz_content;
}
