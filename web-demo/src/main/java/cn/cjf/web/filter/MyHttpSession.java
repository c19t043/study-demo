package cn.cjf.web.filter;

import cn.cjf.web.util.RedisPoolUtil;
import com.alibaba.fastjson.JSONObject;

import javax.servlet.ServletContext;
import javax.servlet.http.*;
import java.util.Enumeration;
import java.util.List;
import java.util.UUID;

public class MyHttpSession implements HttpSession {

    HttpServletRequest request;
    HttpServletResponse response;

    private ThreadLocal<String> local = new ThreadLocal<String>();

    public MyHttpSession(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    public long getCreationTime() {
        return 0;
    }

    public String getId() {
        return null;
    }

    public long getLastAccessedTime() {
        return 0;
    }

    public ServletContext getServletContext() {
        return null;
    }

    public void setMaxInactiveInterval(int i) {

    }

    public int getMaxInactiveInterval() {
        return 0;
    }

    public HttpSessionContext getSessionContext() {
        return null;
    }

    public Object getAttribute(String key) {
         /*
           判断用户浏览器有没有把cookie里面的sessionId传过来
           如果没有sessionId，则表示是第一次请求，第一次请求后，后端生成一个跟用户绑定的唯一sessionId
        */
        String sessionId = getSessionIdFromCookie();

        List<String> list = RedisPoolUtil.lrang(sessionId);

        if (null != list) {
            return getValueByKey(list, key);
        }

        return null;
    }

    public Object getValueByKey(List<String> list, String key) {
        for (String s : list) {
            JSONObject jo = JSONObject.parseObject(s);
            if (jo.containsKey(key)) {
                return jo.get(key);
            }
        }

        return null;
    }

    public Object getValue(String s) {
        return null;
    }

    public Enumeration<String> getAttributeNames() {
        return null;
    }

    public String[] getValueNames() {
        return new String[0];
    }

    // 向redis设置缓存信息
    public void setAttribute(String key, Object value) {
        /*
           判断用户浏览器有没有把cookie里面的sessionId传过来
           如果没有sessionId，则表示是第一次请求，第一次请求后，后端生成一个跟用户绑定的唯一sessionId
        */
        String sessionId = getSessionIdFromCookie();

        if (null == sessionId) {
            sessionId = local.get();
            if (null == sessionId) {
                sessionId = "cjf--" + UUID.randomUUID();
                local.set(sessionId);
            }
        }

        JSONObject jo = new JSONObject();
        jo.put(key, value);
        RedisPoolUtil.lpush(sessionId, jo.toJSONString());

        writeCookis(sessionId);
    }

    private void writeCookis(String sessionId) {
        Cookie cookie = new Cookie("sessionId", sessionId);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    private String getSessionIdFromCookie() {
        Cookie[] cookies = request.getCookies();

        if (null != cookies) {
            for (Cookie cookie : cookies) {
                if ("sessionId".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }

        return null;
    }

    public void putValue(String s, Object o) {

    }

    public void removeAttribute(String s) {

    }

    public void removeValue(String s) {

    }

    public void invalidate() {

    }

    public boolean isNew() {
        return false;
    }
}
