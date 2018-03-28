package com.dg11185.hnyz.bean.common.wx;

import java.util.List;

/**
 * 微信上的图文消息
 * Created by xiesp on 2016/12/14.
 */
public class NewsMessage extends BaseMessage{

    private int ArticleCount;//图文消息个数
    private List<Article> Articles;//图文消息列表
    public int getArticleCount() {
        return ArticleCount;
    }
    public void setArticleCount(int articleCount) {
        ArticleCount = articleCount;
    }
    public List<Article> getArticles() {
        return Articles;
    }
    public void setArticles(List<Article> articles) {
        Articles = articles;
    }
}
