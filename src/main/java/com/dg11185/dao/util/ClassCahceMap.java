package com.dg11185.dao.util;

import com.dg11185.dao.da.config.DaoConfig;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author xiesp
 * @description
 * @date 2:00 PM  11/28/2017
 * @copyright 全国邮政电子商务运营中心
 */
public class ClassCahceMap {

    private static final ConcurrentMap<Class<?>,ClassCache> CLASS_CACHE_MAP = new ConcurrentHashMap<>();

    public static ClassCache getClassCache(Object object){
        return getClassCache(object.getClass());
    }


    public static ClassCache getClassCache(Class<?> clazz){
        if(DaoConfig.isDebug()){
            return new ClassCache(clazz);
        }else{
            ClassCache classCache = CLASS_CACHE_MAP.get(clazz);
            if(classCache == null){
                classCache = new ClassCache(clazz);
                CLASS_CACHE_MAP.putIfAbsent(clazz,classCache);
            }
            return  classCache;
        }
    }

    public static Map<String,Object> getAllNotNullFileValues(Object object){
        ClassCache classCache = getClassCache(object);
        Map<String,Object> valueMap = new HashMap<String,Object>();
        try {
            for(Map.Entry<String,Field> entry:classCache.getFieldMap().entrySet()){
                Field field = entry.getValue();
                Object value = field.get(object);
                if(value!=null){
                    valueMap.put(entry.getKey(),value);
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return valueMap;
    }

}

