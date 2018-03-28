package com.dg11185.hnyz.bean.common.wx;

/**
 * 图文消息的一个图文
 * Created by xiesp on 2016/12/14.
 */
public class Article {
    private String Title;//标题
    private String Description;//描述
    private String PicUrl;//图片地址
    private String Url;//跳转地址
    public String getTitle() {
        return Title;
    }
    public void setTitle(String title) {
        Title = title;
    }
    public String getDescription() {
        return Description;
    }
    public void setDescription(String description) {
        Description = description;
    }
    public String getPicUrl() {
        return PicUrl;
    }
    public void setPicUrl(String picurl) {
        PicUrl = picurl;
    }
    public String getUrl() {
        return Url;
    }
    public void setUrl(String url) {
        Url = url;
    }
}
