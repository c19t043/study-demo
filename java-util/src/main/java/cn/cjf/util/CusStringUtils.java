package cn.cjf.util;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;

public class CusStringUtils extends StringUtils {

    public static int toInt(String str) {
        if (StringUtils.isEmpty(str)) {
            return 0;
        }
        return Integer.parseInt(str);
    }

    /**
     * 隐藏手机号中间4位数为****
     */
    public static String hideMobile(String mobile) {
        if (StringUtils.isNotBlank(mobile) && isMobileNO(mobile)) {
            return mobile.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
        }
        return mobile;
    }

    /**
     * 验证手机号码合法性
     *
     * @param mobile
     * @return
     */
    public static boolean isMobileNO(String mobile) {
        if (mobile == null) {
            return false;
        }
        Pattern p = compile("^((13[0-9])|(14[0-9])|(15[0-9])|(17[0-9])|(18[0-9]))\\d{8}$");
        Matcher m = p.matcher(mobile);
        return m.matches();
    }

    /**
     * 分转元，保留两位小数
     */
    public static String retain2PositionDecimal(int data) {
        if (data == 0) {
            return "0.00";
        }
        int yuan = data / 100;
        int fen = data % 100;
        if (fen < 0) {
            fen = -fen;
        }
        return yuan + "." + String.format("%02d", fen);
    }

    public static String retain2PositionDecimal(Integer data) {
        if (data == null) {
            data = 0;
        }
        return retain2PositionDecimal(data.intValue());
    }

    /**
     * sql特殊字符转义处理
     *
     * @author:lihang
     */
    public static String escapeSql(String str) {
        final String specialChar0 = "\\\\";
        final String specialChar1 = "_";
        final String specialChar2 = "%";
        return str.replaceAll(specialChar1, specialChar0 + specialChar1)
                .replaceAll(specialChar2, specialChar0 + specialChar2);
    }
}
