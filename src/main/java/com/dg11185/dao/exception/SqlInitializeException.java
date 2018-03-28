package com.dg11185.dao.exception;

/**
 * @author xiesp
 * @description
 * @date 6:18 PM  11/28/2017
 * @copyright 全国邮政电子商务运营中心
 */
public class SqlInitializeException extends BaseDaoException{
    public SqlInitializeException(String msg){
        super(msg);
    }


    public SqlInitializeException(String msg,Exception e){
        super(msg,e);
    }
}
