package com.dg11185.dao.da.sql.parser.operator.impl;

import com.dg11185.dao.da.sql.parser.operator.Operator;
import com.dg11185.util.common.StrUtil;

/**
 * @author xiesp
 * @description
 * @date 10:22 AM  12/1/2017
 * @copyright 全国邮政电子商务运营中心
 */
public class NotEqOperator implements Operator {

    @Override
    public boolean operate(Object compare, String value) {

        if(StrUtil.isNullInText(value)){
            return compare != null;
        }
        if(StrUtil.isInteger(value)){
            long c ;
            long v = Long.parseLong(value);
            if(compare instanceof String){
                c = Long.parseLong(compare.toString());
            }else{
                c = (Long) compare;
            }
            return c != v;
        }
        return false;
    }
}
