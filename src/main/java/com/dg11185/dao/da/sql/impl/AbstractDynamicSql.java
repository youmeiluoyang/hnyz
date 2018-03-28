package com.dg11185.dao.da.sql.impl;

import com.dg11185.dao.da.sql.Sql;

/**
 * @author xiesp
 * @description
 * @date 3:15 PM  12/1/2017
 * @copyright 全国邮政电子商务运营中心
 */
public abstract class AbstractDynamicSql extends AbstractSql implements Sql {

    protected AbstractDynamicSql(String id,Class<?> clazz){
        super(id,clazz);
    }

}
