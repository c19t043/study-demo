package cn.cjf.pay.util;

/**
 * 费率计算
 */
public class FeeUtil {

    /**
     * 支付宝 服务费的费率
     */
    private static final double ALI_SERVICE_FEE_RATE = 0.0055;

    /**
     * 微信支付 服务费的费率
     */
    private static final double WEIXIN_SERVICE_FEE_RATE = 0.006;

    /**
     * 计算支付宝手续费金额
     *
     * @param fee
     * @return
     */
    public static int getAliServiceFee(Integer fee) {
        return getServiceFee(fee, ALI_SERVICE_FEE_RATE);
    }

    /**
     * 计算微信手续费金额
     *
     * @param fee
     * @return
     */
    public static int getWeixinServiceFee(Integer fee) {
        return getServiceFee(fee, WEIXIN_SERVICE_FEE_RATE);
    }

    private static int getServiceFee(Integer fee, double serviceFeeRate) {
        Long serviceFee = Math.round(fee * serviceFeeRate);
        return serviceFee.intValue();
    }

    public static void main(String[] args) {
        Integer a = 88888;
        System.out.println(getAliServiceFee(a));
    }

}
