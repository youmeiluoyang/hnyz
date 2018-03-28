package com.dg11185.dao.da.sql.sqlelement.dynamic.impl;

import com.dg11185.dao.da.sql.sqlelement.dynamic.AbstractDynamic;
import com.dg11185.dao.da.sql.sqlelement.dynamic.SqlDynamic;
import com.dg11185.dao.da.sql.sqlelement.dynamic.result.DefaultDynamicResult;
import com.dg11185.dao.da.sql.sqlelement.dynamic.result.DynamicResult;

import java.util.Map;

/**
 * @author xiesp
 * @description
 * @date 12:44 PM  12/1/2017
 * @copyright 全国邮政电子商务运营中心
 */
public class OtherwiseDynamic extends AbstractDynamic implements SqlDynamic{

    public OtherwiseDynamic(String passText){
        super("otherwise",null,passText,false,null);
    }

    //always true
    @Override
    public DynamicResult getResult(Map<String, Object> paramMap) {
        return new DefaultDynamicResult(true,passText);
    }
}
