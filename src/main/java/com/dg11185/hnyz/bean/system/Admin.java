package com.dg11185.hnyz.bean.system;

import java.io.Serializable;

/**
 * @author xiesp
 * @description
 * @date 9:11 AM  3/29/2018
 * @copyright 全国邮政电子商务运营中心
 */
public class Admin implements Serializable{

    private String ids;
    private String loginName;
    private String realName;
    private String telephone;
    private String pwd;


    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }
}
