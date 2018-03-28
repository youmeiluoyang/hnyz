package com.dg11185.dao.util;

import com.dg11185.dao.da.common.Pager;

/**
 * @author xiesp
 * @description
 * @date 7:31 PM  11/28/2017
 * @copyright 全国邮政电子商务运营中心
 */
public class SqlUtil {


    /**
     * wrap the current sql to be a count sql
     * @param sql
     * @return
     */
    public static String getCountSql(String sql){
        StringBuilder sb = new StringBuilder("select count(1) from (").append(sql).append(")");
        return sb.toString();
    }


    /**
     * wrap the current sql to be a page sql
     * @param sql
     * @return
     */
    public static String getPageSql(String sql, Pager pager){
        int begin = (pager.getCurPage()-1) * pager.getPageSize();
        int end = begin + pager.getPageSize();
        StringBuilder sb = new StringBuilder("select * from (select t1.*, rownum num from (")
                .append(sql).append(") t1) where num > ").append(begin).append(" and num <=").append(end);
        return sb.toString();
    }


    /**
     * return the parameter in the Sql Object type
     */
    public static String wrapSqlParameter(String parameterName){
        return (new StringBuilder("#")).append(parameterName).append("#").toString();
    }

    /**
     *
     */
    public static String extractSqlParameter(String wrapParameter){
        return wrapParameter.substring(1,wrapParameter.length() -1);
    }

    /**
     *
     */
    public static boolean testSqlParameter(String parameter){
        return parameter.length()>1 && parameter.startsWith("#") && parameter.endsWith("#");
    }
}
