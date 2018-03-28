package com.dg11185.dao.da.sql.parser.operator.logic;

import com.dg11185.dao.da.sql.parser.operator.Operator;

/**
 * @author xiesp
 * @description
 * @date 8:40 AM  12/5/2017
 * @copyright 全国邮政电子商务运营中心
 */
public abstract class LogicOperator implements Operator {

    @Override
    public boolean operate(Object compare, String value) {
        boolean first = Boolean.parseBoolean(compare.toString());
        boolean second = Boolean.parseBoolean(value);
        return this.logicOperate(first,second);
    }

    /**
     *
     * @param first
     * @param second
     * @return
     */
    protected  abstract boolean logicOperate(boolean first,boolean second);
}
