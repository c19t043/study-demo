package cn.cjf.gateway.domain.param.ali;

import cn.cjf.gateway.annotation.RequiredAnnotation;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class AlipayTradeCloseRequest {
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
    @RequiredAnnotation
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
    @RequiredAnnotation
    @JSONField(name = "out_trade_no")
    private String outTradeNo;
    /**
     * <pre>
     * 卖家端自定义的的操作员 ID
     *      可选
     *      String(28)
     * </pre>
     * 操作员 ID
     */
    @JSONField(name = "operator_id")
    private String operatorId;
}
