package com.dg11185.util.queue.extension.delay;

import com.dg11185.util.queue.queueobj.Queueable;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * Transform from {@link Delayable} to built-in {@link  Delayed} obj
 * <q>and delegating the Queueable Obj</q>
 * @author xiesp
 * @description
 * @date 11:19 AM  10/10/2017
 * @copyright 全国邮政电子商务运营中心
 */
public class DelayItem implements Delayed,Queueable {


    private final Delayable delayQueueable;

    public DelayItem(Delayable delayQueueable){
        this.delayQueueable = delayQueueable;
    }


    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(this.getExecTime() - System.currentTimeMillis(),TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(Delayed o) {
        DelayItem that = (DelayItem)o;
        if(this.getExecTime() < that.getExecTime()){
            return  -1;
        }
        if(that.getExecTime() < this.getExecTime()){
            return 1;
        }
        return 0;
    }


    public long getExecTime() {
        return this.delayQueueable.getExecTime();
    }

    @Override
    public boolean process() {
        return delayQueueable.process();
    }

    @Override
    public boolean isUrgent() {
        return delayQueueable.isUrgent();
    }

    @Override
    public boolean isSequential() {
        return delayQueueable.isSequential();
    }

    @Override
    public int getMaxProcessCount() {
        return delayQueueable.getMaxProcessCount();
    }

    @Override
    public int getCurrentProcessCount() {
        return delayQueueable.getCurrentProcessCount();
    }

    @Override
    public void addProcessCount() {
        delayQueueable.addProcessCount();
    }
}
