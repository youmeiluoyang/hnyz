package com.dg11185.dao.da.sql.impl.reflect;

/**
 * @author xiesp
 * @description
 * @date 3:50 PM  12/1/2017
 * @copyright 全国邮政电子商务运营中心
 */
public enum ReflectType {

    INSERT("INSERT"),
    UPDATE("UPDATE"),
    DELETE("DELETE"),
    QUERY_BY_ID("QUERY_BY_ID"),
    NEXT_SEQ("NEXT_SEQ"),
    BATCH("BATCH"),
    BATCH_INSERT("BATCH_INSERT"),
    BATCH_UPDATE("BATCH_UPDATE")
    ;

    private String name;
    ReflectType(String name){
        this.name = name;
    }
}
