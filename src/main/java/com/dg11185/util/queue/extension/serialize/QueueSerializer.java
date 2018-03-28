package com.dg11185.util.queue.extension.serialize;

import java.util.List;

/**
 * Interface that define serialize/deserialize behaviour for queue obj
 * @author xiesp
 * @description
 * @date 10:42 AM  10/11/2017
 * @copyright 全国邮政电子商务运营中心
 */
public interface QueueSerializer<T> {

    /**
     * serialize a queue obj to disk
     * @param queueableList
     */
    void serialize(List<T> queueableList);


    /**
     * deserialize  all queue obj into list
     * @return
     */
    List<T> deserialize();

}
