package cn.cjf.chat.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 日期工具类
 *
 * @author CJF
 */
public class DateUtil {
    private final static Map<String, ThreadLocal<SimpleDateFormat>> sdfMap = new HashMap<>();

    private static SimpleDateFormat getDateFormat(String pattern) {
        SimpleDateFormat simpleDateFormat;
        if (sdfMap.containsKey(pattern)) {
            simpleDateFormat = sdfMap.get(pattern).get();
        } else {
            ThreadLocal<SimpleDateFormat> objectThreadLocal = new ThreadLocal<SimpleDateFormat>() {
                @Override
                protected SimpleDateFormat initialValue() {
                    return new SimpleDateFormat(pattern);
                }
            };
            sdfMap.put(pattern, objectThreadLocal);
            simpleDateFormat = objectThreadLocal.get();
        }
        return simpleDateFormat;
    }

    private enum FormatPattern {
        /**
         * yyyy-MM-dd HH:mm:ss
         */
        FULL("yyyy-MM-dd HH:mm:ss"),
        /**
         * yyyy-MM-dd
         */
        DATE("yyyy-MM-dd"),
        /**
         * HH:mm:ss
         */
        TIME("HH:mm:ss"),
        /**
         * HH:mm
         */
        HOUR("HH:mm");

        private String value;

        private FormatPattern(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }
    }

    public static SimpleDateFormat getFullFormat() {
        return getDateFormat(FormatPattern.FULL.value);
    }

    public static SimpleDateFormat getDateFormat() {
        return getDateFormat(FormatPattern.DATE.value);
    }

    public static SimpleDateFormat getTimeFormat() {
        return getDateFormat(FormatPattern.TIME.value);
    }

    public static SimpleDateFormat getHourFormat() {
        return getDateFormat(FormatPattern.HOUR.value);
    }

    public static String format_yyyy_MM_dd() {
        return getDateFormat().format(new Date());
    }

    public static String format_yyyy_MM_dd_HH_mm() {
        return getFullFormat().format(new Date());
    }
}
