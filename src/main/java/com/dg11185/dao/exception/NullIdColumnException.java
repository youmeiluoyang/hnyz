package com.dg11185.dao.exception;

/**
 * @author xiesp
 * @description
 * @date 3:52 PM  11/28/2017
 * @copyright 全国邮政电子商务运营中心
 */
public class NullIdColumnException extends BaseDaoException{


    public NullIdColumnException(String msg){
        super(msg);
    }
}
