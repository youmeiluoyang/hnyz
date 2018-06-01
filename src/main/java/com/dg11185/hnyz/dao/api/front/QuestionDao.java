package com.dg11185.hnyz.dao.api.front;

import com.dg11185.hnyz.bean.front.question.Answer;
import com.dg11185.hnyz.bean.front.question.AnswerCnt;
import com.dg11185.hnyz.bean.front.question.Survey;
import com.dg11185.hnyz.common.exception.AppException;
import com.dg11185.hnyz.dao.BaseDAO;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author xiesp
 * @description
 * @date 4:36 PM  4/29/2018
 * @copyright 全国邮政电子商务运营中心
 */
@Repository
public class QuestionDao extends BaseDAO{

    public Survey getSurvey(String ids){
        String sql = "select * from tb_survey where ids = ?";
        Survey survey = this.queryForBean(sql,new Object[]{ids},Survey.class);
        return survey;
    }


    public List<Map<String,Object>> getQuestionItems(String ids){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("select a.names quesName,a.type quesType,b.* from tb_question a LEFT JOIN tb_questionitem b");
        stringBuilder.append(" on a.ids = b.questionId where a.surveyId = ? order by a.ids,b.sortNum asc ");
        List<Map<String,Object>>  mapList = this.queryForMapList(stringBuilder.toString(),new Object[]{ids});
        return mapList;
    }

    public void addAnsers(List<Answer> answers){
        String sql = "insert into tb_answer(questionItemId,openId,value,remark) values(?,?,?,?)";
        try{
            for(int i = 0;i<answers.size();i++){
                Answer answer = answers.get(i);
                this.saveOrUpdate(sql,answer.getQuestionItemId(),answer.getOpenId(),answer.getValue(),answer.getRemark());
            }
        }catch (Exception e){
            throw  new AppException(e);
        }
    }

    public int addShareRec(String openId,String type){
        String sql = "insert into tb_share(openId,type) values(?,?)";
        int cnt = this.saveOrUpdate(sql,openId,type);
        return cnt;
    }

    public int getShareCnt(String openId,String type){
        String sql = " select count(*) cnt from  tb_share where openId = ? and type = ?";
        int cnt = this.getJdbcTemplate().queryForObject(sql,Integer.class,openId,type);
        return cnt;
    }

    public List<AnswerCnt> getAnswerCnt(){
        String sql = "select count(questionItemId) cnt,questionItemId itemId from tb_answer group by questionItemId";
        List<AnswerCnt> list = this.queryForList(sql,AnswerCnt.class);
        return list;
    }

    public int getAnswerCntByOpenId(String openId){
        String sql = "select count(*) from tb_answer  where openId = ?";
        int cnt = this.getJb().queryForObject(sql,Integer.class,openId);
        return cnt;
    }

}
