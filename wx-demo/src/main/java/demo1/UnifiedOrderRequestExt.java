package demo1;

/**
 * 统一下单请求参数(非必填)
 *
 * @author Y
 */
public class UnifiedOrderRequestExt extends UnifiedOrderRequest {

    private String device_info;            //设备号
    private String detail;                //商品详情
    private String attach;                //附加数据
    private String fee_type;            //货币类型
    private String time_start;            //交易起始时间
    private String time_expire;            //交易结束时间
    private String goods_tag;            //商品标记
    private String product_id;            //商品ID
    private String limit_pay;            //指定支付方式
    private String openid;                //用户标识
}