package com.dg11185.dao.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicate that this Class is a pojo to a database table
 * @author xiesp
 * @description
 * @date 4:11 PM  10/27/2017
 * @copyright 全国邮政电子商务运营中心
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Table {

    /**
     * table name
     * @return
     */
    String value();
}
