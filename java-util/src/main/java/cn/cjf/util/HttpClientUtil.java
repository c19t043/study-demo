package cn.cjf.util;


import org.apache.commons.lang3.StringUtils;
import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * http  工具类
 *
 */
public class HttpClientUtil {
    private static Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);
    private static final CloseableHttpClient httpclient = HttpClients.createDefault();

    /**
     * 发送HttpGet请求
     * @param url
     * @return
     */
    public static String sendGet(String url) {

        HttpGet httpget = new HttpGet(url);
        CloseableHttpResponse response = null;
        try {
            response = httpclient.execute(httpget);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        String result = null;
        try {
            if(null == response){
                return null;
            }
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                result = EntityUtils.toString(entity);
            }
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 发送HttpPost请求，参数为map
     * @param url
     * @param map
     * @return
     */
    public static String sendPost(String url, Map<String, String> map) {
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            formparams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, Consts.UTF_8);
        HttpPost httppost = new HttpPost(url);
        httppost.setEntity(entity);
        CloseableHttpResponse response = null;
        try {
            response = httpclient.execute(httppost);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String result = null;
        try {
            if(null == response){
                return null;
            }
            HttpEntity entity1 = response.getEntity();
            result = EntityUtils.toString(entity1);
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }finally {
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 发送不带参数的HttpPost请求
     * @param url
     * @return
     */
    public static String sendPost(String url) {
        HttpPost httppost = new HttpPost(url);
        CloseableHttpResponse response = null;
        try {
            response = httpclient.execute(httppost);
        } catch (IOException e) {
            e.printStackTrace();
        }
        HttpEntity entity = response.getEntity();
        String result = null;
        try {
            result = EntityUtils.toString(entity);
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Get请求
     *
     * @param url
     * @param paramMap
     * @return
     */
    public static String get(String url, Map<String, Object> paramMap) {
        try {
            // Get请求
            HttpGet httpget = new HttpGet(url);
            HttpEntity requestEntity = wrapEntity(paramMap, "UTF-8", null);
            String str = EntityUtils.toString(requestEntity);
            String uri = httpget.getURI().toString() + "?" + str;
            logger.info("请求路径：====" + uri);
            httpget.setURI(new URI(uri));

            // 设置请求和传输超时时间
            RequestConfig requestConfig = RequestConfig.custom()
                    .setSocketTimeout(2000)
                    .setConnectTimeout(2000).build();
            httpget.setConfig(requestConfig);

            // 发送请求
            return execute(httpget);
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Get请求
     *
     * @param url
     * @param paramMap
     * @param timeout
     * @return
     */
    public static String get(String url, Map<String, Object> paramMap, int timeout) {
        try {
            // Get请求
            HttpGet httpget = new HttpGet(url);
            HttpEntity requestEntity = wrapEntity(paramMap, "UTF-8", null);
            String str = EntityUtils.toString(requestEntity);
            httpget.setURI(new URI(httpget.getURI().toString() + "?" + str));

            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(timeout).setConnectTimeout(timeout)
                    .build();// 设置请求和传输超时时间
            httpget.setConfig(requestConfig);

            // 发送请求
            return execute(httpget);
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * Post请求
     */
    public static String post(String url, Map<String, Object> paramMap) {
        try {
            // Post请求
            HttpPost httppost = new HttpPost(url);
            HttpEntity requestEntity = wrapEntity(paramMap, "UTF-8", null);
            httppost.setEntity(requestEntity);
            // 发送请求
            return execute(httppost);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String post(String url, Map<String, Object> paramMap, String contentType) {
        try {
            // Post请求
            HttpPost httppost = new HttpPost(url);
            HttpEntity requestEntity = wrapEntity(paramMap, "UTF-8", contentType);
            httppost.setEntity(requestEntity);
            // 发送请求
            return execute(httppost);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * =================== Private Method ===================
     **/

    private static HttpEntity wrapEntity(Map<String, Object> paramMap, String encoding, String contentType)
            throws UnsupportedEncodingException {
        List<NameValuePair> params = map2Pair(paramMap);
        if (StringUtils.isBlank(encoding)) {
            encoding = "UTF-8";
        }
        UrlEncodedFormEntity requestEntity = new UrlEncodedFormEntity(params, encoding);
        if (StringUtils.isNotBlank(contentType)) {
            requestEntity.setContentType(contentType);
        }
        return requestEntity;
    }

    private static List<NameValuePair> map2Pair(Map<String, Object> paramMap) {
        List<NameValuePair> params = new ArrayList<>();
        if (paramMap != null) {
            for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
                if (entry.getValue() != null) {
                    params.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
                }
            }
        }
        return params;
    }

    private static String execute(HttpUriRequest request) {
        try {
            HttpClient client = HttpClientBuilder.create().build();
            HttpResponse httpresponse = client.execute(request);
            // 获取返回数据
            HttpEntity entity = httpresponse.getEntity();
            return EntityUtils.toString(entity, "UTF-8");
        } catch (Exception e) {
            if (StringUtils.isBlank(e.getMessage())) {
                logger.error("{}", e.getCause());
            } else {
                logger.error("{}", e.getMessage());
            }
        }
        return null;
    }


}