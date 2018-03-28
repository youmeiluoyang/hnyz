package com.dg11185.util.queue.extension.restrystrategy;

import com.dg11185.util.queue.consumer.QueueConsumer;
import com.dg11185.util.queue.queueobj.Queueable;

/**
 * queue retry interface
 * @author xiesp
 * @description
 * @date 2:27 PM  9/21/2017
 * @copyright 全国邮政电子商务运营中心
 */
public interface RetryStrategy {

    void retry(Queueable queueable, QueueConsumer consumer);





    //factory,for future extension
    public static class RetryStrategyFactory{
        private static final RetryStrategy RETRY_STRATEGY = new EnqueueRetryStrategy();

        public static RetryStrategy getRetryStrategy(){
            return RETRY_STRATEGY;
        }

    }
}
