package com.dg11185.dao.da.sql.conveter;

import com.dg11185.dao.da.sql.Sql;

/**
 * @author xiesp
 * @description
 * @date 4:41 PM  11/28/2017
 * @copyright 全国邮政电子商务运营中心
 */
public interface ParameterConverter {


    /**
     * convert All sql
     * @param sql
     * @return
     */
    ConvertResult convertSql(Sql sql);


/*    *//**
     * convert base sql,because in some situation,we only need the base sql,ie,under batch condition
     *//*
    String convertBaseSql(Sql sql);*/
}
