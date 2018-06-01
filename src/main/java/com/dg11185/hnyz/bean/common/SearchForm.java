package com.dg11185.hnyz.bean.common;

/**
 * @author xiesp
 * @description
 * @date 8:50 AM  3/30/2018
 * 分页搜索统一使用
 */
public class SearchForm extends PageRequest {
    private String keywords;
    private String lotteryId;


    public String getLotteryId() {
        return lotteryId;
    }

    public void setLotteryId(String lotteryId) {
        this.lotteryId = lotteryId;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }
}
