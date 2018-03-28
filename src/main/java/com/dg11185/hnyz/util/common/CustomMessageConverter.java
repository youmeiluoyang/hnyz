package com.dg11185.hnyz.util.common;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.server.ServletServerHttpResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;

/**
 * 为了个性化Spring调用MessageConverter返回结果,不要总是返回JSON
 */
public class CustomMessageConverter extends FastJsonHttpMessageConverter{

    private  static final String SUCCESS = "success";
    private  static final String EMPTY = "";
    @Override
    public void write(Object t, Type type, MediaType contentType, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        if(t instanceof String){
            String mess = (String)t;
            if(SUCCESS.equals(mess) || EMPTY.equals(mess)){
                ServletServerHttpResponse response =  (ServletServerHttpResponse)outputMessage;
                PrintWriter writer = response.getServletResponse().getWriter();
                try{
                    writer.write(mess);
                    writer.flush();
                }finally {
                    writer.close();
                }
            }else{
                super.write(t,type,contentType,outputMessage);
            }
        }else{
            super.write(t,type,contentType,outputMessage);
        }
    }
}
