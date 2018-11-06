package cn.cjf.wx.utils;

public class MyStringUtil {
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
