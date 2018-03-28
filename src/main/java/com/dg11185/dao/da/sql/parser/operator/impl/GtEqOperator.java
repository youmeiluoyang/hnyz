package com.dg11185.dao.da.sql.parser.operator.impl;

import com.dg11185.dao.da.sql.parser.operator.Operator;
import com.dg11185.dao.da.sql.parser.operator.OperatorFactory;

/**
 * @author xiesp
 * @description
 * @date 2:51 PM  12/1/2017
 * @copyright 全国邮政电子商务运营中心
 */
public class GtEqOperator implements Operator {


    private final Operator gt = OperatorFactory.getOperator(">");
    private final Operator eq = OperatorFactory.getOperator("==");

    @Override
    public boolean operate(Object compare, String value) {
        return gt.operate(compare,value) || eq.operate(compare,value);
    }
}
