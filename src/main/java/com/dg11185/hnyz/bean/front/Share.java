package com.dg11185.hnyz.bean.front;

import java.io.Serializable;

/**
 * @author xiesp
 * @description
 * @date 15:07  2018/5/21
 * @copyright 全国邮政电子商务运营中心
 */
public class Share implements Serializable{
    private String ids;
    private String openId;
    private String type;


    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
