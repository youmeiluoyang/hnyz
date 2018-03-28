package com.dg11185.hnyz.util.wx;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;


/**
 * User: rizenguo
 * Date: 2014/10/29
 * Time: 15:23
 */
public class WXPaySignUtil {
    private final static Log logger = LogFactory.getLog(WXPaySignUtil.class);
	
    /**
     * 签名算法
     * @param o 要参与签名的数据对象
     * @param appSecret 要参与签名的密钥
     * @return 签名
     * @throws IllegalAccessException
     */
    public static String getSign(Object o, String appSecret) throws IllegalAccessException {
        ArrayList<String> list = new ArrayList<String>();
        Class cls = o.getClass();
        Field[] fields = cls.getDeclaredFields();
        for (Field f : fields) {
            f.setAccessible(true);
            if (f.get(o) != null && f.get(o) != "") {
                list.add(f.getName() + "=" + f.get(o) + "&");
            }
        }
        int size = list.size();
        String [] arrayToSort = list.toArray(new String[size]);
        Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < size; i ++) {
            sb.append(arrayToSort[i]);
        }
        String result = sb.toString();
        result += "key=" + appSecret;
        logger.info("Sign Before MD5:" + result);
        result = MD5.MD5Encode(result).toUpperCase();
        logger.info("Sign Result:" + result);
        return result;
    }

    public static String getSign(Map<String,Object> map, String appSecret){
        ArrayList<String> list = new ArrayList<String>();
        for(Map.Entry<String,Object> entry:map.entrySet()){
            if(entry.getValue()!=""){
                list.add(entry.getKey() + "=" + entry.getValue() + "&");
            }
        }
        int size = list.size();
        String [] arrayToSort = list.toArray(new String[size]);
        Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < size; i ++) {
            sb.append(arrayToSort[i]);
        }
        String result = sb.toString();
        result += "key=" + appSecret;
        logger.info("Sign Before MD5:" + result);
        result = MD5.MD5Encode(result).toUpperCase();
        logger.info("Sign Result:" + result);
        return result;
    }

    /**
     * 从API返回的XML数据里面重新计算一次签名
     * @param responseString API返回的XML数据
     * @param appSecret 签名密钥
     * @return 新鲜出炉的签名
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     */
    public static String getSignFromResponseString(String responseString, String appSecret) throws IOException,
            SAXException, ParserConfigurationException {
        Map<String,Object> map = XMLParser.getMapFromXML(responseString);
        //清掉返回数据对象里面的Sign数据（不能把这个数据也加进去进行签名），然后用签名算法进行签名
        map.put("sign","");
        //将API返回的数据根据用签名算法进行计算新的签名，用来跟API返回的签名进行比较
        return WXPaySignUtil.getSign(map, appSecret);
    }

    /**
     * 检验API返回的数据里面的签名是否合法，避免数据在传输的过程中被第三方篡改
     *
     * @param responseString API返回的XML数据字符串
     * @param appSecret 签名密钥
     * @return API签名是否合法
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     */
    public static boolean checkIsSignValidFromResponseString(String responseString,  String appSecret)
            throws ParserConfigurationException, IOException, SAXException {
    	long timestamp = System.currentTimeMillis();
        Map<String,Object> map = XMLParser.getMapFromXML(responseString);
        logger.info("timestamp="+timestamp+"，API返回的数据："+map.toString());

        String signFromAPIResponse = map.get("sign").toString();
        if(signFromAPIResponse=="" || signFromAPIResponse == null){
        	logger.info("timestamp="+timestamp+"，API返回的数据签名数据不存在，有可能被第三方篡改!!!");
            return false;
        }
        logger.info("timestamp="+timestamp+"，服务器回包里面的签名是:" + signFromAPIResponse);
        //清掉返回数据对象里面的Sign数据（不能把这个数据也加进去进行签名），然后用签名算法进行签名
        map.put("sign","");
        //将API返回的数据根据用签名算法进行计算新的签名，用来跟API返回的签名进行比较
        String signForAPIResponse = WXPaySignUtil.getSign(map, appSecret);

        if(!signForAPIResponse.equals(signFromAPIResponse)){
            //签名验不过，表示这个API返回的数据有可能已经被篡改了
        	logger.info("timestamp="+timestamp+"，API返回的数据签名验证不通过，有可能被第三方篡改!!!");
            return false;
        }
        logger.info("timestamp="+timestamp+"，恭喜，API返回的数据签名验证通过!!!");
        return true;
    }

}
