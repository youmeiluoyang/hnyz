package com.dg11185.hnyz.util.wx;

import com.alibaba.fastjson.JSONObject;
import com.dg11185.hnyz.bean.common.wx.Article;
import com.dg11185.hnyz.bean.common.wx.NewsMessage;
import com.dg11185.hnyz.bean.common.wx.RedPack;
import com.dg11185.hnyz.common.constant.WXConstant;
import com.dg11185.hnyz.util.EncryptUtil;
import com.dg11185.hnyz.util.XmlUtil;
import com.dg11185.util.common.ReflectionUtil;
import com.thoughtworks.xstream.XStream;

import java.lang.reflect.Field;
import java.util.*;

/**
 * Created by xiesp on 2016/12/14.
 */
public class WxMessageUtil {

    private static  final XStream xstream = XmlUtil.getXstream();


    /**
     * 微信事件推送类型
     */
    public enum WxEventType{
        COMPONENT_VERIFY_TICKET("component_verify_ticket"),//ticket推送
        UNAUTHORIZED("unauthorized"),//关闭授权
        UPDATEAUTHORIZED("updateauthorized"),//更新授权
        AUTHORIZED("authorized");//开始授权
        private String name;
        private WxEventType(String name){
            this.name = name;
        }
        public String getName(){
            return  this.name;
        }
    }

    /**
     * 微信消息推送类型
     */
    public enum WxPushType{
        EVENT("event"),// 请求消息类型：事件推送
        TEXT("text"),// 请求消息类型：文本
        IMAGE("image"),// 请求消息类型：图片
        VOICE("voice"),// 请求消息类型：语音
        VIDEO("video"),// 请求消息类型：视频
        SHORT_VIDEO("shortvideo"),// 请求消息类型：小视频
        LOCATION("location"),// 请求消息类型：地理位置
        LINK("link");// 请求消息类型：链接
        private String name;
        private WxPushType(String name){
            this.name = name;
        }
        public String getName(){
            return  this.name;
        }
    }


    /**
     * 微信推送事件消息时候的类型
     */
    public enum WxPushEventType{
        CLICK("CLICK"),  // 事件类型：CLICK(自定义菜单)
        VIEW("VIEW"),    // 事件类型：VIEW(自定义菜单)
        UNSUBSCRIBE("UNSUBSCRIBE"),    // 事件类型：unsubscribe(取消订阅)
        SCAN("scan"),    // 事件类型：scan(用户已关注时的扫描带参数二维码)
        LOCATION("LOCATION"),// 事件类型：LOCATION(上报地理位置)
        SUBSCRIBE("SUBSCRIBE"),// 事件类型：subscribe(订阅)
        MASSSENDJOBFINISH("MASSSENDJOBFINISH");
        private String name;
        private WxPushEventType(String name){
            this.name = name;
        }
        public String getName(){
            return  this.name;
        }
    }


    /**
     * 返回为微信的消息类型
     */
    public enum WxRetMessageType{
        TEXT("text"),    // 响应消息类型：文本
        IMAGE("image"),    // 响应消息类型：图片
        VOICE("voice"),  // 响应消息类型：语音
        VIDEO("video"),  // 响应消息类型：视频
        MUSIC("music"),    // 响应消息类型：音乐
        NEWS("news");  // 响应消息类型：图文
        private String name;
        private WxRetMessageType(String name){
            this.name = name;
        }
        public String getName(){
            return  this.name;
        }
    }


    /**
     * 微信图文消息对象转换成xml
     *
     * @param newsMessage 图文消息对象
     * @return xml
     */
    public static String messageToXml(NewsMessage newsMessage) {
        xstream.alias("xml", NewsMessage.class);
        xstream.alias("item", Article.class);
        return xstream.toXML(newsMessage);
    }


    /**
     * 微信红包对象转换成xml
     * @return xml
     */
    public static String messageToXml(RedPack redPack) {
        xstream.alias("xml", RedPack.class);
        return xstream.toXML(redPack);
    }


    /**
     * 生成微信加密需要的随机32位字符串
     * @return
     */
    public static String getNonceStr(){
        Random random = new Random();
        char[] chars = new char[32];
        for(int i = 0;i < 32;i++){
            int type = random.nextInt(10);
            //生成0-9
            if(type%2==0){
                int num = random.nextInt(10) + 48;
                chars[i] = (char)(num);
            }else{
                int num = random.nextInt(26) + 65;
                chars[i] = (char)num;
            }
        }
        return new String(chars);
    }


    /**
     * 微信AP加密
     * @return
     */
    public static String wxApiSing(Object object,String key){
        String result = "";
        List<Field> list =  ReflectionUtil.getNotNullField(object);
        Map<String,String> sortMap = new TreeMap<>();
        try {
            for(int i =0,len = list.size();i<len;i++){
                sortMap.put(list.get(i).getName(), (String) list.get(i).get(object));
            }
            StringBuilder sb = new StringBuilder();
            for(Map.Entry<String,String> entry:sortMap.entrySet()){
                sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
            }
            sb.append("key=").append(key);
            String temp = EncryptUtil.MD5Digest(sb.toString());
            result = temp.toUpperCase();
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return result;

    }


    /**
     * 微信JSAPI签名
     * @return
     */
    public static Map<String,String> wxJSApiSign(String url,String jsApiTicket){
        long timestamp = (new Date()).getTime() / 1000;
        String nonceStr = WxMessageUtil.getNonceStr();
        String result = "";
        Map<String,String> sortMap = new TreeMap<>();
        try {
            sortMap.put("noncestr",nonceStr);
            sortMap.put("jsapi_ticket",jsApiTicket);
            sortMap.put("timestamp",timestamp+"");
            sortMap.put("url",url);
            StringBuilder sb = new StringBuilder();
            for(Map.Entry<String,String> entry:sortMap.entrySet()){
                sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
            }
            String finalRe = sb.substring(0,sb.length() -1);
            String sha1 = EncryptUtil.sha1(finalRe);
            sortMap.put("signature",sha1);
            sortMap.put("appid", WXConstant.APP_ID);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println(JSONObject.toJSONString(sortMap));
        return sortMap;

    }

}
