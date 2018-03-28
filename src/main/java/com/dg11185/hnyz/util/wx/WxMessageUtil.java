package com.dg11185.hnyz.util.wx;

import com.dg11185.hnyz.bean.common.wx.Article;
import com.dg11185.hnyz.bean.common.wx.NewsMessage;
import com.dg11185.hnyz.util.XmlUtil;
import com.thoughtworks.xstream.XStream;

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

}
