package cn.cjf.pay.weixin.constant;

/**
 * @author chenjunfan
 * @date 2019/5/16
 */
public enum WeixinTradeTypeEnum {
    /**
     * JSAPI支付（或小程序支付）
     */
    TYPE_JSAPI(1, "JSAPI"),
    /**
     * Native支付
     */
    TYPE_NATIVE(2, "NATIVE"),
    /**
     * app支付
     */
    TYPE_APP(3, "APP"),
    /**
     * H5支付
     */
    TYPE_H5(4, "MWEB");

    private int code;
    private String desc;

    WeixinTradeTypeEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
