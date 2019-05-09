package cn.cjf.pay.util;

import java.util.Random;

/**
 * 随机字符串工具类
 */
public class RandomUtil {
	public final static String LETTER_NUMBER_CHAR = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

	/**
	 * 生成随机字符串
	 * @param str 随机数来源字符串
	 * @param length 随机字符串长度
	 * @return
	 */
	public static String randomString(String str, int length) {
		char[] c = str.toCharArray();
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for( int i = 0; i < length; i ++) {
        	char cc = c[random.nextInt(c.length)];
        	sb.append(cc);
        }
        return sb.toString();
	}
	
	public static void main(String[] args) {
		System.out.println(RandomUtil.randomString(RandomUtil.LETTER_NUMBER_CHAR, 32));
	}
}
