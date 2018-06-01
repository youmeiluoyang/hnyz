package com.dg11185.hnyz.bean.front.lottery;

import com.dg11185.dao.annotation.NotDbColumn;

import java.io.Serializable;
import java.util.Date;

/**
 * @author xiesp
 * @description
 * @date 11:29 AM  5/2/2018
 * @copyright 全国邮政电子商务运营中心
 */
public class Reward implements Serializable{

    private String ids;
    private String openId;
    private String itemId;
    private Date hitTime;
    private Integer hasHit;
    private String lotteryId;
    private String nickName;
    private String imgUrl;
    private String state;
    private String billNo;
    private String attach;



    @NotDbColumn
    private String rewardNames;
    @NotDbColumn
    private String rewardType;
    @NotDbColumn
    private String rewardAmount;
    private String rewardLink;


    public String getRewardLink() {
        return rewardLink;
    }

    public void setRewardLink(String rewardLink) {
        this.rewardLink = rewardLink;
    }

    public String getRewardAmount() {
        return rewardAmount;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public void setRewardAmount(String rewardAmount) {
        this.rewardAmount = rewardAmount;
    }

    public String getRewardType() {
        return rewardType;
    }

    public void setRewardType(String rewardType) {
        this.rewardType = rewardType;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }




    public String getRewardNames() {
        return rewardNames;
    }

    public void setRewardNames(String rewardNames) {
        this.rewardNames = rewardNames;
    }

    public String getLotteryId() {
        return lotteryId;
    }

    public void setLotteryId(String lotteryId) {
        this.lotteryId = lotteryId;
    }

    public Integer getHasHit() {
        return hasHit;
    }

    public void setHasHit(Integer hasHit) {
        this.hasHit = hasHit;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public Date getHitTime() {
        return hitTime;
    }

    public void setHitTime(Date hitTime) {
        this.hitTime = hitTime;
    }
}
