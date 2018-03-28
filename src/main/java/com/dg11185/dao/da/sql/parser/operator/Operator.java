package com.dg11185.dao.da.sql.parser.operator;

/**
 * @author xiesp
 * @description
 * @date 10:01 AM  12/1/2017
 * @copyright 全国邮政电子商务运营中心
 */
public interface Operator {


    /**
     *
     * @param compare
     * @param value
     * @return
     */
    boolean operate(Object compare, String value);
}
