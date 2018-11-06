package cn.cjf.utils;


import java.util.Date;
import java.util.Random;

public class StringUtil {
    /**
     * 创建UUID
     */
    public static synchronized String makeUUID() {
        StringBuffer s = new StringBuffer(DateUtil.DateToString(new Date(),DateStyle.YYYYMMDDHHMMSS));
        return s.append((new Random().nextInt(900) + 100)).toString();
    }

    public static void main(String[] args) {
        System.out.println(StringUtil.makeUUID());
    }
}
