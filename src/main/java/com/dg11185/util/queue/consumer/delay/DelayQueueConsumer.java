package com.dg11185.util.queue.consumer.delay;

import com.dg11185.hnyz.util.LogUtil;
import com.dg11185.util.concurrent.ThreadPoolUtil;
import com.dg11185.util.queue.consumer.AbsQueueConsumer;
import com.dg11185.util.queue.consumer.QueueConsumer;
import com.dg11185.util.queue.extension.delay.DelayItem;
import com.dg11185.util.queue.queueobj.AbsQueueObj;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Consumer that supports delay executions
 * @author xiesp
 * @description
 * @date 11:06 AM  10/10/2017
 * @copyright 全国邮政电子商务运营中心
 */
public class DelayQueueConsumer extends AbsQueueConsumer<DelayItem> {

    private final static Logger log = LoggerFactory.getLogger(DelayQueueConsumer.class);
    private final AtomicBoolean running = new AtomicBoolean(false);
    //the delegating QueueConsumer...
    private final QueueConsumer delegatingQueueConsumer;
    public DelayQueueConsumer(QueueConsumer delegatingQueueConsumer){
        //override container
        super(new DelayQueue<DelayItem>());
        this.delegatingQueueConsumer = delegatingQueueConsumer;
    }


    @Override
    public void run() {
        while (queue.size()>0){
            try{
                DelayItem delayItem= queue.take();

                /**
                 * do not update itself,because this consumer  is very sensitive to time
                 * so we delegate it task to the Concurrent One
                 * also ,wo could form a strategy here....TODO
                 */
                //this.consumeQueable(delayItem);
                this.delegateQueueable(delayItem);
                //IE occur?
                if(Thread.currentThread().isInterrupted() || stop){
                    log.info("[消费者监听({})]:一个任务执行完成之后收到终端或退出命令,强制退出.",this.getClass().getSimpleName());
                    running.set(false);
                    stop = true;
                    return;
                }
            }catch (InterruptedException ie){
                log.info("[消费者监听({})]:阻塞过程中收到中断标志,强制退出.",this.getClass().getSimpleName());
                running.set(false);
                stop = true;
                return;
            }
            catch (Exception e){
                log.error("[消费者监听({})]出现异常:{}",this.getClass().getSimpleName(), LogUtil.getTrace(e));
            }
        }
        //if code reaches here,meaning that queue is empty,then stop thread running
        running.set(false);
        log.info("[消费者监听({})]:队列任务处理完毕,结束线程...",this.getClass().getSimpleName());

    }


    @Override
    public  void putToQueue(DelayItem delayItem) {
        queue.add(delayItem);
        //没有启动话就启动
        if(!running.get()){
            boolean succ = running.compareAndSet(false,true);
            if(succ){
                log.info("[消费者监听({})]:发现队列任务,消费者开始启动...",this.getClass().getSimpleName());
                ThreadPoolUtil.executeTask(this);
            }
        }
    }


    /**
     * .....
     * @param delayItem
     */
    private void delegateQueueable(final DelayItem delayItem){
        delegatingQueueConsumer.addQueueable(new DelegatingQueueable(delayItem));
    }
}

class DelegatingQueueable extends AbsQueueObj{

    private final DelayItem delayItem;
    DelegatingQueueable(DelayItem delayItem){
        this.delayItem = delayItem;
    }

    @Override
    public boolean process() {
        return delayItem.process();
    }

    @Override
    public boolean isUrgent() {
        return true;
    }
}
