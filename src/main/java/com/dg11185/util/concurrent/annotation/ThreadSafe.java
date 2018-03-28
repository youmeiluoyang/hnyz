package com.dg11185.util.concurrent.annotation;

import java.lang.annotation.*;

/**
 *
 * Indicate that a class is thread-safe in any condition
 * @author xiesp
 * @description
 * @date 3:41 PM  10/27/2017
 * @copyright 全国邮政电子商务运营中心
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
@Documented
public @interface ThreadSafe {

    /**
     * Maybe sometimes need to state sth?
     * @return
     */
    String descn() default "";
}
