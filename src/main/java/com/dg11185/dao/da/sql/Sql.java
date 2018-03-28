package com.dg11185.dao.da.sql;

import com.dg11185.dao.da.sql.conveter.ConvertResult;
import com.dg11185.dao.da.sql.conveter.SqlParam;

import java.util.Map;

/**
 * this class represent a sql clause that is to be executed soon.
 * By using CloneSql method,this class is intended to server as an Immutable Object though it is essential not.
 * @author xiesp
 * @description
 * @date 3:01 PM  12/1/2017
 * @copyright 全国邮政电子商务运营中心
 */
public interface Sql {

    /**
     * return unique id
     * @return
     */
    String getId();


    /**
     * the result Class
     * @return
     */
    Class<?> getResultClass();


    /**
     * return executable sql
     * @return
     */
    String getExecSql();


    /**
     * For concurrency Purpose
     * @return
     */
    Sql cloneSql();


    /**
     * Dynamic Sql?
     * @return
     */
    boolean isDynamic();


    /**
     * set params via
     * @param paramMap
     */
    void setParam(Map<String, Object> paramMap);



    /**
     * set params via object using reflection,null fields will be ignored
     * @param object
     */
    void setParam(Object object);


    /**
     * return param
     * @return
     */
    SqlParam getParam();

    /**
     *
     * @return
     */
    boolean isHasParam();

    /**
     *
     * @param hasParam
     */
    void setHasParam(boolean hasParam);


    /**
     * final step,we should convert the parameters
     * @return
     */
    ConvertResult getConvertedResult();


}
