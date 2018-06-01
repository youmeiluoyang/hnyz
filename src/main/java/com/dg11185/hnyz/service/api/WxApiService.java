package com.dg11185.hnyz.service.api;

import com.dg11185.hnyz.bean.common.wx.FansDetail;
import com.dg11185.hnyz.bean.common.wx.NewsMessage;

/**
 * @author xiesp
 * @description
 * @date 1:35 PM  4/29/2018
 * @copyright 全国邮政电子商务运营中心
 */
public interface WxApiService {

    /**
     * 获取粉丝的详细信息,也可以判断是不是粉丝
     * @param openId
     * @return
     */
    FansDetail getFansDetail(String openId);


    /**
     * 发送模板消息
     * @param json
     */
    void sendTemplateMessage(String json);


    /**
     * 发送客服消息
     */
    void sendKefuMessage(NewsMessage newsMessage);
}
