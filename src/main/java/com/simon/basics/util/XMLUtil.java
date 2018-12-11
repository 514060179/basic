package com.simon.basics.util;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * xml工具类
 */
public class XMLUtil {

	/**
	 * 解析xml,返回第一级元素键值对。如果第一级元素有子节点，则此节点的值是子节点的xml数据。
	 * 
	 * @param strxml
	 * @return
	 * @throws JDOMException
	 * @throws IOException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map doXMLParse(String strxml) throws JDOMException,
			IOException {
		if (null == strxml || "".equals(strxml)) {
			return null;
		}
		Map m = new HashMap();
		InputStream in = String2Inputstream(strxml);
		SAXBuilder builder = new SAXBuilder();
		Document doc = builder.build(in);
		Element root = doc.getRootElement();
		List list = root.getChildren();
		Iterator it = list.iterator();
		while (it.hasNext()) {
			Element e = (Element) it.next();
			String k = e.getName();
			String v = "";
			List children = e.getChildren();
			if (children.isEmpty()) {
				v = e.getTextNormalize();
			} else {
				v = XMLUtil.getChildrenText(children);
			}

			m.put(k, v);
		}
		// 关闭流
		in.close();
		return m;
	}

	public static InputStream String2Inputstream(String str) throws UnsupportedEncodingException {
		return new ByteArrayInputStream(str.getBytes("utf-8"));
	}

	/**
	 * 获取子结点的xml
	 * 
	 * @param children
	 * @return String
	 */
	@SuppressWarnings({ "rawtypes"})
	public static String getChildrenText(List children) {
		StringBuffer sb = new StringBuffer();
		if (!children.isEmpty()) {
			Iterator it = children.iterator();
			while (it.hasNext()) {
				Element e = (Element) it.next();
				String name = e.getName();
				String value = e.getTextNormalize();
				List list = e.getChildren();
				sb.append("<" + name + ">");
				if (!list.isEmpty()) {
					sb.append(XMLUtil.getChildrenText(list));
				}
				sb.append(value);
				sb.append("</" + name + ">");
			}
		}

		return sb.toString();
	}

	/**
	 * 获取xml编码字符集
	 * 
	 * @param strxml
	 * @return
	 * @throws IOException
	 * @throws JDOMException
	 */
	public static String getXMLEncoding(String strxml) throws JDOMException,
			IOException {
		InputStream in = String2Inputstream(strxml);
		SAXBuilder builder = new SAXBuilder();
		Document doc = builder.build(in);
		in.close();
		return (String) doc.getProperty("encoding");
	}
	
	
	  /**
		 * @date
		 * @Description：返回给微信的参数
		 * @param return_code
		 *            返回编码
		 * @param return_msg
		 *            返回信息
		 * @return
		 */
		public static String setXML(String return_code, String return_msg) {
			return "<xml><return_code><![CDATA[" + return_code
					+ "]]></return_code><return_msg><![CDATA[" + return_msg
					+ "]]></return_msg></xml>";
		}

}
