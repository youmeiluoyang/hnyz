package com.dg11185.hnyz.service.api.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dg11185.hnyz.bean.common.wx.FansDetail;
import com.dg11185.hnyz.bean.common.wx.NewsMessage;
import com.dg11185.hnyz.common.config.ResourceConfig;
import com.dg11185.hnyz.common.exception.WxCallException;
import com.dg11185.hnyz.service.api.WeixinService;
import com.dg11185.hnyz.service.api.WxApiService;
import com.dg11185.util.network.HttpClientUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xiesp
 * @description
 * @date 1:36 PM  4/29/2018
 * @copyright 全国邮政电子商务运营中心
 */
@Service
public class WxApiServiceImpl implements WxApiService{



    private final static Logger log = LoggerFactory.getLogger(WxApiServiceImpl.class);
    @Autowired
    private WeixinService weixinService;

    @Override
    public FansDetail getFansDetail(String openId) {
        String baseUrl = ResourceConfig.getWxApiConfig().getProperty("address.back.getFans");
        String accessToken = weixinService.getAccessToken();
        String url = baseUrl + accessToken + "&openid=" +openId + "&lang=zh_CN";
        String result = HttpClientUtils.getByHttps(url);
        JSONObject json = JSON.parseObject(result);
        String errcode = json.getString("errcode");
        String errmsg = json.getString("errmsg");
        if(errcode == null || "0".equals(errcode)){
            FansDetail fansDetail = new FansDetail();
            fansDetail.setSubscribe(json.getString("subscribe"));
            fansDetail.setNickname(json.getString("nickname"));
            fansDetail.setHeadimgurl(json.getString("headimgurl"));
            log.info("[公众号管理]获取粉丝详情完成,openId:"+openId);
            return fansDetail;
        }else{
            throw new WxCallException(errmsg,errcode,"[公众号管理]获取粉丝详情失败,openId:" +openId);
        }
    }


    @Override
    public void sendTemplateMessage(String json) {
        String baseUrl = ResourceConfig.getWxApiConfig().getProperty("address.back.sendTemplate");
        String accessToken = weixinService.getAccessToken();
        String url = baseUrl + accessToken;
        String result = HttpClientUtils.postByHttps(url,json);
        JSONObject jsonResult = JSON.parseObject(result);
        String errcode = jsonResult.getString("errcode");
        String errmsg = jsonResult.getString("errmsg");
        if(errcode == null || "0".equals(errcode)){
            log.info("[公众号管理]发送模板消息成功:" + json);
        }else{
            throw new WxCallException(errmsg,errcode,"[公众号管理]发送模板消息失败:" + json);
        }
    }


    @Override
    public void sendKefuMessage(NewsMessage newsMessage) {
        String baseUrl = ResourceConfig.getWxApiConfig().getProperty("address.kefu.send");
        String accessToken = weixinService.getAccessToken();
        String url = baseUrl + accessToken;
        System.out.println(url);
        String json = JSONObject.toJSONString(newsMessage);
        String result = HttpClientUtils.postByHttps(url,json);
        JSONObject jsonResult = JSON.parseObject(result);
        String errcode = jsonResult.getString("errcode");
        String errmsg = jsonResult.getString("errmsg");
        if(errcode == null || "0".equals(errcode)){
            log.info("[公众号管理]发送客服消息成功:" + json);
        }else{
            throw new WxCallException(errmsg,errcode,"[公众号管理]发送客服消息失败:" + json);
        }
    }
}
