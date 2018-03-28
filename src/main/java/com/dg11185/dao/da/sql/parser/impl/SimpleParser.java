package com.dg11185.dao.da.sql.parser.impl;

import com.dg11185.dao.da.sql.parser.AbstractParser;
import com.dg11185.dao.da.sql.parser.Parser;
import com.dg11185.dao.da.sql.parser.operator.Operator;
import com.dg11185.dao.da.sql.parser.operator.OperatorFactory;

import java.util.Map;

/**
 * @author xiesp
 * @description
 * @date 9:46 AM  12/1/2017
 * @copyright 全国邮政电子商务运营中心
 */
public class SimpleParser extends AbstractParser implements Parser{

    protected final String compareField;
    protected final Operator operatopr;
    protected final String compareValue;
    public SimpleParser(String compareField, String operator,String compareValue){
        this.compareField = compareField;
        this.operatopr = OperatorFactory.getOperator(operator);
        this.compareValue = compareValue;
    }




    @Override
    public boolean parse(Map<String, Object> paramMap) {
        Object fieldValue = paramMap.get(this.compareField);
        if(this.operatopr.operate(fieldValue,this.compareValue)){
            return true;
        }
        return false;
    }
}
