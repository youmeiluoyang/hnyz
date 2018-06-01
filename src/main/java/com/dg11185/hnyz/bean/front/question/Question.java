package com.dg11185.hnyz.bean.front.question;

import com.dg11185.dao.annotation.NotDbColumn;

import java.io.Serializable;
import java.util.List;

/**
 * @author xiesp
 * @description
 * @date 4:14 PM  4/29/2018
 * @copyright 全国邮政电子商务运营中心
 */
public class Question implements Serializable,Comparable<Question>{
    private String ids;
    private String surveyId;
    private String names;
    private String type;
    private String hasRemark;

    @NotDbColumn
    private List<QuestionItem> questionItems;


    public void addQuestionItem(QuestionItem questionItem){
        questionItems.add(questionItem);
    }

    public List<QuestionItem> getQuestionItems() {
        return questionItems;
    }

    public void setQuestionItems(List<QuestionItem> questionItems) {
        this.questionItems = questionItems;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public String getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(String surveyId) {
        this.surveyId = surveyId;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getHasRemark() {
        return hasRemark;
    }

    public void setHasRemark(String hasRemark) {
        this.hasRemark = hasRemark;
    }

    @Override
    public int compareTo(Question o) {
        int id1 = Integer.parseInt(this.ids);
        int id2 = Integer.parseInt(o.getIds());
        if(id1>id2)
            return 1;
        else if(id2 > id1)
            return -1;
        return 0;
    }
}
