package com.dg11185.util.queue.consumer.sequential;

import com.dg11185.hnyz.util.LogUtil;
import com.dg11185.util.concurrent.ThreadPoolUtil;
import com.dg11185.util.queue.consumer.AbsQueueConsumer;
import com.dg11185.util.queue.queueobj.Queueable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Sequential Consumer,for now, the enqueue speed and consume speed is not considered TODO
 * @author xiesp
 * @description
 * @date 2:41 PM  9/19/2017
 * @copyright 全国邮政电子商务运营中心
 */
public class SequentialQueueConsumer extends AbsQueueConsumer<Queueable> {


    private final static Logger log = LoggerFactory.getLogger(SequentialQueueConsumer.class);
    private final AtomicBoolean running = new AtomicBoolean(false);

    public SequentialQueueConsumer(){}

    @Override
    public void run() {
        while (queue.size()>0){
            try{
                Queueable queueable = queue.take();
                this.consumeQueable(queueable);
                //IE?
                if(Thread.currentThread().isInterrupted() || stop){
                    running.set(false);
                    stop = true;
                    log.info("[消费者监听({})]:一个任务执行完成之后收到终端或退出命令,强制退出.",this.getClass().getSimpleName());
                    return;
                }
            }catch (InterruptedException ie){
                stop = true;
                running.set(false);
                log.info("[消费者监听({})]:阻塞过程中收到中断标志,强制退出.",this.getClass().getSimpleName());
                return;
            }
            catch (Exception e){
                log.error("[消费者监听({})]出现异常:{}",this.getClass().getSimpleName(), LogUtil.getTrace(e));
            }
        }
        running.set(false);
        log.info("[消费者监听({})]:队列任务处理完毕,结束线程...",this.getClass().getSimpleName());
    }



    @Override
    public void putToQueue(Queueable queueable) {

        queue.add(queueable);
        //没有启动话就启动
        if(!running.get()){
            boolean succ = running.compareAndSet(false,true);
            if(succ){
                log.info("[消费者监听({})]:发现队列任务,消费者开始启动...",this.getClass().getSimpleName());
                ThreadPoolUtil.executeTask(this);
            }
        }
    }

}
