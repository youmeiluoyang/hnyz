package com.dg11185.hnyz.controller.api;

import com.dg11185.hnyz.common.exception.AppException;
import com.dg11185.hnyz.service.api.WeixinService;
import com.dg11185.hnyz.util.LogUtil;
import com.dg11185.hnyz.util.XmlUtil;
import com.dg11185.hnyz.util.wx.WxMessageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * 处理微信推送的相关消息
 * @author xiesp
 * @description
 * @date 2:32 PM  3/28/2018
 * @copyright 全国邮政电子商务运营中心
 */
@Controller
@RequestMapping("/wx/*")
public class WxController {

    private final static Logger log = LoggerFactory.getLogger(WxController.class);
    @Autowired
    private WeixinService weixinService;


    /**
     * 接收并处理不同微信公众号回调过来的消息
     */
    @RequestMapping(value = "callback.do")
    @ResponseBody
    public Object wxCallback(HttpServletRequest request, HttpServletResponse response){
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        //这是微信发过来的随机字符串
        String echostr = request.getParameter("echostr");
        //加密需要的参数
        String encrypt_type = request.getParameter("encrypt_type");
        String msg_signature = request.getParameter("msg_signature");
        String decryptedData = "";
        //默认返回success
        String respMessage = "success";
        boolean success = true;
        long start = System.currentTimeMillis();
        String postData = "";
        try{
            postData = this.getRequestData(request);
            //解密消息
            decryptedData = weixinService.decryptWxMessage(msg_signature,timestamp,nonce,postData);
            //转换为map对象好处理
            final Map<String,String> map = XmlUtil.parseXml(decryptedData);
            //开始业务处理流程
            String msgType = map.get("MsgType");
            //事件推送
            if(WxMessageUtil.WxPushType.EVENT.getName().equals(msgType)){

                String event = map.get("Event");
                // 自定义菜单点击
                if(WxMessageUtil.WxPushEventType.CLICK.getName().equals(event) ||
                        WxMessageUtil.WxPushEventType.VIEW.getName().equals(event)){

                }
                // 粉丝 关注
                else if (WxMessageUtil.WxPushEventType.SUBSCRIBE.getName().equalsIgnoreCase(event)) {

                }
                // 粉丝 取消关注
                else if (WxMessageUtil.WxPushEventType.UNSUBSCRIBE.getName().equalsIgnoreCase(event)) {

                }
                // 群发消息结果
                else if (WxMessageUtil.WxPushEventType.MASSSENDJOBFINISH.getName().equalsIgnoreCase(event)) {

                }
            }
            //普通消息推送
            else if(WxMessageUtil.WxPushType.TEXT.getName().equals(msgType)){
                respMessage = "success";
            }
        }catch (Exception e){
            log.error("[微信推送-消息处理]处理微信公众号推送消息失败!异常信息:{},对应推送消息==>\n{}", LogUtil.getTrace(e),decryptedData);
            success=false;
        }
        //如果处理过程正确
        if(success){
            //打印出日志
            long end = System.currentTimeMillis();
            log.info("[微信推送-消息处理]将要返回给微信的消息是:{},一共耗时:{},对应推送消息==>\n{}",respMessage,end-start,decryptedData);
        }
        //返回结果给微信
        return  respMessage;
    }




    /**
     * 获取微信推送数据时候的输入流
     * @param request
     * @return
     */
    private String getRequestData(HttpServletRequest request){
        String postData = null;
        InputStream in = null;
        try{
            in =request.getInputStream();
            postData = org.apache.commons.io.IOUtils.toString(in,"UTF-8");
        }catch (Exception e) {
            log.error("[微信推送]获取请求输入流失败.{}",LogUtil.getTrace(e));
        }
        finally {
            try{
                if(in!=null){
                    in.close();
                }
            }catch(IOException e){
                log.error("[微信推送]关闭输入流错误:{}",LogUtil.getTrace(e));
                throw new AppException(e);
            }
        }
        log.debug("[微信推送]推送参数流数据是:{}",postData);
        return postData;
    }

}
