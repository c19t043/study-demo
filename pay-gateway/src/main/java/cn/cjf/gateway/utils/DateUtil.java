package cn.cjf.gateway.utils;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author wangwei
 * @date 2018/9/14 0014 18:21.
 */
public class DateUtil {

    private static DateTimeFormatter timeformatter = DateTimeFormatter.ofPattern("HH:mm");
    private static DateTimeFormatter dateformatter = DateTimeFormatter.ofPattern("YYYY.MM.dd");

    public static String date2TimeStr(Date date){
        Instant instant = date.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
        LocalTime localTime = localDateTime.toLocalTime();
        return localTime.format(timeformatter);
    }

    public static String getNowDate(){
        LocalDate localDate = LocalDate.now();
        return localDate.format(dateformatter);
    }
}
