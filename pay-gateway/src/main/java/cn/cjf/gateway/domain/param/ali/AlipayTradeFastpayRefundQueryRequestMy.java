package cn.cjf.gateway.domain.param.ali;

import cn.cjf.gateway.annotation.RequiredAnnotation;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class AlipayTradeFastpayRefundQueryRequestMy {
    /**
     * <pre>
     * 该交易在支付宝系统中的交易流水号
     * 最短 16 位，最长 64 位。
     * 和out_trade_no不能同时为空，如果同时传了 out_trade_no和 trade_no，则以 trade_no为准。
     *      特殊可选
     *      String(64)
     * </pre>
     * 交易流水号
     */
    @JSONField(name = "trade_no")
    private String tradeNo;
    /**
     * <pre>
     * 订单支付时传入的商户订单号,和支付宝交易号不能同时为空
     *  trade_no,out_trade_no如果同时存在优先取trade_no
     *      特殊可选
     *      String(64)
     * </pre>
     * 商户订单号
     */
    @JSONField(name = "out_trade_no")
    private String outTradeNo;
    /**
     * <pre>
     * 请求退款接口时，传入的退款请求号，如果在退款请求时未传入，则该值为创建交易时的外部交易号
     *      必选
     *      String(64)
     * </pre>
     * 退款请求号
     */
    @RequiredAnnotation
    @JSONField(name = "out_request_no")
    private String outRequestNo;
    /**
     * <pre>
     * 银行间联模式下有用，其它场景请不要使用；
     * 双联通过该参数指定需要查询的交易所属收单机构的pid
     *      可选
     *      String(16)
     * </pre>
     */
    @JSONField(name = "org_pid")
    private String orgPid;
}
