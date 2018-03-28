package com.dg11185.dao.da.sql.impl;

import com.dg11185.dao.da.sql.Sql;
import com.dg11185.util.concurrent.annotation.Immutable;

/**
 * PlainText Sql
 * @author xiesp
 * @description
 * @date 3:11 PM  12/1/2017
 * @copyright 全国邮政电子商务运营中心
 */
@Immutable
public class PlainSql extends AbstractSql implements Sql{

    private final String plainSql;

    public PlainSql(String id,Class<?> resultClass,String plainSql){
        super(id,resultClass);
        this.plainSql = plainSql;
    }

    @Override
    public String getExecSql() {
        return this.plainSql;
    }

    @Override
    public Sql cloneSql() {
        return new PlainSql(this.id,this.resultClass,this.plainSql);
    }

    @Override
    public boolean isDynamic() {
        return false;
    }

}
