package com.dg11185.hnyz.bean.common.wx;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 关注的图文消息
 * Created by xiesp on 2016/12/14.
 */
public class NewsMessage{

    private String touser;
    private String msgtype = "news";
    private Map<String,List<Article>> news;

    public NewsMessage(){
        this.news = new HashMap<>();
        this.news.put("articles",new ArrayList<Article>());
    }


    public void addArtcle(Article article){
        this.news.get("articles").add(article);
    }

    public String getTouser() {
        return touser;
    }

    public void setTouser(String touser) {
        this.touser = touser;
    }

    public String getMsgtype() {
        return msgtype;
    }

    public void setMsgtype(String msgtype) {
        this.msgtype = msgtype;
    }

    public Map<String, List<Article>> getNews() {
        return news;
    }

    public void setNews(Map<String, List<Article>> news) {
        this.news = news;
    }
}
