package com.dg11185.util.concurrent.annotation;

import java.lang.annotation.*;

/**
 * Indicate that a class is  thread-safe only in the  thread who create them!
 * @author xiesp
 * @description
 * @date 3:01 PM  11/28/2017
 * @copyright 全国邮政电子商务运营中心
 */
@Target({ElementType.TYPE,ElementType.FIELD})
@Retention(RetentionPolicy.SOURCE)
@Documented
public @interface ThreadConfined {
}
