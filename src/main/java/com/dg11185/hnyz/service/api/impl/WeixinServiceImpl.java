package com.dg11185.hnyz.service.api.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dg11185.hnyz.common.config.ResourceConfig;
import com.dg11185.hnyz.common.exception.AesException;
import com.dg11185.hnyz.common.exception.WxCallException;
import com.dg11185.hnyz.service.api.WeixinService;
import com.dg11185.hnyz.util.ServiceFacade;
import com.dg11185.hnyz.util.wx.WXBizMsgCrypt;
import com.dg11185.util.network.HttpClientUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @author xiesp
 * @description
 * @date 3:22 PM  3/28/2018
 * @copyright 全国邮政电子商务运营中心
 */
@Service
public class WeixinServiceImpl implements WeixinService {


    private final static Logger log = LoggerFactory.getLogger(WeixinServiceImpl.class);

    //加解密类,只一个
    private static final WXBizMsgCrypt wxBizMsgCrypt;
    private static final String appSecret;
    private static final String appId;

    //静态初始化流程
    static {

        String token = ResourceConfig.getAppConfig().getProperty("app.token");
        String encryptKey = ResourceConfig.getAppConfig().getProperty("app.encodingKey");
        appId = ResourceConfig.getAppConfig().getProperty("app.appId");
        appSecret = ResourceConfig.getAppConfig().getProperty("app.secret");
        wxBizMsgCrypt = new WXBizMsgCrypt(token, encryptKey, appId);
    }


    @Override
    public String decryptWxMessage(String msgSignature, String timeStamp, String nonce, String postData) throws AesException {
        return wxBizMsgCrypt.decryptMsg(msgSignature,timeStamp,nonce,postData,false);
    }


    @Override
    @Cacheable(value = "accessTokenCache",key="'key'")
    public String getAccessToken() {
        StringBuilder url = new StringBuilder(ResourceConfig.getWxApiConfig().getProperty("address.acces_token"));
        url.append("&appid=").append(appId).append("&secret=").append(appSecret);
        String result = HttpClientUtils.getByHttps(url.toString());
        JSONObject json = JSON.parseObject(result);
        String errcode = json.getString("errcode");
        String errmsg = json.getString("errmsg");
        String accessToken = null;
        if(errcode == null ||  "0".equals(errcode)){
            accessToken = json.getString("access_token");
            log.info("[公众号管理]更新公众平台access_token:{}",accessToken);
            return accessToken;
        }else{
            throw new WxCallException(errmsg,errcode,"[公众号管理]获取微信access_token失败");
        }
    }


    @Override
    @Cacheable(value = "accessTokenCache",key="'jsApiTicket'")
    public String gerJsApiToken() {
        WeixinService weixinService = ServiceFacade.getBean(WeixinService.class);
        String accessToken = weixinService.getAccessToken();
        StringBuilder url = new StringBuilder(ResourceConfig.getWxApiConfig().getProperty("address.jsApiAccessToken"));
        url.append(accessToken);
        String result = HttpClientUtils.getByHttps(url.toString());
        JSONObject json = JSON.parseObject(result);
        String errcode = json.getString("errcode");
        String errmsg = json.getString("errmsg");
        String ticket = null;
        if(errcode == null ||  "0".equals(errcode)){
            ticket = json.getString("ticket");
            log.info("[公众号管理]获取jsApi ticket:{}",ticket);
            return ticket;
        }else{
            throw new WxCallException(errmsg,errcode,"[公众号管理]获取jsAPi ticket失败");
        }
    }
}
