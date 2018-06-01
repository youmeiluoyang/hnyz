package com.dg11185.hnyz.bean.common.wx;

/**
 * 图文消息的一个图文
 * Created by xiesp on 2016/12/14.
 */
public class Article {
    private String title;//标题
    private String description;//描述
    private String picurl;//图片地址
    private String url;//跳转地址


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPicurl() {
        return picurl;
    }

    public void setPicurl(String picurl) {
        this.picurl = picurl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
