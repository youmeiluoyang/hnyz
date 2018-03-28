package com.dg11185.dao.da.sql.impl.reflect;

import com.dg11185.dao.da.sql.Sql;
import com.dg11185.dao.da.sql.impl.AbstractSql;
import com.dg11185.dao.exception.SqlConvertException;

/**
 * Generating sql using Reflections!
 * @author xiesp
 * @description
 * @date 3:09 PM  12/1/2017
 * @copyright 全国邮政电子商务运营中心
 */
public abstract class AbstractReflectSql extends AbstractSql implements Sql{

    protected final Object object;
    protected final ReflectType reflectType;

    public AbstractReflectSql(Object o, ReflectType reflectType){
        this(o,reflectType,o.getClass());
    }

    public AbstractReflectSql(Object o, ReflectType reflectType,Class<?> resultClass){
        super(o instanceof Class?((Class) o).getSimpleName()+"-" + reflectType.name():o.getClass().getSimpleName()+ "-" +reflectType.name(),resultClass);
        this.object = o;
        this.reflectType = reflectType;
    }



    @Override
    public String getExecSql() {
        try{
            return generateSql();
        }catch (Exception e){
            StringBuilder sb = new StringBuilder("Error occured during generating sql for: ").
                    append(this.getClass().getSimpleName()).append(",type:").append(reflectType.name());
            throw new SqlConvertException(sb.toString(),e);
        }
    }


    /**
     * sub class to implement
     * params is null in most sub-classes ,but after generateSql is called,param may be presented
     * @return
     * @throws Exception
     */
    abstract String generateSql() throws Exception;

    //not dynamic
    @Override
    public boolean isDynamic() {
        return false;
    }

}

