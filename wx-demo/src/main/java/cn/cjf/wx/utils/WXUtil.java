package cn.cjf.wx.utils;

import cn.cjf.common.util.MD5Util;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.*;

public class WXUtil {

	/**
	 * @return
	 */
	public static String getNonceStr() {
		return UUID.randomUUID().toString().replaceAll("-", "").substring(0, 32);
	}

	/**
	 * 获得随机字符串utf8编码
	 * @return
	 */
	public static String getNonceStrUTF8() {
		Random random = new Random();
		return MD5Util.MD5Encode( System.currentTimeMillis() + String.valueOf(random.nextInt(10000)), "UTF-8");
	}

	/**
	 * 获得时间戳
	 * @return
	 */
	public static String getTimeStamp() {
		return String.valueOf(System.currentTimeMillis());
	}

	/**
	 * 解析xml成map
	 * @param xml
	 * @return
	 * @throws Exception
	 */
	public static Map<String, String> parseXml(String xml) throws Exception {
		Map<String, String> map = new HashMap<>();
		Document document = DocumentHelper.parseText(xml);
		Element root = document.getRootElement();
		List<Element> elementList = root.elements();
		for (Element e : elementList) {
			map.put(e.getName(), e.getText());
		}
		return map;
	}

	/**
	 * 根据request请求解析xml数据流
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public static Map<String, String> parseXml(HttpServletRequest request) throws Exception {
		Map<String, String> map = new HashMap<>();
		InputStream inputStream = request.getInputStream();
		SAXReader reader = new SAXReader();
		Document document = reader.read(inputStream);
		Element root = document.getRootElement();
		List<Element> elementList = root.elements();
		for (Element e : elementList) {
			map.put(e.getName(), e.getText());
		}
		inputStream.close();
		return map;
	}
}
