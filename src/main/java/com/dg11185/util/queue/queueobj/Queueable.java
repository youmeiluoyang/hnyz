package com.dg11185.util.queue.queueobj;

import java.io.Serializable;

/**
 *
 * 所有可以队列处理的消息都应该实现这个接口,保证队列的可扩展性.<br>
 * 队列的任务应该是细粒度的,可以多个任务,每个任务执行之间短.不应该一个任务,执行时间很长<br>
 * 如果执行时间太长,请启动另外的线程执行<br>
 * Serializable接口的实现,是因为队列太长的时候,先保存在本地,数量减低之后再恢复<br>
 * @author xiesp
 * @description
 * @date 9:14 AM  8/21/2017
 * @copyright 全国邮政电子商务运营中心
 */
public interface Queueable extends Serializable{

    /**
     * 将被消费者调用的接口,
     * @return
     */
    boolean process();


    /**
     * 是否需要紧急处理的内容,紧急处理的将被插入的队列头部
     * @return
     */
    boolean isUrgent();

    /**
     * 有一些任务是需要顺序执行的,或者说因为不能调用太频繁,而且不会太多,顺序执行即可
     * 那么这个任务将被加入到一个另外的独立的线性队列,顺序一条条的执行,只要一个线程执行
     */
    boolean isSequential();


    /**
     * 有可能会执行失败,只是失败的话,有可能需要重试
     * 如果这个方法返回的数字大于1,那么失败后就会重试
     * 比如返回3,一直失败的话,最多重试三次,然后放弃,
     * 如果需要队列执行者自动帮忙重试,重写这个方法即可
     * @return
     */
    int getMaxProcessCount();


    /**
     * 返回当前的执行次数
     * @return
     */
    int getCurrentProcessCount();

    /**
     * 增加一次执行次数
     * @return
     */
    void addProcessCount();


}
