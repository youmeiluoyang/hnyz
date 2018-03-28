package com.dg11185.dao.da.sqlexecutor;

import com.dg11185.dao.da.common.Pager;
import com.dg11185.dao.da.sql.Sql;

import java.util.List;

/**
 *
 *
 * @author xiesp
 * @description
 * @date 4:02 PM  10/27/2017
 * @copyright 全国邮政电子商务运营中心
 */
public interface SqlExecutor {


    /**
     * @param classType
     * @param <T>
     * @return
     */
    <T> long getNextSeqVal(Class<T> classType);




    /**
     * @param object
     * @param keyColumn
     * @return
     */
    <E> E queryById(Object object, String keyColumn);


    /**
     * Insert a obj to db.Note that is the IdColumn is specified and the idField is null,
     * then this field will automatically be set,otherwise will insert all the not null field
     * @param <T>
     * @param obj
     * @return
     */
    <T> int insert(T obj);


    /**
     * Update all NOT null fields to db.<br>
     * Note:if keyColumn is null,then will use obj's idColumn as keyColumn.
     * @param obj
     * @param keyColumn
     * @param <T>
     * @return
     */
    <T> int update(T obj, String keyColumn);


    /**
     * @param obj
     * @param keyColumn
     * @param <T>
     * @return
     */
    <T> int delete(T obj, String keyColumn);


    /**
     *
     * @param sql
     * @return
     */
    <E> List<E> queryForList(Sql sql);


    /**
     *
     * @param sql
     * @param pager
     * @return
     */
    <E> List<E> queryForListWithPage(Sql sql, Pager pager);


    /**
     *
     * @param sql
     * @return
     */
    <E> E queryForObject(Sql sql);

    /**
     *
     * @param sql
     * @return
     */
    int count(Sql sql);


    /**
     * @param sql
     * @return
     */
    int update(Sql sql);



    /**
     * @param <T>
     * @param objs
     * @param keyColumn
     * @return
     */
    <T> int[] batchUpdate(List<T> objs, String keyColumn);


    /**
     * @param <T>
     * @param objs
     * @return
     */
    <T> int[] batchInsert(List<T> objs);



}
