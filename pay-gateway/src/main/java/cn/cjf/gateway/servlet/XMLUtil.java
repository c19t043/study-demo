package cn.cjf.gateway.servlet;


import cn.cjf.gateway.config.ConstantConfig;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XMLUtil {
	/**
	 * 解析xml,返回第一级元素键值对。如果第一级元素有子节点，则此节点的值是子节点的xml数据。
	 * @param strxml
	 */
	public static Map<String,String> doXMLParse(String strxml) throws DocumentException {
		Map<String, String> map = new HashMap<>(ConstantConfig.MAP_INITIAL_CAPACITY);
		Document document = DocumentHelper.parseText(strxml);
		Element root = document.getRootElement();
		List<Element> elementList = root.elements();
		for (Element e : elementList) {
			map.put(e.getName(), e.getText());
		}
		return map;
	}
}