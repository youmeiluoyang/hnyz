package com.dg11185.hnyz.service.common.impl;

import com.dg11185.hnyz.common.config.ResourceConfig;
import com.dg11185.hnyz.common.exception.AesException;
import com.dg11185.hnyz.service.common.WeixinService;
import com.dg11185.hnyz.util.wx.WXBizMsgCrypt;
import org.springframework.stereotype.Service;

/**
 * @author xiesp
 * @description
 * @date 3:22 PM  3/28/2018
 * @copyright 全国邮政电子商务运营中心
 */
@Service
public class WeixinServiceImpl implements WeixinService {

    //加解密类,只一个
    private static final WXBizMsgCrypt wxBizMsgCrypt;
    private static final String appSecret;

    //静态初始化流程
    static {

        String token = ResourceConfig.getAppConfig().getProperty("app.token");
        String encryptKey = ResourceConfig.getAppConfig().getProperty("app.encodingKey");
        String appId = ResourceConfig.getAppConfig().getProperty("app.appId");
        appSecret = ResourceConfig.getAppConfig().getProperty("app.secret");
        wxBizMsgCrypt = new WXBizMsgCrypt(token, encryptKey, appId);
    }


    @Override
    public String decryptWxMessage(String msgSignature, String timeStamp, String nonce, String postData) throws AesException {
        return wxBizMsgCrypt.decryptMsg(msgSignature,timeStamp,nonce,postData,false);
    }
}
