package com.dg11185.hnyz.bean.common.wx;

import com.dg11185.dao.annotation.NotDbColumn;

import java.io.Serializable;

/**
 * @Author zouwei
 * @Since 1.0
 * @Datetime 2017-04-26 11:02
 * @Copyright (c) 2017 全国邮政电⼦子商务运营中⼼心. All rights reserved.
 */

public class TemplateMsgSetting implements Serializable{

    @NotDbColumn
    private static final long serialVersionUID = -5513487911245840411L;

    private String appId; //公众号appId
    private Integer industryId1; //第一行业
    private Integer industryId2; //第二行业
    private String actRemindTemplateId; //活动提醒消息模板id


    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public Integer getIndustryId1() {
        return industryId1;
    }

    public void setIndustryId1(Integer industryId1) {
        this.industryId1 = industryId1;
    }

    public Integer getIndustryId2() {
        return industryId2;
    }

    public void setIndustryId2(Integer industryId2) {
        this.industryId2 = industryId2;
    }

    public String getActRemindTemplateId() {
        return actRemindTemplateId;
    }

    public void setActRemindTemplateId(String actRemindTemplateId) {
        this.actRemindTemplateId = actRemindTemplateId;
    }
}
