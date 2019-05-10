package cn.cjf.pay.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class SignUtil {

    /**
     * 获取请求签名
     */
    public static String getSign(Map<String, Object> params, String appKey) {
        List<String> keys = new ArrayList<>(params.keySet());
        Collections.sort(keys);
        StringBuilder sb = new StringBuilder();
        for (String key : keys) {
            sb.append(key.toLowerCase() + "=");
            sb.append(params.get(key) + "&");
        }
        sb.deleteCharAt(sb.length() - 1);
        return MD5Util.getMD5String(sb.toString());
    }
}
