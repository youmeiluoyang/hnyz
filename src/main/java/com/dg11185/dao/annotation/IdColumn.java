package com.dg11185.dao.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicate that this filed is the id column mapping to the table
 * and the sequence correspond to this id
 * @author xiesp
 * @description
 * @date 4:10 PM  10/27/2017
 * @copyright 全国邮政电子商务运营中心
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface IdColumn {


    /**
     * Sequence Name
     * @return
     */
    String value();
}
