package com.dg11185.hnyz.bean.front.question;

import java.io.Serializable;

/**
 * @author xiesp
 * @description
 * @date 4:14 PM  4/29/2018
 * @copyright 全国邮政电子商务运营中心
 */
public class Survey implements Serializable{
    private String ids;
    private String names;
    private String desc;


    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
