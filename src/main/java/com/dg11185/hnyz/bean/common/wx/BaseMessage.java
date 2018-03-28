package com.dg11185.hnyz.bean.common.wx;

import java.io.Serializable;

/**
 * 回复给微信消息的父类
 * Created by xiesp on 2016/12/14.
 */
public class BaseMessage implements Serializable {

    // 接收方帐号（收到的OpenID）
    // 开发者微信号
    protected String FromUserName;
    // 消息创建时间 （整型）
    protected long CreateTime;
    // 消息类型（text/music/news）
    protected String MsgType;

    private String ToUserName;
    public String getToUserName() {
        return ToUserName;
    }
    public void setToUserName(String toUserName) {
        ToUserName = toUserName;
    }
    public String getFromUserName() {
        return FromUserName;
    }
    public void setFromUserName(String fromUserName) {
        FromUserName = fromUserName;
    }
    public long getCreateTime() {
        return CreateTime;
    }
    public void setCreateTime(long createTime) {
        CreateTime = createTime;
    }
    public String getMsgType() {
        return MsgType;
    }
    public void setMsgType(String msgType) {
        MsgType = msgType;
    }
}
