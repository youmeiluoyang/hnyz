package com.dg11185.hnyz.bean.front.lottery;

import com.dg11185.dao.annotation.NotDbColumn;

import java.io.Serializable;
import java.util.List;

/**
 * @author xiesp
 * @description
 * @date 11:21 AM  5/2/2018
 * @copyright 全国邮政电子商务运营中心
 */
public class Lottery implements Serializable{

    private String ids;
    private String title;
    private String desc;
    private String personCnt;
    private String hitCnt;
    private String chanceCnt;

    @NotDbColumn
    private List<LotteryItem> lotteryItems;


    public List<LotteryItem> getLotteryItems() {
        return lotteryItems;
    }

    public void setLotteryItems(List<LotteryItem> lotteryItems) {
        this.lotteryItems = lotteryItems;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPersonCnt() {
        return personCnt;
    }

    public void setPersonCnt(String personCnt) {
        this.personCnt = personCnt;
    }

    public String getHitCnt() {
        return hitCnt;
    }

    public void setHitCnt(String hitCnt) {
        this.hitCnt = hitCnt;
    }


    public String getChanceCnt() {
        return chanceCnt;
    }

    public void setChanceCnt(String chanceCnt) {
        this.chanceCnt = chanceCnt;
    }
}
