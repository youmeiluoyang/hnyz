package com.dg11185.hnyz.controller.front;

import com.alibaba.fastjson.JSON;
import com.dg11185.hnyz.bean.common.APIResponse;
import com.dg11185.hnyz.bean.common.ResponseForm;
import com.dg11185.hnyz.bean.common.wx.Fans;
import com.dg11185.hnyz.bean.front.lottery.Lottery;
import com.dg11185.hnyz.bean.front.lottery.LotteryItem;
import com.dg11185.hnyz.bean.front.lottery.Reward;
import com.dg11185.hnyz.bean.front.question.Answer;
import com.dg11185.hnyz.bean.front.question.Question;
import com.dg11185.hnyz.bean.front.question.Survey;
import com.dg11185.hnyz.common.constant.SysConstant;
import com.dg11185.hnyz.service.api.front.LotteryService;
import com.dg11185.hnyz.service.api.front.QuestionService;
import com.dg11185.hnyz.util.LogUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author xiesp
 * @description
 * @date 5:19 PM  4/29/2018
 * @copyright 全国邮政电子商务运营中心
 */
@Controller
@RequestMapping("/api/front")
public class FrontApiController {

    private static Logger log = LoggerFactory.getLogger(FrontApiController.class);

    @Autowired
    private QuestionService questionService;

    @Autowired
    private LotteryService lotteryService;

    /**
     * 获取粉丝信息,可以判定是不是粉丝
     * @return
     */
    @RequestMapping(value = "/getSurveyInfo.do",method = RequestMethod.POST)
    @ResponseBody
    public APIResponse getQuestionInfo(@RequestParam String ids, HttpSession session) {
        APIResponse rsp = new APIResponse(APIResponse.SUCCESS);
        try{
            Fans fans = (Fans) session.getAttribute(SysConstant.WX_FRONT_USER);
            Survey survey = questionService.getSurvey(ids);
            List<Question> questions = questionService.getQuestionItems(ids);
            //看是否做过问卷
            boolean answered = questionService.alreaytAnswer(fans);
            int shareCnt = questionService.getShareCnt(fans.getOpenid(),"1");
            rsp.initedData().put("shareCnt", shareCnt);
            rsp.initedData().put("survey", survey);
            rsp.initedData().put("questions", questions);
            rsp.initedData().put("answered", answered);
        }catch (Exception e){
            rsp.setStatus(APIResponse.INNER_ERROR);
            log.error("【前端控制器】:获取粉丝问卷详情发生错误:" + LogUtil.getTrace(e));
        }
        return rsp;
    }


    /**
     * 入库问题的答案
     */
    @RequestMapping(value = "/addAnswers.do",method = RequestMethod.POST)
    @ResponseBody
    public APIResponse addAnswer(@RequestParam String answers) {
        APIResponse rsp = new APIResponse(APIResponse.SUCCESS);
        try{
            List<Answer> answerList = JSON.parseArray(answers,Answer.class);
            questionService.addAnswers(answerList);
        }catch (Exception e){
            rsp.setStatus(APIResponse.INNER_ERROR);
            log.error("【前端控制器】:提交问卷发生错误:" + LogUtil.getTrace(e));
        }
        return rsp;
    }


    /**
     * 入库问题的答案
     */
    @RequestMapping(value = "/getLottery.do",method = RequestMethod.POST)
    @ResponseBody
    public APIResponse getLottery(@RequestParam String lotteryId,String type,HttpSession session) {
        APIResponse rsp = new APIResponse(APIResponse.SUCCESS);
        if(StringUtils.isEmpty(type)){
            type = "1";
        }
        try{

            Fans fans = (Fans) session.getAttribute(SysConstant.WX_FRONT_USER);
            Lottery lottery = lotteryService.getLottery(lotteryId);
            List<LotteryItem> lotteryItems = lotteryService.getLotteryItems(lotteryId);
            int cnt = lotteryService.getLotteryCntByOpenId(fans,lotteryId);
            //看是否做过问卷
            boolean answered = questionService.alreaytAnswer(fans);
            int shareCnt = questionService.getShareCnt(fans.getOpenid(),type);
            rsp.initedData().put("shareCnt", shareCnt);
            rsp.initedData().put("lottery",lottery);
            rsp.initedData().put("items",lotteryItems);
            rsp.initedData().put("lotteryCnt",cnt);
            rsp.initedData().put("answered", answered);
        }catch (Exception e){
            rsp.setStatus(APIResponse.INNER_ERROR);
            log.error("【前端控制器】:获取抽奖信息发生错误:" + LogUtil.getTrace(e));
        }
        return rsp;
    }


