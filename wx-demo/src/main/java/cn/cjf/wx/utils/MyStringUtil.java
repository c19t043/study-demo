package cn.cjf.wx.utils;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

public class MyStringUtil {
    private final static Logger LOG = LoggerFactory.getLogger("wx_MyStringUtil");

    public final static String STRING_CDATA_START = "<![CDATA[";
    public final static String STRING_CDATA_END = "]]>";

    /**
     * 获取字符串<![CDATA[公众号]]>中的值
     */
    public static String getValueFrom_CDATA(String data) {
        String result = null;
        if (data != null && data.contains(STRING_CDATA_START)) {
            result = data.substring(data.indexOf(STRING_CDATA_START) + STRING_CDATA_START.length(),
                    data.lastIndexOf(STRING_CDATA_END));
        }
        return result;
    }

    /**
     * 解析流中的消息为字符串
     */
    public static String parseFromIO_In(HttpServletRequest request) {
        try (InputStream inputStream = request.getInputStream()){
            return IOUtils.toString(inputStream, Charset.defaultCharset());
        } catch (IOException e) {
            LOG.error("从IO流中获取数据转为字符串失败");
        }
        return null;
    }

    /**
     * 组装成<![CDATA[公众号]]>格式值
     */
    public static String formatValue2_CDATA(String value) {
        return STRING_CDATA_START + value + STRING_CDATA_END;
    }

    public static void main(String[] args) {
//        System.out.println(getValueFrom_CDATA("<![CDATA[公众号]]>"));
        System.out.println(formatValue2_CDATA("公众号"));
    }
}
