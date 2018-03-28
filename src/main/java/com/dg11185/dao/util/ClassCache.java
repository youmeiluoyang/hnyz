package com.dg11185.dao.util;

import com.dg11185.dao.annotation.IdColumn;
import com.dg11185.dao.annotation.NotDbColumn;
import com.dg11185.dao.annotation.Table;
import com.dg11185.util.concurrent.annotation.Immutable;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Cache all the info for a class
 * @author xiesp
 * @description
 * @date 2:31 PM  11/28/2017
 * @copyright 全国邮政电子商务运营中心
 */
@Immutable
public class ClassCache {

    private final String tableName;
    private final IdColumn idCoulumn;
    private final String idColumnName;
    private final Map<String,Field> fieldMap = new HashMap<>();
    private final Class<?> clazz;
    private final Map<Field,NotDbColumn> noDbColumnMap = new HashMap<>();

    public ClassCache(Class<?> clazz){
        this.clazz = clazz;
        String idColumnTmp = null;
        String tableNameTmp = null;
        Field[] fields = clazz.getDeclaredFields();
        Table table = clazz.getAnnotation(Table.class);
        if(table!=null){
            tableNameTmp = table.value();
        }
        IdColumn idColumn = null;
        for(Field field:fields){
            field.setAccessible(true);
            if(idColumn == null){
                idColumn = field.getAnnotation(IdColumn.class);
                if(idColumn!=null){
                    idColumnTmp = field.getName();
                }
            }
            NotDbColumn noDbColumn = field.getAnnotation(NotDbColumn.class);
            if(noDbColumn!=null){
                noDbColumnMap.put(field,noDbColumn);
            }
            fieldMap.put(field.getName(),field);
        }
        this.idCoulumn = idColumn;
        this.tableName = tableNameTmp;
        this.idColumnName = idColumnTmp;
        Class<?> sueperClazz =clazz.getSuperclass();
        while (!sueperClazz.equals(Object.class)){
            for(Field field:sueperClazz.getDeclaredFields()){
                field.setAccessible(true);
                if(fieldMap.get(field.getName()) == null){
                    fieldMap.put(field.getName(),field);
                }
            }
            sueperClazz = sueperClazz.getSuperclass();
        }
    }

    public  Map<String,Field> getNotNullFields(Object object){
        Map<String,Field> valueMap = new HashMap<String,Field>();
        try {
            for(Map.Entry<String,Field> entry:this.getFieldMap().entrySet()){
                Field field = entry.getValue();
                Object value = field.get(object);
                if(value!=null){
                    valueMap.put(entry.getKey(),field);
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return valueMap;
    }



    /**
     * @return
     */
    public String getSeqName(){
        if(this.idCoulumn == null){
            return null;
        }
        return this.idCoulumn.value();
    }

    /**
     * Is a pojo field not a db column?
     * @param field
     * @return
     */
    public boolean isNoDbColumn(Field field){
        return noDbColumnMap.get(field) != null;
    }

    /**
     * returen the corresponding table name
     * @return
     */
    public String getTableName() {
        return tableName;
    }


    /**
     * return the correspond id field
     * @return
     */
    public String getIdColumnName() {
        return idColumnName;
    }

    /**
     * return all the fields
     * @return
     */
    public Map<String, Field> getFieldMap() {
        return fieldMap;
    }


    public Field getField(String fieldName){
        return fieldMap.get(fieldName);
    }
}