    /**
     * 进行抽奖
     */
    @RequestMapping(value = "/doLottery.do",method = RequestMethod.POST)
    @ResponseBody
    public APIResponse doLottery(@RequestParam String openId,@RequestParam String lotteryId,HttpSession session) {
        APIResponse rsp = new APIResponse(APIResponse.SUCCESS);
        try{
            Fans fans = (Fans) session.getAttribute(SysConstant.WX_FRONT_USER);
            LotteryItem lotteryItem = lotteryService.doLottery(lotteryId,fans);
            if(lotteryItem != null){
                rsp.initedData().put("hit",true);
                rsp.initedData().put("item",lotteryItem);
            }else{
                rsp.initedData().put("hit",false);
            }
        }catch (Exception e){
            rsp.setStatus(APIResponse.INNER_ERROR);
            log.error("【前端控制器】:抽奖发生错误:" + LogUtil.getTrace(e));
        }
        return rsp;
    }


    /**
     * 获取奖项
     */
    @RequestMapping(value = "/getReward.do",method = RequestMethod.POST)
    @ResponseBody
    public APIResponse getReward(@RequestParam String lotteryId,HttpSession session) {
        APIResponse rsp = new APIResponse(APIResponse.SUCCESS);
        try{
            Fans fans = (Fans) session.getAttribute(SysConstant.WX_FRONT_USER);
            List<Reward> reward = lotteryService.getReward(fans,lotteryId);
            rsp.initedData().put("reward",reward);
        }catch (Exception e){
            rsp.setStatus(APIResponse.INNER_ERROR);
            log.error("【前端控制器】:获取奖品发生错误:" + LogUtil.getTrace(e));
        }
        return rsp;
    }

    /**
     *前端更新奖品状态
     */
    @RequestMapping(value = "updateReward.do",method = RequestMethod.POST)
    @ResponseBody
    public ResponseForm updateReward(String ids) {
        ResponseForm responseForm = new ResponseForm();
        try{
            this.lotteryService.updateReward(ids);
        }catch (Exception e){
            responseForm.setStatus(ResponseForm.ResponseStatus.ERROR.getName());
            log.error("[抽奖]更新奖品状态发生错误:" + LogUtil.getTrace(e));
        }
        return  responseForm;
    }


    /**
     * 添加分享记录
     */
    @RequestMapping(value = "/addShareRec.do",method = RequestMethod.POST)
    @ResponseBody
    public APIResponse addShareRec(HttpSession session,String type) {
        if(StringUtils.isEmpty(type)){
            type = "1";
        }
        APIResponse rsp = new APIResponse(APIResponse.SUCCESS);
        try{
            Fans fans = (Fans) session.getAttribute(SysConstant.WX_FRONT_USER);
            questionService.addShareRec(fans.getOpenid(),type);
        }catch (Exception e){
            rsp.setStatus(APIResponse.INNER_ERROR);
            log.error("【前端控制器】:获取奖品发生错误:" + LogUtil.getTrace(e));
        }
        return rsp;
    }

    /**
     * 获取奖项
     */
    @RequestMapping(value = "/getHitRecs.do",method = RequestMethod.POST)
    @ResponseBody
    public APIResponse getHitRecs(String lotteryId) {
        if(StringUtils.isEmpty(lotteryId)){
            lotteryId = "1";
        }
        APIResponse rsp = new APIResponse(APIResponse.SUCCESS);
        try{
            List<Reward> list = this.lotteryService.getHitRewards(lotteryId);
            rsp.initedData().put("hits",list);
        }catch (Exception e){
            rsp.setStatus(APIResponse.INNER_ERROR);
            log.error("【前端控制器】:随机获取中奖人员信息发生错误:" + LogUtil.getTrace(e));
        }
        return rsp;
    }


    /**
     * 获取奖项
     */
    @RequestMapping(value = "/test.do")
    @ResponseBody
    public APIResponse test() {
        APIResponse rsp = new APIResponse(APIResponse.SUCCESS);
        try{
            //lotteryService.sendHitMessage("oioFts1-YOJ2QXb5dG4GyM7t6Hzs","测试奖品");
        }catch (Exception e){
            rsp.setStatus(APIResponse.INNER_ERROR);
            log.error("【前端控制器】:测试发生错误呀:" + LogUtil.getTrace(e));
        }
        return rsp;
    }



}
