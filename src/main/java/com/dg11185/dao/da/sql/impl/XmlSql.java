package com.dg11185.dao.da.sql.impl;

import com.dg11185.dao.da.sql.Sql;
import com.dg11185.dao.da.sql.sqlelement.SqlElement;
import com.dg11185.dao.da.sql.sqlelement.dynamic.SqlDynamic;
import com.dg11185.dao.da.sql.sqlelement.dynamic.result.DynamicResult;
import com.dg11185.util.concurrent.annotation.Immutable;

import java.util.List;

/**
 * @author xiesp
 * @description
 * @date 3:59 PM  10/27/2017
 * @copyright 全国邮政电子商务运营中心
 */
@Immutable
public class XmlSql extends AbstractDynamicSql implements Sql{

    //SqlElement
    private final List<SqlElement> elementList;
    //the return type


    /**
     * Normal Constructor,Fully decomposition procedure,that is,every condition is to be examined
     * @param id
     * @param resultClass
     */
    public XmlSql(String id, Class<?> resultClass, List<SqlElement> sqlElements){
        super(id,resultClass);
        this.elementList = sqlElements;
    }



    @Override
    public String getExecSql(){
        StringBuilder sb = new StringBuilder();
        for(SqlElement sqlElement:elementList){
            if(sqlElement.isDynamic()){
                if(this.param == null){
                    continue;
                }
                SqlDynamic sqlDynamic = (SqlDynamic)sqlElement;
                DynamicResult result = sqlDynamic.getResult(param.getParamMap());
                if(result.isPass()){
                    sb.append(result.getPassText());
                }
            }else{
                sb.append(sqlElement.getText());
            }
        }
        return sb.toString();
    }



    public XmlSql cloneSql(){
        return  new XmlSql(this.id,this.resultClass,this.elementList);
    }


    @Override
    public boolean isDynamic() {
        return true;
    }

    /**
     * readable text
     * @return
     */
    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        for(SqlElement sqlElement:elementList){
            stringBuilder.append(sqlElement.getText());
        }
        return stringBuilder.toString();
    }

}
