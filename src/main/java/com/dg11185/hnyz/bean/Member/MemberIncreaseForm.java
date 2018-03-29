package com.dg11185.hnyz.bean.Member;

import java.io.Serializable;

/**
 * @author xiesp
 * @description
 * @date 6:36 PM  3/29/2018
 * 用户增长的信息
 */
public class MemberIncreaseForm implements Serializable{

    private int count;
    private String orgName;


    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }
}
