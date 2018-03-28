package com.dg11185.util.observable;

/**
 * @author xiesp
 * @description
 * @date 11:40 AM  8/14/2017
 * @copyright 全国邮政电子商务运营中心
 */
public interface Observaber {
    /**
     * 改变后的动作
     * @param observableEvent
     */
    void onNotify(ObserveEvent observableEvent, Object... args);
}
