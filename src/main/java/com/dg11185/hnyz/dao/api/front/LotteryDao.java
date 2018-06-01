package com.dg11185.hnyz.dao.api.front;

import com.dg11185.hnyz.bean.common.PageWrap;
import com.dg11185.hnyz.bean.common.SearchForm;
import com.dg11185.hnyz.bean.front.Coupon;
import com.dg11185.hnyz.bean.front.lottery.Lottery;
import com.dg11185.hnyz.bean.front.lottery.LotteryItem;
import com.dg11185.hnyz.bean.front.lottery.Reward;
import com.dg11185.hnyz.dao.BaseDAO;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiesp
 * @description
 * @date 11:30 AM  5/2/2018
 * @copyright 全国邮政电子商务运营中心
 */
@Repository
public class LotteryDao extends BaseDAO{


    public Lottery getLottery(String ids){
        String sql = "select * from tb_lottery where ids = ?";
        Lottery lottery= this.queryForBean(sql,new Object[]{ids},Lottery.class);
        return lottery;
    }


    public List<LotteryItem> getLotteryItems(String lotteryId){
        String sql = "select * from tb_lotteryItem where lotteryId = ?";
        List<LotteryItem> lotteryItems= this.queryForList(sql,new Object[]{lotteryId},LotteryItem.class);
        return lotteryItems;
    }


    public int getLotteryCntByOpenId(String openId,String lotteryId){
        String sql = "select count(*) from tb_reward where openId = ?";
        int cnt = this.getJb().queryForObject(sql,Integer.class,openId);
        return cnt;
    }


    public List<Reward> getReward(String openId, String lotteryId){
        String sql = "select a.*,b.names rewardNames,b.type rewardType,b.amount rewardAmount,b.link rewardLink " +
                " from tb_reward a LEFT JOIN  tb_lotteryItem b\n" +
                " on a.itemId = b.ids where a.openId = ? and a.lotteryId = ? and a.hasHit = 1";
        List<Reward> rewards = this.queryForList(sql,new Object[]{openId,lotteryId},Reward.class);
        if(rewards.size() ==0)
            return null;
        return rewards;
    }


    public Reward getReward(String ids){
        String sql = "select a.*,b.names rewardNames,b.type rewardType,b.amount rewardAmount,b.link rewardLink " +
                " from tb_reward a LEFT JOIN  tb_lotteryItem b\n" +
                " on a.itemId = b.ids where a.ids = ? and a.hasHit = 1";
        List<Reward> rewards = this.queryForList(sql,new Object[]{ids},Reward.class);
        if(rewards.size() ==0)
            return null;
        return rewards.get(0);
    }


    public PageWrap<Reward> getAllReward(SearchForm searchForm){
        List<Object> params = new ArrayList<>();
        params.add(searchForm.getLotteryId());
        String sql = "select a.*,b.names rewardNames,b.link rewardLink from tb_reward a LEFT JOIN  tb_lotteryItem b\n" +
                " on a.itemId = b.ids where  a.lotteryId = ? and a.hasHit = 1";
        if(StringUtils.isNotBlank(searchForm.getKeywords())){
            sql += " and a.nickName like '%" + searchForm.getKeywords() + "%'";
        }
        PageWrap<Reward> wrap = this.queryForPage(sql,params.toArray(),searchForm,
                Reward.class);
        return wrap;
    }


    public int updateReward(Reward reward){
        return this.update(reward,"ids");
    }

    public Coupon getUnusedCoupon(){
        String sql = "select * from tb_coupon where ids = (select MIN(ids)  from  tb_coupon where state = 0)";
        Coupon coupon = this.queryForBean(sql,new Object[]{},Coupon.class);
        return coupon;
    }

    public int updateCoupon(Coupon coupon){
        String sql = "update tb_coupon set state = 1 where ids = ?";
        int cnt = this.getJdbcTemplate().update(sql,coupon.getIds());
        return cnt;
    }
}
