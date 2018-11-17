package demo;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;
import net.sf.json.JSONObject;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.*;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 通用工具类
 *
 * @author rory.wu
 * @version 1.0
 * @since 2015年08月05日
 */
public class CommonUtil {

    private static Logger log = LoggerFactory.getLogger(CommonUtil.class);
    public static String createNonceStr() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 将字节数组转换为十六进制字符串
     *
     * @param byteArray
     * @return
     */
    public static String byteToStr(byte[] byteArray) {
        String strDigest = "";
        for (int i = 0; i < byteArray.length; i++) {
            strDigest += byteToHexStr(byteArray[i]);
        }
        return strDigest;
    }

    /**
     * 将字节转换为十六进制字符串
     *
     * @return
     */
    public static String byteToHexStr(byte bytes) {
        char[] Digit = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        char[] tempArr = new char[2];
        tempArr[0] = Digit[(bytes >>> 4) & 0X0F];
        tempArr[1] = Digit[bytes & 0X0F];

        String s = new String(tempArr);
        return s;
    }

    /**
     * 获取ip地址
     *
     * @param request
     * @return
     */
    public static String getIpAddr(HttpServletRequest request) {
        InetAddress addr = null;
        try {
            addr = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            return request.getRemoteAddr();
        }
        byte[] ipAddr = addr.getAddress();
        String ipAddrStr = "";
        for (int i = 0; i < ipAddr.length; i++) {
            if (i > 0) {
                ipAddrStr += ".";
            }
            ipAddrStr += ipAddr[i] & 0xFF;
        }
        return ipAddrStr;
    }
    /**
     * 扩展xstream使其支持CDATA
     */
    private static XStream xstream = new XStream(new XppDriver() {
        @Override
        public HierarchicalStreamWriter createWriter(Writer out) {
            return new PrettyPrintWriter(out) {
                //增加CDATA标记
                boolean cdata = true;

                @Override
                public void startNode(String name, Class clazz) {
                    super.startNode(name, clazz);
                }

                @Override
                protected void writeText(QuickWriter writer, String text) {
                    if (cdata) {
                        writer.write("<![CDATA[");
                        writer.write(text);
                        writer.write("]]>");
                    } else {
                        writer.write(text);
                    }
                }
            };
        }
    });

    public static String payInfoToXML(PayInfo pi) {
        xstream.alias("xml", pi.getClass());
        return xstream.toXML(pi);
    }
    public static JSONObject httpsRequestToJsonObject(String requestUrl, String requestMethod, String outputStr) {
        JSONObject jsonObject = null;
        try {
            StringBuffer buffer = httpsRequest(requestUrl, requestMethod, outputStr);
            jsonObject = JSONObject.fromObject(buffer.toString());
        } catch (ConnectException ce) {
            log.error("连接超时：" + ce.getMessage());
        } catch (Exception e) {
            log.error("https请求异常：" + e.getMessage());
        }
        return jsonObject;
    }

    @SuppressWarnings("unchecked")
    public static Map<String, String> parseXml(String xml) throws Exception {
        Map<String, String> map = new HashMap<String, String>();

        Document document = DocumentHelper.parseText(xml);
        Element root = document.getRootElement();
        List<Element> elementList = root.elements();

        for (Element e : elementList) {
            map.put(e.getName(), e.getText());
        }
        return map;
    }

    public static Map<String, String> httpsRequestToXML(String requestUrl, String requestMethod, String outputStr) {
        Map<String, String> result = new HashMap<>();
        try {
            StringBuffer buffer = httpsRequest(requestUrl, requestMethod, outputStr);
            result = parseXml(buffer.toString());
        } catch (ConnectException ce) {
            log.error("连接超时：" + ce.getMessage());
        } catch (Exception e) {
            log.error("https请求异常：" + e.getMessage());
        }
        return result;
    }

    private static StringBuffer httpsRequest(String requestUrl, String requestMethod, String output)
            throws NoSuchAlgorithmException, NoSuchProviderException, KeyManagementException, MalformedURLException,
            IOException, ProtocolException, UnsupportedEncodingException {

        URL url = new URL(requestUrl);
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();

        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setUseCaches(false);
        connection.setRequestMethod(requestMethod);
        if (null != output) {
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(output.getBytes("UTF-8"));
            outputStream.close();
        }

        // 从输入流读取返回内容
        InputStream inputStream = connection.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String str = null;
        StringBuffer buffer = new StringBuffer();
        while ((str = bufferedReader.readLine()) != null) {
            buffer.append(str);
        }

        bufferedReader.close();
        inputStreamReader.close();
        inputStream.close();
        inputStream = null;
        connection.disconnect();
        return buffer;
    }

    public static String createTimestamp() {
        return null;
    }
}