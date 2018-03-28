package com.dg11185.util.observable;

/**
 * 通知接口<br>
 * 主要目的是为了形成维护良好的代码,不要在多个serice里重复的检测一些信息.<br>
 * 全部的触发事件都统一写在一个类里,便于管理维护
 * @author xiesp
 * @description
 * @date 11:40 AM  8/14/2017
 * @copyright 全国邮政电子商务运营中心
 */
public interface Observable {
    /**
     * 添加观察者
     * @param observaber
     */
    void  addLitener(Observaber observaber, Class<? extends ObserveEvent> t);

    /**
     * 移除观察者
     * @param ObserveEventClass
     */
    void removeLitener(Class<? extends ObserveEvent> ObserveEventClass);

    /**
     * 通知观察者
     * @param ObserveEvent
     */
    void notifyLitener(ObserveEvent ObserveEvent, Object... args);



}
