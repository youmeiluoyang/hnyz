package com.dg11185.dao.exception;

/**
 * Base Dao Exception,all the other exceptions will inherit from this one
 * @author xiesp
 * @description
 * @date 10:14 AM  11/29/2017
 * @copyright 全国邮政电子商务运营中心
 */
public class BaseDaoException extends RuntimeException{

    public BaseDaoException(String msg){
        super(msg);
    }


    public BaseDaoException(Exception e){
        super(e);
    }


    public BaseDaoException(String msg,Exception e){
        super(msg,e);
    }


}
