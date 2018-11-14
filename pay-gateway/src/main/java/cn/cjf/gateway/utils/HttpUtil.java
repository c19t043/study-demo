package cn.cjf.gateway.utils;

import org.apache.commons.codec.CharEncoding;
import org.apache.commons.codec.Charsets;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author wangwei
 * @date 2018/9/19 0019 14:58.
 */
public final class HttpUtil {

    private static final int CONNECT_TIMEOUT = 15000;

    private static final int READ_TIMEOUT = 60000;

    private static final int CONNECT_SUCC_CODE = 200;

    public static void main(String[] args) {
        String url = "http://192.168.2.108:8080/amclienti/api/manager/cLogin";
        String str = HttpUtil.doPost(url, "loginName=xxx&password=123456");
        System.out.println(str);
    }

    private HttpUtil() {
    }

    /**
     * @param httpurl 请求地址
     * @return
     * @Description get请求
     * @Author wangwei
     * @Date 2018/9/19 0019 下午 2:59
     */
    public static String doGet(String httpurl) {
        HttpURLConnection connection = null;
        String result = null;
        // 返回结果字符串
        try {
            // 创建远程url连接对象
            URL url = new URL(httpurl);
            // 通过远程url连接对象打开一个连接，强转成httpURLConnection类
            connection = (HttpURLConnection) url.openConnection();
            // 设置连接方式：get
            connection.setRequestMethod("GET");
            // 设置连接主机服务器的超时时间：15000毫秒
            connection.setConnectTimeout(CONNECT_TIMEOUT);
            // 设置读取远程返回的数据时间：60000毫秒
            connection.setReadTimeout(READ_TIMEOUT);
            // 发送请求
            connection.connect();
            // 通过connection连接，获取输入流
            try (InputStream is = connection.getInputStream();
                 BufferedReader br = new BufferedReader(new InputStreamReader(is, Charsets.UTF_8))) {
                if (connection.getResponseCode() == CONNECT_SUCC_CODE) {
                    // 封装输入流is，并指定字符集
                    // 存放数据
                    StringBuffer sbf = new StringBuffer();
                    String temp;
                    while ((temp = br.readLine()) != null) {
                        sbf.append(temp);
                    }
                    result = sbf.toString();
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭远程连接
            if (connection != null) {
                connection.disconnect();
            }
        }
        return result;
    }

    /**
     * @param httpUrl 请求地址
     * @param param   参数
     */
    public static String doPost(String httpUrl, String param) {
        HttpURLConnection connection = null;
        String result = null;
        try {
            URL url = new URL(httpUrl);
            // 通过远程url连接对象打开连接
            connection = (HttpURLConnection) url.openConnection();
            // 设置连接请求方式
            connection.setRequestMethod("POST");
            // 设置连接主机服务器超时时间：15000毫秒
            connection.setConnectTimeout(CONNECT_TIMEOUT);
            // 设置读取主机服务器返回数据超时时间：60000毫秒
            connection.setReadTimeout(READ_TIMEOUT);
            // 默认值为：false，当向远程服务器传送数据/写数据时，需要设置为true
            connection.setDoOutput(true);
            // 默认值为：true，当前向远程服务读取数据时，设置为true，该参数可有可无
            connection.setDoInput(true);
            // 设置传入参数的格式:请求参数应该是 name1=value1&name2=value2 的形式。
            connection.setRequestProperty("Content-Type", "application/json");
            //设置请求编码
            connection.setRequestProperty("Charset", CharEncoding.UTF_8);
            // 通过连接对象获取一个输出流
            try (OutputStream os = connection.getOutputStream();
                 InputStream is = connection.getInputStream();
                 BufferedReader br = new BufferedReader(new InputStreamReader(is, Charsets.UTF_8))) {
                // 通过输出流对象将参数写出去/传输出去,它是通过字节数组写出的
                os.write(param.getBytes());
                // 通过连接对象获取一个输入流，向远程读取
                if (connection.getResponseCode() == CONNECT_SUCC_CODE) {
                    // 对输入流对象进行包装:charset根据工作项目组的要求来设置
                    StringBuffer sbf = new StringBuffer();
                    String temp;
                    // 循环遍历一行一行读取数据
                    while ((temp = br.readLine()) != null) {
                        sbf.append(temp);
                    }
                    result = sbf.toString();
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭远程连接
            if (connection != null) {
                connection.disconnect();
            }
        }
        return result;
    }
}
