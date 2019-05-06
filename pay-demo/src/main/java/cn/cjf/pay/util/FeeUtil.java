package cn.cjf.pay.util;

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
	 * @param fee
	 * @return
	 */
	public static int getAliServiceFee(Integer fee) {
		try {
			Long serviceFee = Math.round(fee * ALI_SERVICE_FEE_RATE);
			return serviceFee.intValue();
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	/**
	 * 计算微信手续费金额
	 * @param fee
	 * @return
	 */
	public static int getWeixinServiceFee(Integer fee) {
		try {
			Long serviceFee = Math.round(fee * WEIXIN_SERVICE_FEE_RATE);
			return serviceFee.intValue();
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	public static void main(String[] args) {
		Integer a = 88888;
		System.out.println(getAliServiceFee(a));
	}
	
}
