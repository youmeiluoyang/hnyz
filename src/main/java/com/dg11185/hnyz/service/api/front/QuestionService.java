package com.dg11185.hnyz.service.api.front;

import com.dg11185.hnyz.bean.common.wx.Fans;
import com.dg11185.hnyz.bean.front.question.Answer;
import com.dg11185.hnyz.bean.front.question.Question;
import com.dg11185.hnyz.bean.front.question.Survey;

import java.util.List;

/**
 * @author xiesp
 * @description
 * @date 4:56 PM  4/29/2018
 * @copyright 全国邮政电子商务运营中心
 */
public interface  QuestionService {

    /**
     * 获取问卷的信息
     * @param ids
     * @return
     */
    Survey getSurvey(String ids);


    /**
     * 获取问卷的题目信息
     * @param ids
     * @return
     */
    List<Question> getQuestionItems(String ids);


    /**
     * 入库问卷答案
     */
    void addAnswers(List<Answer> answers);


    /**
     * 统计各个问题的回答数量
     * @return
     */
    List<Question> statQuestion();


    /**
     * 判断一个用户是否参加过问卷
     * @return
     */
    boolean alreaytAnswer(Fans fans);

    /**
     * 添加分享记录
     */
    int addShareRec(String openId,String type);


    /**
     * 获取分享记录数目
     * @param openId
     * @param type
     */
    int getShareCnt(String openId, String type);



}
