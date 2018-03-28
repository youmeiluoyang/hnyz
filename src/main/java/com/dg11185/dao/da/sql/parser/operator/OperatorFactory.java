package com.dg11185.dao.da.sql.parser.operator;

import com.dg11185.dao.da.sql.parser.operator.impl.*;
import com.dg11185.dao.da.sql.parser.operator.logic.AndOperator;
import com.dg11185.dao.da.sql.parser.operator.logic.OrOpreator;

/**
 * @author xiesp
 * @description
 * @date 10:25 AM  12/1/2017
 * @copyright 全国邮政电子商务运营中心
 */
public class OperatorFactory {

    private static final Operator EQ = new EqOperator();
    private static final Operator NOT_EQ = new NotEqOperator();
    private static final Operator GT = new GtOperator();
    private static final Operator LT= new LtOperator();
    private static final Operator GTEQ= new GtEqOperator();
    private static final Operator LTEQ= new LtEqOperator();
    private static final Operator AND= new AndOperator();
    private static final Operator OR= new OrOpreator();


    public static Operator getOperator(String key){
        if("==".equals(key)){
            return  EQ;
        }else if("!=".equals(key)){
            return NOT_EQ;
        }else if(">".equals(key)){
            return GT;
        }else if("<".equals(key)){
            return LT;
        }else if("<=".equals(key)){
            return LTEQ;
        }else if(">=".equals(key)){
            return GTEQ;
        }else if("and".equalsIgnoreCase(key)){
            return AND;
        }else if("or".equalsIgnoreCase(key)){
            return OR;
        }
        throw new RuntimeException("operator for '" +key +"' not found!");
    }
}
