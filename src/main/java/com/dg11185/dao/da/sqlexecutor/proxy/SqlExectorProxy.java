package com.dg11185.dao.da.sqlexecutor.proxy;

import com.dg11185.dao.da.sqlexecutor.SqlExecutor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author xiesp
 * @description
 * @date 2:26 PM  12/4/2017
 * @copyright 全国邮政电子商务运营中心
 */
public class SqlExectorProxy implements InvocationHandler{
    private Object proxiedSqlExecutor;


    public void setProxiedSqlExecutor(SqlExecutor sqlExecutor){
        this.proxiedSqlExecutor = SqlExectorProxy.getProxyInstance(sqlExecutor);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = null;
        System.out.println("before");
        try{
            result  =method.invoke(proxiedSqlExecutor,args);
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("after");
        return null;
    }

    public static   SqlExecutor getProxyInstance(SqlExecutor sqlExecutor){
        Class<?> clazz = sqlExecutor.getClass();
        while (clazz.getInterfaces().length == 0){
            clazz = clazz.getSuperclass();
        }
        ClassLoader classLoader = clazz.getClassLoader();
        Class<?>[] interfaces = clazz.getInterfaces();
        Object object = Proxy.newProxyInstance(classLoader, interfaces, new SqlExectorProxy());
        SqlExecutor proxied = (SqlExecutor)object;
        return  proxied;
    }
}
