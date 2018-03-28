package com.dg11185.dao.exception;

/**
 * @author xiesp
 * @description
 * @date 8:42 AM  11/30/2017
 * @copyright 全国邮政电子商务运营中心
 */
public class SqlConvertException extends BaseDaoException{

    public SqlConvertException(String msg){
        super(msg);
    }

    public SqlConvertException(String msg,Exception e){
        super(msg,e);
    }
}
