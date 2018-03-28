package com.dg11185.util.queue.queueobj;

/**
 * 提供对队列对象的默认实现
 * @author xiesp
 * @description
 * @date 11:36 AM  8/24/2017
 * @copyright 全国邮政电子商务运营中心
 */
public abstract class AbsQueueObj implements Queueable {


    protected volatile int processCount = 1;

    /**
     * 子类需要实现的特定业务的方法,返回对象是为了以后兼容
     * @return
     */
    public abstract boolean process();

    /**
     * 默认不是紧急处理的
     * @return
     */
    @Override
    public boolean isUrgent() {
        return false;
    }


    /**
     * 默认是不顺序执行的
     * @return
     */
    @Override
    public boolean isSequential() {
        return false;
    }


    /**
     * 默认只执行一次,不重试
     * @return
     */
    @Override
    public int getMaxProcessCount() {
        return 1;
    }


    //不允许重写
    @Override
    public final  int getCurrentProcessCount() {
        return processCount;
    }

    //不允许重写
    @Override
    public final void addProcessCount() {
        processCount++;
    }
}
