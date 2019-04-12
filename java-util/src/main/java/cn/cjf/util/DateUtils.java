package cn.cjf.util;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateUtils {

    public final static String TIME_END_SUFFIX = " 23:59:59";
    public final static String TIME_START_SUFFIX = " 00:00:00";

    /**
     * 获取当前时间字符串
     */
    public static String getCurrentTimeStr() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    /**
     * 格式化时间字符串
     */
    public static String formatDate2StrWith_yyyy_MM_dd(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    /**
     * 格式化时间字符串
     */
    public static String formatDate2StrWith_yyyy_MM_dd_HH_mm_ss(Date date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }
}
