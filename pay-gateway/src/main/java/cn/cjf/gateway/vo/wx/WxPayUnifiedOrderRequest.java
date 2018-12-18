//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cn.cjf.gateway.vo.wx;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

@XStreamAlias("xml")
@Data
public class WxPayUnifiedOrderRequest extends WxPayBaseRequest {
    private static final String[] TRADE_TYPES = new String[]{"JSAPI", "NATIVE", "APP", "MWEB"};
    @XStreamAlias("device_info")
    private String deviceInfo;
    @XStreamAlias("body")
    private String body;
    @XStreamAlias("detail")
    private String detail;
    @XStreamAlias("attach")
    private String attach;
    @XStreamAlias("out_trade_no")
    private String outTradeNo;
    @XStreamAlias("fee_type")
    private String feeType;
    @XStreamAlias("total_fee")
    private Integer totalFee;
    @XStreamAlias("spbill_create_ip")
    private String spbillCreateIp;
    @XStreamAlias("time_start")
    private String timeStart;
    @XStreamAlias("time_expire")
    private String timeExpire;
    @XStreamAlias("goods_tag")
    private String goodsTag;
    @XStreamAlias("notify_url")
    private String notifyURL;
    @XStreamAlias("trade_type")
    private String tradeType;
    @XStreamAlias("product_id")
    private String productId;
    @XStreamAlias("limit_pay")
    private String limitPay;
    @XStreamAlias("openid")
    private String openid;
    @XStreamAlias("sub_openid")
    private String subOpenid;
    @XStreamAlias("scene_info")
    private String sceneInfo;
    @XStreamAlias("fingerprint")
    private String fingerprint;
}
