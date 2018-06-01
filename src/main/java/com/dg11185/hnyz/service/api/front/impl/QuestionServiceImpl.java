package com.dg11185.hnyz.service.api.front.impl;

import com.dg11185.hnyz.bean.common.wx.Fans;
import com.dg11185.hnyz.bean.front.question.*;
import com.dg11185.hnyz.dao.api.front.QuestionDao;
import com.dg11185.hnyz.service.api.front.QuestionService;
import com.dg11185.hnyz.util.ServiceFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author xiesp
 * @description
 * @date 4:57 PM  4/29/2018
 * @copyright 全国邮政电子商务运营中心
 */
@Service
public class QuestionServiceImpl implements QuestionService{
    private static Logger log = LoggerFactory.getLogger(QuestionServiceImpl.class);

    @Autowired
    private QuestionDao questionDao;

    @Override
    public Survey getSurvey(String ids) {
        return questionDao.getSurvey(ids);
    }

    @Override
    @Cacheable(value = "commonCache",key="#ids+methodName")
    public List<Question> getQuestionItems(String ids) {

        Map<String,Question> hasDeal = new HashMap<>();
        List<Question> questions= new ArrayList<>();
        List<Map<String,Object>> mapList= questionDao.getQuestionItems(ids);
        for(Map<String,Object> map:mapList){
            String quesId = (String) map.get("questionId").toString();
            Question question = null;
            if(hasDeal.get(quesId) == null){
                question = new Question();
                question.setNames((String) map.get("quesName"));
                question.setIds((String) map.get("questionId").toString());
                question.setType((String) map.get("quesType").toString());
                question.setQuestionItems(new ArrayList<QuestionItem>());
                hasDeal.put(quesId,question);
            }else{
                question = hasDeal.get(quesId);
            }
            QuestionItem questionItem = new QuestionItem();
            questionItem.setDesc((String) map.get("desc"));
            questionItem.setIds((String) map.get("ids").toString());
            questionItem.setSortNum((String) map.get("sortNum").toString());
            question.addQuestionItem(questionItem);
        }
        List<Question> list =new ArrayList<>(hasDeal.values());
        Collections.sort(list);
        return list;
    }


    @Override
    public void addAnswers(List<Answer> answers) {
        questionDao.addAnsers(answers);
    }


    @Override
    public List<Question> statQuestion() {
        List<AnswerCnt> answerCnts = questionDao.getAnswerCnt();
        //获取问题和问题的选项
        List<Question> questions = ServiceFacade.getBean(QuestionService.class).getQuestionItems("1");
        Map<String,Integer> map = new HashMap<>();
        for(AnswerCnt answerCnt:answerCnts){
            map.put(answerCnt.getItemId(),answerCnt.getCnt());
        }
        //开始匹配答题数目
        for(Question question:questions){
            List<QuestionItem> questionItems = question.getQuestionItems();
            for(QuestionItem questionItem:questionItems){
               Integer cnt = map.get(questionItem.getIds());
               if(cnt!=null){
                   questionItem.setCnt(cnt);
               }
            }
        }
        return questions;
    }


    @Override
    public boolean alreaytAnswer(Fans fans) {
        int cnt = this.questionDao.getAnswerCntByOpenId(fans.getOpenid());
        return cnt>0;
    }


    @Override
    public int addShareRec(String openId, String type) {
        return  this.questionDao.addShareRec(openId,type);
    }


    @Override
    public int getShareCnt(String openId, String type) {
        int cnt = this.questionDao.getShareCnt(openId,type);
        return cnt;
    }
}
