package com.dg11185.hnyz.util;

import java.io.PrintWriter;
import java.io.StringWriter;


/**
 * 定义系统日志相关常用方法类
 *
 * @author xdweleven
 * @version 1.0
 * @since 2016-10-27
 */
public class LogUtil {

    /**
     * 将完整异常栈转换成字符串返回
     *
     * @param t 异常对象
     * @return 返回异常栈字符串
     */
    public static String getTrace(Throwable t) {
        StringWriter stringWriter= new StringWriter();
        PrintWriter writer= new PrintWriter(stringWriter);
        t.printStackTrace(writer);
        StringBuffer buffer= stringWriter.getBuffer();
        return buffer.toString();
    }


}
