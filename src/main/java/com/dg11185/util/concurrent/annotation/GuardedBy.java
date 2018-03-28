package com.dg11185.util.concurrent.annotation;

import java.lang.annotation.*;

/**
 * Used in Conditional thread-safe situation,indicated which monitor
 * it is using to provide thread-safe.
 * @author xiesp
 * @description
 * @date 3:47 PM  10/27/2017
 * @copyright 全国邮政电子商务运营中心
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
@Documented
public @interface GuardedBy {

    /**
     *  point out which variable is used as monitor
     * @return
     */
    String value();


    /**
     * describe sth
     * @return
     */
    String descn();
}
