package com.dg11185.util.queue.consumer.concurrent;

import com.dg11185.hnyz.util.LogUtil;
import com.dg11185.util.concurrent.ThreadPoolUtil;
import com.dg11185.util.queue.consumer.AbsQueueConsumer;
import com.dg11185.util.queue.queueobj.Queueable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Deque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Consumer that consume queueing obj concurrent.for now,the
 * concurrency strategy is for every 10 tasks,a thread is booted
 * to run this consumer.
 * <p>For scalability and performance ,every queue task should update as fast as possible</p>
 * @author xiesp
 * @description
 * @date 2:41 PM  9/19/2017
 * @copyright 全国邮政电子商务运营中心
 */
public class ConcurrentQueueConsumer extends AbsQueueConsumer<Queueable> {

    private final static Logger log = LoggerFactory.getLogger(ConcurrentQueueConsumer.class);
    private static volatile int maxThreadCount;
    //all instance share one queue
    private static final BlockingQueue<Queueable> staticQueue = new LinkedBlockingDeque<>();
    //initial 1,self-maintained
    private static final AtomicInteger threadCount = new AtomicInteger(1);
    private volatile boolean running = true;
    private volatile static  boolean allStop = false;
    //because of concurrency of this consumer...
    private static volatile  boolean hasDeserialize = false;


    public static void setMaxThreadCount(int count){
        maxThreadCount = count;
    }

    public ConcurrentQueueConsumer(){
        //override the container
        super(staticQueue);
    }



    @Override
    public void run() {
        log.info("[消费者监听({})]:并发消费者启动,最大线程数目:{}",this.getClass().getSimpleName(),maxThreadCount);
        while (running){
            try{
                Queueable queueable = queue.take();
                //check queue too long
                checkQueueTooLong();
                //process queue
                this.consumeQueable(queueable);
                //check queue to short
                checkEndcondition();
            }catch (InterruptedException ie){
                log.info("[消费者监听({})]:阻塞过程中收到中断标志,强制退出.",this.getClass().getSimpleName());
                running = false;
                break;
            }
            catch (Exception e){
                log.error("[消费者监听({})]出现异常:{}",this.getClass().getSimpleName(), LogUtil.getTrace(e));
            }
        }
    }


    @Override
    public void putToQueue(Queueable queueable) {
        //if is urgent,placed at head of the queue
        if(queueable.isUrgent()){
            ((Deque)queue).addFirst(queueable);
        }
        else{
            queue.add(queueable);
        }
    }





    //check whether new thread is needed
    private void checkQueueTooLong(){
        int size = queue.size();
        //every 10
        int threadWanted = (size / 10) +1;
        int threadcurrent= threadCount.get();
        if(threadcurrent < threadWanted){
            if(threadcurrent <maxThreadCount){
                boolean succ = threadCount.compareAndSet(threadcurrent,threadcurrent+1);
                if(succ){
                    log.info("[消费者监听({})]待处理任务上升:{},目前线程数目:{},开启新线程协助处理...",this.getClass().getSimpleName(),size,threadcurrent);
                    ThreadPoolUtil.executeTask(new ConcurrentQueueConsumer());
                }
            }
        }
    }

    //check whether thread should be terminated
    private void checkEndcondition(){
        //Test is allStop
        if(allStop ){
            running = false;
            return;
        }
        int size = queue.size();
        int threadWanted = (size / 10) +1;
        int threadcurrent= threadCount.get();
        if(threadcurrent > threadWanted){
            if(threadcurrent >= 2){
                boolean succ = threadCount.compareAndSet(threadcurrent,threadcurrent-1);
                if(succ){
                    log.info("[消费者监听({})]待处理任务下降:{},目前线程数目:{},关闭线程...",this.getClass().getSimpleName(),size,threadcurrent);
                    running = false;
                }
            }
        }
        //check is had been interrupted
        if(Thread.currentThread().isInterrupted()){
            log.info("[消费者监听({})]任务完成后检测到中断标志,强制退出...",this.getClass().getSimpleName());
            running = false;
        }
    }

    //should override because of concurrency
    //atomic is not guaranteed
    @Override
    public void stop() {
        if(!allStop){
            allStop = true;
            super.stop();
        }
    }

    @Override
    public void start() {
        ThreadPoolUtil.executeTask(this);
        //caution,here atomic is not guaranteed
        if(!hasDeserialize){
            hasDeserialize = true;
            super.start();
        }
    }
}
