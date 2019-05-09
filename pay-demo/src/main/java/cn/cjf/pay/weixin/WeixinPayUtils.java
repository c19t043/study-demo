package cn.cjf.pay.weixin;

import cn.cjf.pay.util.RandomUtil;

/**
 * @author chenjunfan
 * @date 2019/5/9
 */
public class WeixinPayUtils {
    /**
     * 创建支付随机字符串
     *
     * @return
     */
    public static String getNonceStr() {
        return RandomUtil.randomString(RandomUtil.LETTER_NUMBER_CHAR, 16);
    }

    /**
     * 支付时间戳
     *
     * @return
     */
    public static String getTimestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }
}
