package cn.cjf.netty.utils;

import org.apache.commons.codec.Charsets;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author CJF
 */
public class HttpClientUtil {

    public static String doPost(String url, Map<String, String> map) {
        CloseableHttpClient httpClient;
        HttpPost httpPost;
        String result = null;
        try {
            CookieStore cookieStore = new BasicCookieStore();
            httpClient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
            httpPost = new HttpPost(url);
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> elem = iterator.next();
                list.add(new BasicNameValuePair(elem.getKey(), elem.getValue()));
            }
            if (list.size() > 0) {
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, Charsets.UTF_8);
                httpPost.setEntity(entity);
            }
            CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity entity = httpResponse.getEntity();

            result = EntityUtils.toString(entity, Charsets.UTF_8);

            String JSESSIONID = null;
            List<Cookie> cookies = cookieStore.getCookies();
            for (int i = 0; i < cookies.size(); i++) {
                if (cookies.get(i).getName().equals("JSESSIONID")) {
                    JSESSIONID = cookies.get(i).getValue();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    private static final CloseableHttpClient HTTP_CLIENT = HttpClients.createDefault();

    /**
     * 发送HttpGet请求
     *
     * @param url 请求地址
     */
    public static String sendGet(String url) {
        HttpGet httpget = new HttpGet(url);
        String result = null;
        try (CloseableHttpResponse response = HTTP_CLIENT.execute(httpget)) {
            if (null == response) {
                return null;
            }
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                result = EntityUtils.toString(entity);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 发送HttpPost请求，参数为map
     *
     * @param url 请求地址
     * @param map 参数
     */
    public static String sendPost(String url, Map<String, String> map) {
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            formparams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, Consts.UTF_8);
        HttpPost httppost = new HttpPost(url);
        httppost.setEntity(entity);

        String result = null;
        try (CloseableHttpResponse response = HTTP_CLIENT.execute(httppost)) {
            if (null == response) {
                return null;
            }
            HttpEntity entity1 = response.getEntity();
            result = EntityUtils.toString(entity1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 发送不带参数的HttpPost请求
     *
     * @param url 请求地址
     */
    public static String sendPost(String url) {
        HttpPost httppost = new HttpPost(url);
        String result = null;
        try (CloseableHttpResponse response = HTTP_CLIENT.execute(httppost)) {
            HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(entity);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
