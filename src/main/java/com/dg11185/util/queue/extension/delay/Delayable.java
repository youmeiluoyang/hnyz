package com.dg11185.util.queue.extension.delay;

import com.dg11185.util.queue.queueobj.Queueable;

/**
 * This interface extend the  {@link Queueable} interface to
 * support delayd execution
 * @author xiesp
 * @description
 * @date 3:06 PM  9/21/2017
 * @copyright 全国邮政电子商务运营中心
 */
public interface Delayable extends Queueable {


    /**
     * 返回以时间戳的代表的需要运行的时间
     * @return
     */
    long getExecTime();
}
