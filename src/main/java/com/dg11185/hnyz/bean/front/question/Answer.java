package com.dg11185.hnyz.bean.front.question;

import java.io.Serializable;

/**
 * @author xiesp
 * @description
 * @date 4:15 PM  4/29/2018
 * @copyright 全国邮政电子商务运营中心
 */
public class Answer implements Serializable{

    private String ids;
    private String questionItemId;
    private String openId;
    private String value;
    private String remark;


    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public String getQuestionItemId() {
        return questionItemId;
    }

    public void setQuestionItemId(String questionItemId) {
        this.questionItemId = questionItemId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
