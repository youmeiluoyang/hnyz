package com.dg11185.dao.da.sql.parser.operator.logic;

import com.dg11185.dao.da.sql.parser.operator.Operator;

/**
 * @author xiesp
 * @description
 * @date 8:39 AM  12/5/2017
 * @copyright 全国邮政电子商务运营中心
 */
public class AndOperator extends LogicOperator implements Operator {


    @Override
    protected boolean logicOperate(boolean first, boolean second) {
        return first && second;
    }
}
