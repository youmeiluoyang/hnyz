package com.dg11185.util.observable;

/**
 * 事件父类
 * @author xiesp
 * @description
 * @date 9:11 AM  9/29/2017
 * @copyright 全国邮政电子商务运营中心
 */
public abstract  class ObserveEvent {
    protected final String type;


    public ObserveEvent(String type){
        this.type = type;
    }


    public String getType(){
        return this.type;
    }
}
