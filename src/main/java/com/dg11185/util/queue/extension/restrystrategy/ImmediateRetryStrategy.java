package com.dg11185.util.queue.extension.restrystrategy;

import com.dg11185.util.queue.consumer.QueueConsumer;
import com.dg11185.util.queue.queueobj.Queueable;

/**
 * if fail,retry immediately
 * @author xiesp
 * @description
 * @date 2:33 PM  9/21/2017
 * @copyright 全国邮政电子商务运营中心
 */
public class ImmediateRetryStrategy implements RetryStrategy {


    @Override
    public void retry(Queueable queueable, QueueConsumer consumer) {
        consumer.consumeQueable(queueable);
/*        while (queueable.getCurrentProcessCount()<queueable.getMaxProcessCount()){
            queueable.addProcessCount();
            boolean isSuccess = queueable.process();
            if(isSuccess){
                return;
            }
        }*/
    }
}
