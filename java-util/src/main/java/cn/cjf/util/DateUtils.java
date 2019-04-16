package cn.cjf.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    public final static String TIME_END_SUFFIX = " 23:59:59";
    public final static String TIME_START_SUFFIX = " 00:00:00";

    public static final String DATE_PATTERN_FULL = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_PATTERN_SPECIAL_1 = "yyyy/MM/dd HH:mm";
    public static final String DATE_PATTERN_DATE = "yyyy-MM-dd";

    public static Date getZeroDate() {
        Date date = new Date(0);
        return date;
    }

    public static String getDateStr(Date date) {
        if (null == date || date.getTime() == 0) {
            return "";
        }
        SimpleDateFormat format = new SimpleDateFormat(DATE_PATTERN_FULL);
        return format.format(date);
    }

    public static String getDateStr(Date date, String pattern) {
        if (null == date || date.getTime() == 0) {
            return "";
        }
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(date);
    }

    /**
     * 格式化时间字符串
     */
    public static String formatDate2StrWith_yyyy_MM_dd(Date date) {
        if (null == date || date.getTime() == 0) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN_DATE);
        return sdf.format(date);
    }

    /**
     * 格式化时间字符串
     */
    public static String formatDate2StrWith_yyyy_MM_dd_HH_mm_ss(Date date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN_FULL);
        return sdf.format(date);
    }
}
