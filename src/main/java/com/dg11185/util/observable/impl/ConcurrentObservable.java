package com.dg11185.util.observable.impl;

import com.dg11185.util.observable.Observaber;
import com.dg11185.util.observable.Observable;
import com.dg11185.util.observable.ObserveEvent;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 具体实现的一个被观察者
 * @author xiesp
 * @description
 * @date 12:21 PM  8/14/2017
 * @copyright 全国邮政电子商务运营中心
 */
public class ConcurrentObservable implements Observable {

    private static final ConcurrentMap<Class<? extends ObserveEvent>,Observaber> OBSERVABER_CONCURRENT_MAP =
            new ConcurrentHashMap<Class<? extends ObserveEvent>,Observaber>();

    @Override
    public void addLitener(Observaber observaber, Class<? extends ObserveEvent> t) {
        //非覆盖形式,一般一个业务只需要一个处理即可x
        OBSERVABER_CONCURRENT_MAP.putIfAbsent(t,observaber);
    }

    @Override
    public void removeLitener(Class<? extends ObserveEvent> observableEvent) {
        OBSERVABER_CONCURRENT_MAP.remove(observableEvent);
    }

    @Override
    public void notifyLitener(ObserveEvent observableEvent,Object... args) {
        Observaber observaber =  OBSERVABER_CONCURRENT_MAP.get(observableEvent.getClass());
        if(observaber!=null){
            observaber.onNotify(observableEvent,args);
        }
    }


}
