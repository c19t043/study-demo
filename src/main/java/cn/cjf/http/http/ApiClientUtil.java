package cn.cjf.http.http;

import cn.cjf.http.ApiResult;
import com.alibaba.fastjson.JSONObject;
import io.netty.util.internal.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Map;

public abstract class ApiClientUtil {

    private static final Log logger = LogFactory.getLog(ApiClientUtil.class);

    public static ApiResult doPost(String method, Map<String, Object> params) throws Exception {
        String jsonStr = null;
        try {
            jsonStr = HttpclientPoolUtil.post(method, params);
            return JSONObject.parseObject(jsonStr, ApiResult.class);
        } catch (Exception e) {
            logger.error("############" + jsonStr, e);
            throw e;
        }
    }

    public static String doPostToString(String method, Map<String, Object> params) throws Exception {
        String jsonStr = null;
        try {
            jsonStr = HttpclientPoolUtil.post(method, params);
            return jsonStr;
        } catch (Exception e) {
            logger.error("############" + jsonStr, e);
            throw e;
        }
    }

    public static ApiResult doGet(String method, Map<String, Object> params) throws Exception {
        String jsonStr = null;
        try {
            jsonStr = HttpclientPoolUtil.get(method, params);
            return JSONObject.parseObject(jsonStr, ApiResult.class);
        } catch (Exception e) {
            logger.error("############" + jsonStr, e);
            throw e;
        }
    }

    public static String getApiUri(String host, String port, String method) {
        StringBuilder sb = null;
        if (host.contains("http")) {
            sb = new StringBuilder();
        } else {
            sb = new StringBuilder("http://");
        }
        sb.append(host);
        if (StringUtils.isNotEmpty(port) && !"80".equals(port)) {
            sb.append(":").append(port);
        }
        sb.append(method);
        return sb.toString();
    }
}
