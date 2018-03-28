package com.dg11185.hnyz.util;

import com.dg11185.hnyz.common.exception.AesException;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * xml工具类
 */
public class XmlUtil {


    private XmlUtil(){

    }

	/**
	 * 扩展xstream使其支持CDATA
	 */
	private static final XStream xstream = new XStream(new XppDriver() {
		public HierarchicalStreamWriter createWriter(Writer out) {
			return new PrettyPrintWriter(out) {
				// 对所有xml节点的转换都增加CDATA标记
				boolean cdata = true;

				@SuppressWarnings("unchecked")
				public void startNode(String name, Class clazz) {
					super.startNode(name, clazz);
				}

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

	/**
	 * 返回这个xstream
	 */
	public static XStream getXstream(){
		return xstream;
	}

	/**
	 * java 转换成xml
	 * @Title: toXml
	 * @param obj 对象实例
	 * @return String xml字符串
	 */
	public static String toXml(Object obj){
		xstream.processAnnotations(obj.getClass()); //通过注解方式的，一定要有这句话
		return xstream.toXML(obj);
	}

	/**
	 *  将传入xml文本转换成Java对象
	 * @Title: toBean
	 * @param xmlStr
	 * @param cls  xml对应的class类
	 * @return T   xml对应的class类的实例对象
	 */
	public static <T> T  toBean(String xmlStr,Class<T> cls){
		xstream.processAnnotations(cls);
		T obj=(T)xstream.fromXML(xmlStr);
		return obj;
	}



	/**
	 * 生成xml消息,微信加解密时候使用,其他无
	 * @param encrypt 加密后的消息密文
	 * @param signature 安全签名
	 * @param timestamp 时间戳
	 * @param nonce 随机字符串
	 * @return 生成的xml字符串
	 */
	public static String generate(String encrypt, String signature, String timestamp, String nonce) {

		String format = "<xml>\n" + "<Encrypt><![CDATA[%1$s]]></Encrypt>\n"
				+ "<MsgSignature><![CDATA[%2$s]]></MsgSignature>\n"
				+ "<TimeStamp>%3$s</TimeStamp>\n" + "<Nonce><![CDATA[%4$s]]></Nonce>\n" + "</xml>";
		return String.format(format, encrypt, signature, timestamp, nonce);
	}



	/**
	 * 提取出xml数据包中的加密消息,微信加解密使用,其他无
	 * @param xmltext 待提取的xml字符串
	 * @param isEvent 是否是服务器事件推送
	 * @return 提取出的加密消息字符串
	 * @throws AesException
	 */
	public static Object[] extract(String xmltext,boolean isEvent) throws AesException {
		Object[] result = new Object[3];
		try {

			Document document = DocumentHelper.parseText(xmltext);
			Element root = document.getRootElement();
			Element node1 = root.element("Encrypt");
			//有可能是明文的,未加密的,这时候直接返回
			if(node1 == null){
				return null;
			}
			Element node2 = null;
			if(isEvent){
				node2 = root.element("AppId");
			}else{
				node2 = root.element("ToUserName");
			}
			result[0] = 0;
			result[1] = node1.getTextTrim();
			result[2] = node2.getTextTrim();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			throw new AesException(AesException.ParseXmlError);
		}
	}


	/**
	 * 讲xml转为map存储
	 * @param xml
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> parseXml(String xml) throws Exception {
		// 将解析结果存储在HashMap中
		Map<String, String> map = new HashMap<String, String>();
		Document document = DocumentHelper.parseText(xml);
		// 得到xml根元素
		Element root = document.getRootElement();
		// 得到根元素的所有子节点
		List<Element> elementList = root.elements();
		// 遍历所有子节点
		for (Element e : elementList)
			map.put(e.getName(), e.getText());
		return map;
	}


	public static InputStream getStringStream(String sInputString) throws UnsupportedEncodingException {
		ByteArrayInputStream tInputStringStream = null;
		if (sInputString != null && !sInputString.trim().equals("")) {
			tInputStringStream = new ByteArrayInputStream(sInputString.getBytes("UTF-8"));
		}
		return tInputStringStream;
	}

	public static Object getObjectFromXML(String xml, Class tClass) {
		//将从API返回的XML数据映射到Java对象
		XStream xStreamForResponseData = new XStream();
		xStreamForResponseData.alias("xml", tClass);
		xStreamForResponseData.ignoreUnknownElements();//暂时忽略掉一些新增的字段
		return xStreamForResponseData.fromXML(xml);
	}


}
