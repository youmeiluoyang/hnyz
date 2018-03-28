package com.dg11185.util.queue;

import com.dg11185.util.queue.consumer.QueueConsumer;
import com.dg11185.util.queue.consumer.concurrent.ConcurrentQueueConsumer;
import com.dg11185.util.queue.consumer.delay.DelayQueueConsumer;
import com.dg11185.util.queue.consumer.sequential.SequentialQueueConsumer;
import com.dg11185.util.queue.extension.delay.DelayItem;
import com.dg11185.util.queue.extension.delay.Delayable;
import com.dg11185.util.queue.queueobj.Queueable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 队列调度对象,做一些额外的工作
 * @author xiesp
 * @description
 * @date 6:44 PM  8/15/2017
 * @copyright 全国邮政电子商务运营中心
 */
public class QueueHandler{


    private static final QueueHandler queueHandler = new QueueHandler();
    private final static Logger log = LoggerFactory.getLogger(QueueHandler.class);
    //最大线程数目
    private static volatile int maxThreadCount;
    //阈值
    private  final int threshHold= 0;//Integer.parseInt(SysConfig.getProperty("queued.threshHold"));
    //重新接受的个数
    private  final int accepeableNum= 0;//Integer.parseInt(SysConfig.getProperty("queued.acceptableNum"));
    //指示是否队列太长,开始要转存到磁盘
    private static volatile boolean needStore = false;
    //并行执行者
    private   final QueueConsumer concurrentConsumer;
    //顺序执行者
    private   final QueueConsumer sequentialConsumer;
    //延迟执行者
    private   final QueueConsumer delayConsumer;


    //private constructor
    private  QueueHandler(){
        concurrentConsumer = new ConcurrentQueueConsumer();
        sequentialConsumer = new SequentialQueueConsumer();
        delayConsumer = new DelayQueueConsumer(concurrentConsumer);
    }




    /**
     * 开始启动队列消费,
     * 只能调用一次!!!!
     */
    public static void startQueuedTask(int maxThread){
        maxThreadCount = maxThread;
        log.info("[消费者监听处理者]开始启动消费者,最大线程数:{}", maxThreadCount);
        //boot the concurrentConsumer,others will start automatically
        queueHandler.delayConsumer.start();
        queueHandler.sequentialConsumer.start();
        ConcurrentQueueConsumer.setMaxThreadCount(maxThread);
        queueHandler.concurrentConsumer.start();
    }


    /**
     * delegating  method for adding queue obj
     * @param queueable
     */
    public static void addQuequeableObject(Queueable queueable){
        //Delayable?
        if(queueable instanceof Delayable){
            DelayItem delayItem = new DelayItem((Delayable) queueable);
            queueHandler.delayConsumer.addQueueable(delayItem);
        }else{
            //Sequential?
            if(queueable.isSequential()){
                queueHandler.sequentialConsumer.addQueueable(queueable);
            }else{
                queueHandler.concurrentConsumer.addQueueable(queueable);
            }
        }

    }
    /**
     * 返回最大的允许的队列线程执行数目
     * @return
     */
    public static int getMaxQueueThread(){
        return maxThreadCount;
    }


    /**
     * 停止
     */
    public static void stop(){
        queueHandler.delayConsumer.stop();
        queueHandler.sequentialConsumer.stop();
        queueHandler.concurrentConsumer.stop();
    }
}
