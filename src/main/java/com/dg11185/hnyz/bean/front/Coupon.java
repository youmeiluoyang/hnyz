package com.dg11185.hnyz.bean.front;

import java.io.Serializable;

/**
 * @author xiesp
 * @description
 * @date 16:46  2018/5/21
 * @copyright 全国邮政电子商务运营中心
 */
public class Coupon implements Serializable{
    private String ids;
    private String code;
    private String state;

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
