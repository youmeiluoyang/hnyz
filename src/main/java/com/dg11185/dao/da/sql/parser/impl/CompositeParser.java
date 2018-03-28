package com.dg11185.dao.da.sql.parser.impl;

import com.dg11185.dao.da.sql.parser.AbstractParser;
import com.dg11185.dao.da.sql.parser.Parser;
import com.dg11185.dao.da.sql.parser.operator.logic.LogicOperator;

import java.util.List;
import java.util.Map;

/**
 * @author xiesp
 * @description
 * @date 8:45 AM  12/5/2017
 * @copyright 全国邮政电子商务运营中心
 */
public class CompositeParser extends AbstractParser implements Parser {

    private final List<Parser> parserList;
    private final List<LogicOperator> operators;
    public CompositeParser(List<Parser> parsers, List<LogicOperator> operators){
        this.parserList = parsers;
        this.operators = operators;
    }

    @Override
    public boolean parse(Map<String, Object> paramMap) {

        if(parserList.size()==1){
            return parserList.get(0).parse(paramMap);
        }else{
            boolean first = parserList.get(0).parse(paramMap);
            for(int i = 1;i<parserList.size();i++){
                boolean second = parserList.get(i).parse(paramMap);
                LogicOperator operator = operators.get(i-1);
                //result as the first to run next compare
                first= operator.operate(first,second+"");
            }
            return first;

        }
    }
}
