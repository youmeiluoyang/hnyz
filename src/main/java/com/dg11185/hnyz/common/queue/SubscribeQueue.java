package com.dg11185.hnyz.common.queue;

import com.dg11185.hnyz.bean.common.wx.Article;
import com.dg11185.hnyz.bean.common.wx.NewsMessage;
import com.dg11185.hnyz.service.api.WxApiService;
import com.dg11185.hnyz.util.ServiceFacade;
import com.dg11185.util.queue.queueobj.AbsQueueObj;

import java.util.List;

/**
 * @author xiesp
 * @description
 * @date 16:52  2018/5/28
 * @copyright 全国邮政电子商务运营中心
 */
public class SubscribeQueue extends AbsQueueObj {


    private final String openId;
    private final List<Article> articles;

    public SubscribeQueue(String openId,List<Article> articles){
        this.openId = openId;
        this.articles = articles;
    }

    @Override
    public boolean process() {
        NewsMessage newsMessage = new NewsMessage();
        newsMessage.setTouser(openId);
        newsMessage.getNews().put("articles",articles);
        WxApiService wxApiService = ServiceFacade.getBean(WxApiService.class);
        wxApiService.sendKefuMessage(newsMessage);
        return true;
    }
}
