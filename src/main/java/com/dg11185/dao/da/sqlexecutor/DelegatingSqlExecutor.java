package com.dg11185.dao.da.sqlexecutor;

import com.dg11185.dao.da.common.Pager;
import com.dg11185.dao.da.sql.Sql;
import com.dg11185.util.concurrent.annotation.ThreadConfined;

import java.util.List;

/**
 * This Class is intended to delegating all functionality to another SqlExecutor.
 * Mainly for a already-existed frameWork to cooperate with This little Dao library
 * @author xiesp
 * @description
 * @date 4:51 PM  10/27/2017
 * @copyright 全国邮政电子商务运营中心
 */
public class DelegatingSqlExecutor implements SqlExecutor {

    //hb principles will promise thread-safe
    @ThreadConfined
    protected volatile   SqlExecutor delegatingSqlExecutor;




    @Override
    public <T> long getNextSeqVal(Class<T> classType) {
        return delegatingSqlExecutor.getNextSeqVal(classType);
    }

    @Override
    public <E> E queryById(Object object, String keyColumn) {
        return delegatingSqlExecutor.queryById(object, keyColumn);
    }

    @Override
    public <T> int insert(T obj) {
        return delegatingSqlExecutor.insert(obj);
    }

    @Override
    public <T> int update(T obj, String keyColumn) {
        return delegatingSqlExecutor.update(obj, keyColumn);
    }

    @Override
    public <T> int delete(T obj, String keyColumn) {
        return delegatingSqlExecutor.delete(obj, keyColumn);
    }

    @Override
    public <E> List<E> queryForList(Sql sql) {
        return delegatingSqlExecutor.queryForList(sql);
    }

    @Override
    public <E> List<E> queryForListWithPage(Sql sql, Pager pager) {
        return delegatingSqlExecutor.queryForListWithPage(sql, pager);
    }

    @Override
    public <E> E queryForObject(Sql sql) {
        return delegatingSqlExecutor.queryForObject(sql);
    }

    @Override
    public int count(Sql sql) {
        return delegatingSqlExecutor.count(sql);
    }

    @Override
    public int update(Sql sql) {
        return delegatingSqlExecutor.update(sql);
    }

    @Override
    public <T> int[] batchUpdate(List<T> objs, String keyColumn) {
        return delegatingSqlExecutor.batchUpdate(objs, keyColumn);
    }

    @Override
    public <T> int[] batchInsert(List<T> objs) {
        return delegatingSqlExecutor.batchInsert(objs);
    }




}
