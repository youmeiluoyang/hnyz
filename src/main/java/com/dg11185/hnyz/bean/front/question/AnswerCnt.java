package com.dg11185.hnyz.bean.front.question;

import java.io.Serializable;

/**
 * @author xiesp
 * @description
 * @date 6:19 PM  4/30/2018
 * @copyright 全国邮政电子商务运营中心
 */
public class AnswerCnt implements Serializable{

    private String itemId;
    private Integer cnt;


    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public Integer getCnt() {
        return cnt;
    }

    public void setCnt(Integer cnt) {
        this.cnt = cnt;
    }
}
