package com.dg11185.util.queue.consumer;

import com.dg11185.hnyz.util.LogUtil;
import com.dg11185.util.queue.extension.restrystrategy.RetryStrategy;
import com.dg11185.util.queue.extension.serialize.DefaultQueueSerializer;
import com.dg11185.util.queue.extension.serialize.QueueSerializer;
import com.dg11185.util.queue.queueobj.Queueable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Default consumer implementation
 * @author xiesp
 * @description
 * @date 5:29 PM  9/19/2017
 * @copyright 全国邮政电子商务运营中心
 */
public abstract  class AbsQueueConsumer<E extends Queueable> implements QueueConsumer<E> {

    private final static Logger log = LoggerFactory.getLogger(AbsQueueConsumer.class);
    protected final RetryStrategy retryStrategy;
    protected final BlockingQueue<E> queue;
    protected final QueueSerializer queueSerializer;
    //number that should start serialize
    protected final int threadHoldMax;
    protected final int threadHoldMin;
    protected final boolean enableSerialize;
    protected volatile boolean stop = false;
    protected volatile AtomicBoolean isThresholdExceed = new AtomicBoolean(false);

    protected static final List<Queueable> tempList = new ArrayList<>();



    protected AbsQueueConsumer(){
        this(new LinkedBlockingDeque<E>());
    }
    protected AbsQueueConsumer(BlockingQueue<E> queue){
        this(queue,System.getProperty("java.io.tmpdir"));
    }

    protected AbsQueueConsumer(BlockingQueue<E> queue,String serializePath){
        this(queue,serializePath,500,100,true);
    }

    protected AbsQueueConsumer(BlockingQueue<E> queue,String serializePath,int threadHoldMax,int threadHoldMin,boolean enableSerialize){
        this.queueSerializer = new DefaultQueueSerializer<Queueable>(serializePath+ File.separator+this.getClass().getSimpleName());
        this.retryStrategy = RetryStrategy.RetryStrategyFactory.getRetryStrategy();
        this.queue = queue;
        this.threadHoldMax = threadHoldMax;
        this.threadHoldMin = threadHoldMin;
        this.enableSerialize = enableSerialize;
    }

    /**
     * provide template
     */
    @Override
    public  final void consumeQueable(E queueable){
        try{
            //update queue task
            long start = System.currentTimeMillis();
            boolean isSuccess = queueable.process();
            long end= System.currentTimeMillis();
            if(end - start >3000){
                log.info("[消费者监听({})]:队列对象==> {} 运行时间太长,一共耗时  {}  毫秒,请注意!",this.getClass().getSimpleName(),
                        queueable.getClass().getSimpleName(),end-start);
            }
            //success?if not,need retry?
            if(!isSuccess){
                if(queueable.getCurrentProcessCount()<queueable.getMaxProcessCount()){
                    log.info("[消费者监听({})]队列对象:  {}  处理失败,开始重试,目前执行次数:{},最大重试次数:{}...,",this.getClass().getSimpleName(),
                            queueable.getClass().getSimpleName(),queueable.getCurrentProcessCount(),queueable.getMaxProcessCount());
                    //processCount +1
                    queueable.addProcessCount();
                    //retry
                    retryStrategy.retry(queueable,this);
                }
                else{
                    log.info("[消费者监听({})]队列对象:  {}  处理失败,,目前执行次数:{},最大重试次数:{},放弃执行!,",this.getClass().getSimpleName(),
                            queueable.getClass().getSimpleName(),queueable.getCurrentProcessCount(),queueable.getMaxProcessCount());
                }
            }
            //check
            //checkThreshHoldMin();
        }catch (Exception e){
            log.error("[消费者监听({})]出现异常:{}",this.getClass().getSimpleName(), LogUtil.getTrace(e));
        }
    }


    @Override
    public int getQueueSize(){
        return queue.size();
    }



    @Override
    public final List<E> toList() {
        return new ArrayList<E>(queue);
    }

    @Override
    public final void serializer(){
        if(this.enableSerialize && queue.size() > 0){
            log.info("[消费者监听({})].开始序列化队列对象,数目为:{},",this.getClass().getSimpleName(),queue.size());
            this.queueSerializer.serialize(this.toList());
        }
    }

    @Override
    public final void deserializer(){
        if(this.enableSerialize){
            List<Queueable> queueableList = this.queueSerializer.deserialize();
            if(queueableList.size()>0){
                log.info("[消费者监听({})].开始反序列化队列对象,数目为:{},",this.getClass().getSimpleName(),queueableList.size());
                for(Queueable queueable:queueableList){
                    //FIXME cast needed
                    this.addQueueable((E)queueable);
                }
            }
        }
    }



    @Override
    public void stop() {
        stop = true;
        this.serializer();
        log.info("[消费者监听({})]:停止运行!",this.getClass().getSimpleName());
    }

    @Override
    public void start() {
        stop = false;
        this.deserializer();
        log.info("[消费者监听({})]:开始运行!",this.getClass().getSimpleName());
    }


    @Override
    public boolean isStop() {
        return this.stop;
    }

    @Override
    public final boolean addQueueable(E queueable) {
        if(!stop){
            this.putToQueue(queueable);
            //TODO implements a concurrent list,not like copy on write,it need more write than read
/*            int size = this.getQueueSize();
            if(isThresholdExceed.get()){
                addToTempList(queueable);
            }
            //isThresholdExceed must be false here
            else if(size > this.threadHoldMax){
                //need not compete,and need not to be atomic
                isThresholdExceed.set(true);
                log.info("[消费者监听({})]:队列数目超过限制:{},开始转存硬盘!",this.getClass().getSimpleName(),threadHoldMax);
                this.addToTempList(queueable);
            }
            //put to queue
            else{
                this.putToQueue(queueable);
            }*/
            return true;
        }else{
            log.info("[消费者监听({})]:已经停止处理任务,不再接受新任务.",this.getClass().getSimpleName());
            return false;
        }
    }



    //....
    private void addToTempList(E queueable){
        List<E> newList = null;
        synchronized (tempList){
            tempList.add(queueable);
            if(tempList.size()>100){
                newList= new ArrayList<>();
                for(int i = 0,size = tempList.size();i<size;i++){
                    newList.add((E)tempList.remove(0));
                }
            }
        }
        if(newList!=null){
            this.queueSerializer.serialize(newList);
        }
    }


    private void checkThreshHoldMin(){
        int size = this.getQueueSize();
        //re-accept
        if(size<threadHoldMin&&isThresholdExceed.get()){
            isThresholdExceed.set(false);
            log.info("[消费者监听({})]:队列数目恢复正常;{},开始重新接受队列任务!",this.getClass().getSimpleName(),threadHoldMin);
            //requeue obj
            this.deserializer();
            //donot forget templist
            synchronized (tempList){
                for(int i = 0,num = tempList.size();i<num;i++){
                    this.addQueueable((E)tempList.get(i));
                }
            }
        }
    }
}
