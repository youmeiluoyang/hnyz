package com.dg11185.hnyz.service.api.front.impl;

import com.alibaba.fastjson.JSONObject;
import com.dg11185.hnyz.bean.common.PageWrap;
import com.dg11185.hnyz.bean.common.SearchForm;
import com.dg11185.hnyz.bean.common.wx.Fans;
import com.dg11185.hnyz.bean.common.wx.RedPack;
import com.dg11185.hnyz.bean.front.Coupon;
import com.dg11185.hnyz.bean.front.lottery.Lottery;
import com.dg11185.hnyz.bean.front.lottery.LotteryItem;
import com.dg11185.hnyz.bean.front.lottery.Reward;
import com.dg11185.hnyz.common.config.ResourceConfig;
import com.dg11185.hnyz.common.config.SysConfig;
import com.dg11185.hnyz.common.queue.SendRedpackQueue;
import com.dg11185.hnyz.common.queue.SendTemplateQueue;
import com.dg11185.hnyz.dao.api.front.LotteryDao;
import com.dg11185.hnyz.service.api.front.LotteryService;
import com.dg11185.hnyz.util.LogUtil;
import com.dg11185.hnyz.util.wx.WxMessageUtil;
import com.dg11185.util.common.DateUtil;
import com.dg11185.util.queue.QueueHandler;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.OutputStream;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author xiesp
 * @description
 * @date 11:38 AM  5/2/2018
 * @copyright 全国邮政电子商务运营中心
 */
@Service
public class LotteryServiceImpl implements LotteryService{

    private static Logger log = LoggerFactory.getLogger(LotteryServiceImpl.class);
    //抽奖的可能中奖纪录
    private static final ConcurrentMap<String,List<LotteryItem>> itemsMap = new ConcurrentHashMap<>();


    @Autowired
    private LotteryDao lotteryDao;

    @Override
    @Cacheable(value = "commonCache",key="#ids+methodName")
    public Lottery getLottery(String ids) {
        Lottery lottery = this.lotteryDao.getLottery(ids);
        return lottery;
    }

    @Override
    @Cacheable(value = "commonCache",key="#ids+methodName")
    public List<LotteryItem> getLotteryItems(String lotteryId) {
        List<LotteryItem> lotteryItems = this.lotteryDao.getLotteryItems(lotteryId);
        itemsMap.putIfAbsent(lotteryId,lotteryItems);
        return lotteryItems;
    }


    @Override
    public LotteryItem doLottery(String ids, Fans fans) {
        List<LotteryItem> lotteryItems = itemsMap.get(ids);
        Random random = new Random();
        int start = 0;
        int end = 0;
        LotteryItem hit = null;
        int num = random.nextInt(1000);
        for(LotteryItem lotteryItem:lotteryItems){
            int chance = lotteryItem.getChance();
            end +=chance;
            //这里其实已经中了奖了.但是还要判断数目是否正确.如果已经没有这个奖品了
            //相当于没中奖
            if(start<= num && num<end){
                //性能暂时不考虑,虽然在循环里面,最多只会进行同步代码一次
                //注意,只有中奖相同的用户才需要同步
                synchronized (lotteryItem){
                    int remain = lotteryItem.getHitLimit()  - lotteryItem.getAlreadyHitCnt();
                    if(remain>0){
                        hit = lotteryItem;
                        lotteryItem.setAlreadyHitCnt(lotteryItem.getAlreadyHitCnt() + 1);
                        break;
                    }
                    //相当于没中奖,直接返回
                    else{
                        break;
                    }
                }

            }else{
                start+=chance;
            }
        }
        this.addLotteryRecord(ids,hit,fans);
        return hit;
    }



    @Override
    public int getLotteryCntByOpenId(Fans fans,String lotteryId){
        int cnt = this.lotteryDao.getLotteryCntByOpenId(fans.getOpenid(),lotteryId);
        return cnt;
    }


    @Override
    public List<Reward> getReward(Fans fans, String lotteryId) {
        List<Reward> list=  this.lotteryDao.getReward(fans.getOpenid(),lotteryId);
        return list;
    }


    @Override
    public PageWrap<Reward> getRewardByPage(SearchForm searchForm) {
        return this.lotteryDao.getAllReward(searchForm);
    }



    private void addLotteryRecord(String lotteryId, LotteryItem hitItem, Fans fans){
        //更新参与人数和中奖人数
        String updateLotterySql = "update  tb_lottery set personCnt = personCnt+1,hitCnt = hitCnt+" +(hitItem==null?"0":"1");
        this.lotteryDao.getJdbcTemplate().update(updateLotterySql);

        String billNo = "";
        String state ="0";
        String attach = "";
        //更新奖品的已中奖数量
        if(hitItem!=null){
            String itemSql = "update tb_lotteryItem set alreadyHitCnt= alreadyHitCnt+1 where ids = ?";
            this.lotteryDao.getJdbcTemplate().update(itemSql,hitItem.getIds());
            //如果是红包,直接发送红包
            if("1".equals(hitItem.getType())){
                billNo = this.lotteryDao.getNextSqlValue("mch_billno")+"";
                String amount = hitItem.getAmount();
                this.sendRedPack(fans,amount,billNo);
                state = "1";
            }else{
                //发送模板消息
                this.sendHitMessage(fans.getOpenid(),hitItem.getNames());
            }
            //优惠券
            if("3".equals(hitItem.getType())){
                //需要同步,不然会出现问题
                synchronized (LotteryService.class){
                    Coupon coupon = this.lotteryDao.getUnusedCoupon();
                    attach = coupon.getCode();
                    this.lotteryDao.updateCoupon(coupon);
                }
            }
        }
        //插入参与记录
        String rewardSql = "insert into tb_reward(openId,itemId,hitTime,hasHit,lotteryId,imgUrl,nickName,state,billNo,attach) " +
                "values(?,?,?,?,?,?,?,?,?,?)";
        this.lotteryDao.saveOrUpdate(rewardSql,fans.getOpenid(),hitItem==null?null:hitItem.getIds(),new Date(),
                hitItem==null?0:1,lotteryId,fans.getHeadimgurl(),fans.getNickname(),state,billNo,attach);
    }


