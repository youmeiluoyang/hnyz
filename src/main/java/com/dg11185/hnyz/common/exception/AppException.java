package com.dg11185.hnyz.common.exception;

/**
 * 统一处理业务抛出的异常
 * @author xiesp
 * @description
 * @date 9:06 AM  8/14/2017
 * @copyright 全国邮政电子商务运营中心
 */
public class AppException extends RuntimeException {

    public AppException(String message){
        super(message);
    }

    public AppException(Throwable cause){
        super(cause);
    }

    public AppException(){
        super();
    }

    public AppException(String message, Throwable cause){
        super((new StringBuilder("发生异常,描述==>").append(message).toString()),cause);
    }



}
