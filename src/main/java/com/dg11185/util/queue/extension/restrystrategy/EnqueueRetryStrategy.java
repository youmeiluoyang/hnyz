package com.dg11185.util.queue.extension.restrystrategy;

import com.dg11185.util.queue.consumer.QueueConsumer;
import com.dg11185.util.queue.queueobj.Queueable;

/**
 * if fail,enqueue again
 * @author xiesp
 * @description
 * @date 2:30 PM  9/21/2017
 * @copyright 全国邮政电子商务运营中心
 */
public class EnqueueRetryStrategy implements RetryStrategy {


    @Override
    public void retry(Queueable queueable, QueueConsumer consumer) {
        consumer.addQueueable(queueable);
    }
}
