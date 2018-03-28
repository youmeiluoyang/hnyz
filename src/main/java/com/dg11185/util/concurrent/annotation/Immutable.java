package com.dg11185.util.concurrent.annotation;

import java.lang.annotation.*;

/**
 * Indicate that a class is Immutable(so is thread-safe)
 * @author xiesp
 * @description
 * @date 3:54 PM  10/27/2017
 * @copyright 全国邮政电子商务运营中心
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
@Documented
public @interface Immutable {
}
