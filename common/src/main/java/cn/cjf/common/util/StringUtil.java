package cn.cjf.common.util;


import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Date;
import java.util.Random;

public class StringUtil {
    /**
     * 创建UUID
     */
    public static String makeUUID() {
        StringBuffer s = new StringBuffer(DateFormatUtils.format(new Date(),"yyyyMMddHHmmss"));
        return s.append((new Random().nextInt(900) + 100)).toString();
    }

    public static void main(String[] args) {
        System.out.println(StringUtil.makeUUID());
    }
}
