package com.dg11185.hnyz.bean.front.question;

import com.dg11185.dao.annotation.NotDbColumn;

import java.io.Serializable;

/**
 * @author xiesp
 * @description
 * @date 4:15 PM  4/29/2018
 * @copyright 全国邮政电子商务运营中心
 */
public class QuestionItem implements Serializable {
    private String ids;
    private String questionId;
    private String desc;
    private String sortNum;

    //投票人数,默认是0
    @NotDbColumn
    private Integer cnt = 0;


    public Integer getCnt() {
        return cnt;
    }

    public void setCnt(Integer cnt) {
        this.cnt = cnt;
    }

    public String getSortNum() {
        return sortNum;
    }

    public void setSortNum(String sortNum) {
        this.sortNum = sortNum;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