    @Override
    public void updateReward(String ids) {
        Reward target =   this.lotteryDao.getReward(ids);
        //String billNo = "";
        if(target!=null){
            //是否为发放
/*            if("0".equals(target.getState())){
                //如果是红包
                if("1".equals(target.getRewardType())){

                }
            }*/
            //实际去更新奖品状态
            Reward reward = new Reward();
            reward.setIds(ids);
            reward.setState("1");
            //reward.setBillNo(billNo);
            this.lotteryDao.updateReward(reward);
        }

    }

    //发送红包
    @Override
    public boolean sendRedPack(Fans fans,String amount,String billNo){
        RedPack redPack = new RedPack();
        String nonStr = WxMessageUtil.getNonceStr();
        redPack.setNonce_str(nonStr);
        redPack.setMch_billno(billNo);
        redPack.setClient_ip(SysConfig.ip);
        redPack.setMch_id(ResourceConfig.mchId);
        redPack.setWxappid(ResourceConfig.appId);
        redPack.setTotal_amount(amount);
        redPack.setTotal_num("1");
        redPack.setSend_name("邮美洛阳");
        redPack.setRe_openid(fans.getOpenid());
        redPack.setWishing("感谢您参与问卷调查,红包请收好!");
        redPack.setAct_name("有奖问卷调查");
        redPack.setRemark("运气不错哦,中奖啦");
        SendRedpackQueue sendRedpackQueue = new SendRedpackQueue(redPack);
        QueueHandler.addQuequeableObject(sendRedpackQueue);
        return true;
    }

    @Override
    public void sendHitMessage(String openId,String itemName){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("touser",openId);
        jsonObject.put("template_id","1Bmngg0I24je4kLU470DGq0YOw-ZdUC-feiI01RlVB8");
        jsonObject.put("url",SysConfig.domain + "/res/front/html/getReward.html");
        JSONObject data = new JSONObject();
        JSONObject first = new JSONObject();
        first.put("value","恭喜您中奖啦");
        first.put("color","#173177");
        JSONObject key1 = new JSONObject();
        key1.put("value",itemName);
        key1.put("color","#173177");
        JSONObject key2 = new JSONObject();
        key2.put("value", DateUtil.formatDate(new Date(),"yyyy-MM-dd HH:mm:ss"));
        key2.put("color","#173177");
        JSONObject remark = new JSONObject();
        remark.put("value","点我查看中奖详情哦");
        remark.put("color","#173177");
        jsonObject.put("data",data);
        data.put("first",first);
        data.put("keyword1",key1);
        data.put("keyword2",key2);
        //通过队列发送
        QueueHandler.addQuequeableObject(new SendTemplateQueue(JSONObject.toJSONString(jsonObject)));
    }


    @Override
    @Cacheable(value = "commonCache",key="methodName")
    public List<Reward> getHitRewards() {
        String sql = "select a.names rewardNames,b.* from tb_lotteryItem a right JOIN(select openId,nickName,imgUrl,itemId from tb_reward where hasHit = 1 group by openId,nickName,imgUrl,itemId) b\n" +
                " on a.ids = b.itemId limit 10";
        List<Reward> list = this.lotteryDao.queryForList(sql,Reward.class);
        return list;
    }


    @Override
    public void export(SearchForm queryForm, OutputStream outputStream) {
        WritableWorkbook wwb = null;
        try {
            // 创建可写EXCEL工作簿对象
            wwb = Workbook.createWorkbook(outputStream);
            // 创建第一个sheet
            wwb.createSheet("中奖统计", 0);
            // 得到第一个sheet
            WritableSheet sheet = wwb.getSheet(0);
            // 设置标题
            sheet.addCell(new Label(0, 0, "中奖统计"));
            // 设置表头
            int i = 0;
            sheet.addCell(new Label(i++, 1, "序号"));
            sheet.addCell(new Label(i++, 1, "昵称"));
            sheet.addCell(new Label(i++, 1, "奖品"));
            sheet.addCell(new Label(i++, 1, "中奖时间"));
            sheet.addCell(new Label(i++, 1, "状态"));
            sheet.addCell(new Label(i++, 1, "单号"));

            // 查询列表
            List<Reward> list= this.getRewardByPage(queryForm).getList();
            // 注入数据
            for (int row=2; row<list.size() + 2; row++) {
                Reward form = list.get(row - 2);
                i = 0;
                sheet.addCell(new Label(i++, row, String.valueOf(row - 1)));
                sheet.addCell(new Label(i++, row, form.getNickName()));
                sheet.addCell(new Label(i++, row, form.getRewardNames()));
                sheet.addCell(new Label(i++, row, "" +DateUtil.formatDate(form.getHitTime(),"yyyy-MM-dd HH:mm")));
                String state = "未发放";
                if("1".equals(form.getState())){
                    state="已发放";
                }else if("2".equals(form.getState())){
                    state="发放失败";
                }
                sheet.addCell(new Label(i++, row, state));
                sheet.addCell(new Label(i++, row, form.getBillNo()));
            }
        } catch (Exception e) {
            log.error("【中奖统计】报表出错:" + LogUtil.getTrace(e));
        } finally {
            try {
                wwb.write();
                wwb.close();
            } catch (Exception e) {
                log.error("【中奖统计】报表关闭输入流出错" + LogUtil.getTrace(e));
            }
        }
    }


}
