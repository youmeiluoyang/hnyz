package com.dg11185.hnyz.service.api.front;

import com.dg11185.hnyz.bean.common.PageWrap;
import com.dg11185.hnyz.bean.common.SearchForm;
import com.dg11185.hnyz.bean.common.wx.Fans;
import com.dg11185.hnyz.bean.front.lottery.Lottery;
import com.dg11185.hnyz.bean.front.lottery.LotteryItem;
import com.dg11185.hnyz.bean.front.lottery.Reward;

import java.io.OutputStream;
import java.util.List;

/**
 * @author xiesp
 * @description
 * @date 11:36 AM  5/2/2018
 * @copyright 全国邮政电子商务运营中心
 */
public interface LotteryService {

    /**
     * 获取抽奖的信息
     * @param ids
     * @return
     */
    Lottery getLottery(String ids);


    /**
     * 获取抽奖的奖品信息
     * @return
     */
    List<LotteryItem> getLotteryItems(String lotteryId);


    /**
     * 抽奖,返回中奖或者null
     * @param openId
     * @return
     */
    LotteryItem doLottery(String ids, Fans fans);


    /**
     * 获取已经参加的抽奖
     * @param openId
     * @return
     */
    int getLotteryCntByOpenId(Fans fans,String lotteryId);


    /**
     * 获取一个用户的获奖记录
     * @param lotteryId
     * @return
     */
    List<Reward> getReward(Fans fans, String lotteryId);


    /**
     * 获取全部用户的获奖记录
     * @return
     */
    PageWrap<Reward> getRewardByPage(SearchForm searchForm);


    /**
     * 发送红包
     * @param amount
     */
    boolean sendRedPack(Fans fans,String amount,String billNo);


    /**
     *更新奖品信息
     * @param ids
     */
    void updateReward(String ids);


    /**
     * 发送中奖的模板消息
     * @param openId
     * @param itemName
     */
    void sendHitMessage(String openId,String itemName);


    /**
     * 获取一些中奖纪录展示
     */
    List<Reward> getHitRewards();


    /**
     * 导出中奖列表
     * @param queryForm
     * @param outputStream
     */
    void export(SearchForm queryForm, OutputStream outputStream);

}
