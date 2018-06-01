package com.dg11185.hnyz.bean.front.lottery;

import java.io.Serializable;

/**
 * @author xiesp
 * @description
 * @date 11:27 AM  5/2/2018
 * @copyright 全国邮政电子商务运营中心
 */
public class LotteryItem implements Serializable{
    private String ids;
    private String lotteryId;
    private String names;
    private Integer chance;
    private Integer hitLimit;
    private Integer alreadyHitCnt;
    private String type;
    private String img;
    private String amount;
    private String remark;
    private String link;


    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public Integer getAlreadyHitCnt() {
        return alreadyHitCnt;
    }

    public void setAlreadyHitCnt(Integer alreadyHitCnt) {
        this.alreadyHitCnt = alreadyHitCnt;
    }

    public Integer getHitLimit() {
        return hitLimit;
    }

    public void setHitLimit(Integer hitLimit) {
        this.hitLimit = hitLimit;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public String getLotteryId() {
        return lotteryId;
    }

    public void setLotteryId(String lotteryId) {
        this.lotteryId = lotteryId;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public Integer getChance() {
        return chance;
    }

    public void setChance(Integer chance) {
        this.chance = chance;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
