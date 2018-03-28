package com.dg11185.dao.da.sql.sqlelement.dynamic.impl;

import com.dg11185.dao.da.sql.sqlelement.dynamic.AbstractDynamic;
import com.dg11185.dao.da.sql.sqlelement.dynamic.SqlDynamic;
import com.dg11185.dao.da.sql.sqlelement.dynamic.result.DefaultDynamicResult;
import com.dg11185.dao.da.sql.sqlelement.dynamic.result.DynamicResult;

import java.util.List;
import java.util.Map;

/**
 * ChooseDynamic is in chain-mode
 * @author xiesp
 * @description
 * @date 12:40 PM  12/1/2017
 * @copyright 全国邮政电子商务运营中心
 */
public class ChooseDynamic extends AbstractDynamic implements SqlDynamic{


    public ChooseDynamic(List<SqlDynamic> list){
        super("choose","","",true,list);
    }

    @Override
    public DynamicResult getResult(Map<String, Object> paramMap) {
        for(SqlDynamic sqlDynamic:chainList){
            DynamicResult result = sqlDynamic.getResult(paramMap);
            if(result.isPass()){
                return result;
            }
        }
        return new DefaultDynamicResult(false,null);
    }

}
