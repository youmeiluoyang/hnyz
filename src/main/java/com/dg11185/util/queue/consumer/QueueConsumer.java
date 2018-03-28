package com.dg11185.util.queue.consumer;

import java.util.List;

/**
 * Interfaces for queue consumers
 * @author xiesp
 * @description
 * @date 2:38 PM  9/19/2017
 * @copyright 全国邮政电子商务运营中心
 */
public interface QueueConsumer<E> extends Runnable{

    /**
     * add a obj to queue
     * @param queueable
     */
    boolean addQueueable(E queueable);


    /**
     *
     * @return
     */
    void putToQueue(E queueable);

    /**
     * return the numbers that is waiting to be processed by consumer
     * @return
     */
    int getQueueSize();


    /**
     * how to consumer a queued obj
     * @param queueable
     */
    void consumeQueable(E queueable);


    /**
     * stop
     */
    void stop();


    /**
     * start
     */
    void start();


    /**
     *
     * @return
     */
    boolean isStop();


    /**
     * put unprocessed obj to list
     */
    List<E> toList();


    /**
     *
     */
    void serializer();

    /**
     *
     */
    void deserializer();



}
