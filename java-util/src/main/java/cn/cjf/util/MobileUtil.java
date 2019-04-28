package cn.cjf.util;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 手机号工具类
 */
public class MobileUtil {

    private static final Pattern PHONE_PATTERN = Pattern.compile("^((13[0-9])|(14[0-9])|(15[0-9])|(16[0-9])|(17[0-9])|(18[0-9])|(19[0-9]))\\d{8}$");

    /**
     * 验证手机号码合法性
     *
     * @param mobile
     * @return boolean
     */
    public static boolean isMobile(String mobile) {

        if (StringUtils.isEmpty(mobile)) {
            return false;
        }
        Matcher m = PHONE_PATTERN.matcher(mobile);
        return m.matches();
    }

    /**
     * 隐藏手机号码中间4位数字。
     *
     * @param mobile
     * @return String
     */
    public static String hidePhonePart(String mobile) {
        if (null != mobile && isMobile(mobile)) {
            return mobile.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
        } else {
            return mobile;
        }
    }
}
