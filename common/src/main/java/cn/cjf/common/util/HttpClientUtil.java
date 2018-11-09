package cn.cjf.common.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class HttpClientUtil {
    private static final Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);
    /**
     * 发送Get请求到url，获得response的json实体
     * @param url
     * @return
     * @throws IOException
     */
    public static JSONObject doGetUrl(String url) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response;
        String result = null;
        try {
            response = httpclient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(entity, "UTF-8");
            httpclient.close();
        } catch (IOException e) {
            logger.error("执行GET请求发生错误!", e);
        }
        return JSONObject.parseObject(result);
    }

    /**
     * 发送post请求
     * @param url
     * @param param
     * @return
     */
    public static JSONObject doPostUrl(String url, String param) {
        final String CONTENT_TYPE_TEXT_JSON = "application/json";
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        HttpResponse response;
        String result = null;
        try {
            StringEntity stringEntity = new StringEntity(param);
            stringEntity.setContentType(CONTENT_TYPE_TEXT_JSON);
            stringEntity.setContentEncoding("UTF-8");
            httpPost.setEntity(stringEntity);
            response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(entity, "UTF-8");
            httpClient.close();
        } catch (IOException e) {
            logger.error("执行POST请求发生错误!", e);
        }
        return JSONObject.parseObject(result);
    }
    /**
     * https post request
     * @param url
     * @param map
     * @param charset
     * @return
     */
    public static String doPost(String url, Map<String, String> map, String charset) {
        HttpClient httpClient;
        HttpPost httpPost;
        String result = null;
        try {
            httpClient = new SSLClient();
            httpPost = new HttpPost(url);
            //设置参数
            List<NameValuePair> list = new ArrayList<>();
            //参数key值迭代器
            if (!CollectionUtils.isEmpty(map)) {
                Iterator iterator = map.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry<String, String> elem = (Map.Entry<String, String>) iterator.next();
                    list.add(new BasicNameValuePair(elem.getKey(), elem.getValue()));
                }
                if (list.size() > 0) {
                    UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, charset);
                    httpPost.setEntity(entity);
                }
            }
            HttpResponse response = httpClient.execute(httpPost);
            if (response != null) {
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    result = EntityUtils.toString(resEntity, charset);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    /**
     * fashttps xml参数的请求
     * @param url
     * @param param
     * @return
     */
    public static String doPostHttpsXMLParam(String url, String param) {
        HttpClient httpClient;
        HttpPost httpPost;
        String result = null;
        try {
            httpClient = new SSLClient();
            httpPost = new HttpPost(url);
            if (!StringUtils.isEmpty(param)) {
                //设置参数
                StringEntity entity = new StringEntity(
                        param, "text/xml", "UTF-8");
                httpPost.setEntity(entity);
            }
            HttpResponse response = httpClient.execute(httpPost);
            if (response != null) {
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    result = EntityUtils.toString(resEntity, "UTF-8");
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }
}
