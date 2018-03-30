package com.dg11185.hnyz.common.exception;

/**
 * 抛出调用微信接口出现的异常
 * @author xiesp
 * @description
 * @date 9:10 AM  8/14/2017
 */
public class WxCallException extends AppException{

    public WxCallException(String message, String errcode, String descn){
        super(new StringBuilder("调用微信接口出现错误,信息:==> ").append(message).append(", 错误码:==>  ").
                append(errcode).append(", 业务描述:==> ").append(descn).toString());
    }
}
