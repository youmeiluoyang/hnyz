package com.dg11185.util.observable.impl;

import com.dg11185.util.observable.Observaber;
import com.dg11185.util.observable.Observable;
import com.dg11185.util.observable.ObserveEvent;

/**
 * 工具类,屏蔽内部实现细节
 * @author xiesp
 * @description
 * @date 12:47 PM  8/14/2017
 * @copyright 全国邮政电子商务运营中心
 */
public class Observables {
    private static final Observable observable;
    static {
        observable = new ConcurrentObservable();
    }

    /**
     * 通知观察者
     * @return
     */
    public  static void notifyLitener(ObserveEvent observableEvent,Object... args){
        observable.notifyLitener(observableEvent,args);
    }


    /**
     * 添加
     * @return
     */
    public  static void addLitener(Observaber observaber, Class<? extends ObserveEvent> observableEventClass){
        observable.addLitener(observaber,observableEventClass);
    }

}
