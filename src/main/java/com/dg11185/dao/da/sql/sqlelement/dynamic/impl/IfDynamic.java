package com.dg11185.dao.da.sql.sqlelement.dynamic.impl;

import com.dg11185.dao.Constants;
import com.dg11185.dao.da.sql.parser.Parser;
import com.dg11185.dao.da.sql.parser.impl.CompositeParser;
import com.dg11185.dao.da.sql.parser.impl.SimpleParser;
import com.dg11185.dao.da.sql.parser.operator.OperatorFactory;
import com.dg11185.dao.da.sql.parser.operator.logic.LogicOperator;
import com.dg11185.dao.da.sql.sqlelement.dynamic.AbstractDynamic;
import com.dg11185.dao.da.sql.sqlelement.dynamic.SqlDynamic;
import com.dg11185.dao.da.sql.sqlelement.dynamic.result.DefaultDynamicResult;
import com.dg11185.dao.da.sql.sqlelement.dynamic.result.DynamicResult;
import com.dg11185.dao.exception.SqlInitializeException;
import com.dg11185.dao.util.SqlUtil;
import com.dg11185.util.common.StrUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author xiesp
 * @description
 * @date 7:01 PM  11/30/2017
 * @copyright 全国邮政电子商务运营中心
 */
public class IfDynamic extends AbstractDynamic implements SqlDynamic {


    protected final Parser parser;

    public IfDynamic(String condition,String passText){
        this("if",condition,passText);
    }

    public IfDynamic(String qName,String condition,String passText){
        super(qName,condition,passText,false,null);
        //add whitespace between arithmetic operator,first <= and => because < and > conflict with those
        String testText = condition.replaceAll("(?:>=|<=|!=|==|<|>)"," $0 ");
        //then spilt with whitespaces
        String[] arr = testText.split(StrUtil.WHITE_SPACES_RE);
        List<Parser> parsers = new ArrayList<>();
        List<LogicOperator> operators = new ArrayList<>();
        for(int i = 0,len=arr.length;i<len;i++){
            String key = arr[i].trim();
            if(key.matches(Constants.ARITHMETIC_OPERATORS_RE)){
                String field = arr[i-1];
                String compare = arr[i+1];
                String[] deter = this.determinField(field,compare);
                field = deter[0];
                compare = deter[1];
                field= SqlUtil.extractSqlParameter(field);
                Parser sp = new SimpleParser(field,key,compare);
                parsers.add(sp);
                i+=1;
            }else if(key.matches(Constants.LOGIC_OPERATORS_RE)){
                //find next arithmetic expression
                String nextField = arr[i+1];
                String nextOperator = arr[i+2];
                String nextCompare = arr[i+3];
                String[] deter = this.determinField(nextField,nextCompare);
                nextField = deter[0];
                nextCompare = deter[1];
                nextField= SqlUtil.extractSqlParameter(nextField);
                Parser sp = new SimpleParser(nextField,nextOperator,nextCompare);
                parsers.add(sp);
                operators.add((LogicOperator)OperatorFactory.getOperator(key));
                i+=3;
            }
        }
        Parser parser = new CompositeParser(parsers,operators);
        this.parser = parser;
    }



    private String[] determinField(String field,String value){
        boolean first = SqlUtil.testSqlParameter(field);
        boolean second = SqlUtil.testSqlParameter(value);
        if(first ^ second){
            if(second){
                String tmp = field;
                field = value;
                value = tmp;
            }
            return new String[]{field,value};
        }else{
            throw new SqlInitializeException(this.qName + " SqlDynamic test expression not correct!");
        }
    }

    @Override
    public DynamicResult getResult(Map<String, Object> paramMap) {
        boolean flag = parser.parse(paramMap);
        DynamicResult dynamicResult = new DefaultDynamicResult(flag,passText);
        return dynamicResult;
    }
}
