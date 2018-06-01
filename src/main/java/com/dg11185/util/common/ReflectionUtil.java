package com.dg11185.util.common;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 扩展JAVA对象的反射机制
 * 
 * @author xdweleven
 * @version 1.0
 * @since 2016-09-18
 * @Copyright 2016 全国邮政电子商务运营中心 All rights reserved.
 */
public class ReflectionUtil {


	public static boolean isClassPrimitive(Class<?> clazz){
		return clazz.equals(Integer.class) || clazz.equals(Long.class) || clazz.equals(Double.class)
				|| clazz.equals(Float.class);
	}


	/**
	 * 设置对象的指定属性名称的属性值
     *
	 * @param obj 待设置的对象
	 * @param fieldName 对象属性名称
	 * @param value 属性值
	 */
	public static void setFieldValue(Object obj, String fieldName, Object value) {
		Class<? extends Object> c = obj.getClass();
		try {
			Field field = null;
			Field[] fields = c.getDeclaredFields();
			for(int i = 0; i < fields.length; i++){
				String fieldNameTemp = fields[i].getName();
				if(fieldNameTemp.equalsIgnoreCase(fieldName)){
					field = c.getDeclaredField(fieldNameTemp);
					field.setAccessible(true);
					field.set(obj, value);
					return;
				}
			}	
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 获取对象的指定属性名称的属性值
     *
	 * @param obj 待设置的对象
	 * @param fieldName 对象属性名称
	 */
	public static Object getFieldValue(Object obj, String fieldName) {
		Class<? extends Object> c = obj.getClass();
		Field[] fields = c.getDeclaredFields();
		try {
			for(int i = 0; i < fields.length; i++){
				if(fields[i].getName().equalsIgnoreCase(fieldName)){
					fields[i].setAccessible(true);
					return fields[i].get(obj);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return null;
	}

	/**
	 * 获取对象的属性值不为空的属性名称
     *
	 * @param obj 待获取的对象
	 * @return 返回属性值不为空的对象的属性名称列表
	 */
	public static List<Field> getNotNullField(Object obj) {
		Class<? extends Object> c = obj.getClass();
		List<Field> list = new ArrayList<Field>();
		try {
			Field[] fields = c.getDeclaredFields();
			for(int i = 0; i < fields.length; i++){
				fields[i].setAccessible(true);
				if(fields[i].get(obj) != null){
					list.add(fields[i]);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return list;
	}


	/**
	 * 所有的非空字段到map
	 * @return 返回类属性列表
	 */
	public static Map<String,Object> toMap(Object obj){
		Class<?> c  = obj.getClass();
		Map<String,Object> map = new java.util.HashMap<String,Object>();
		try{
			while(c!=null){
				Field[] fields = c.getDeclaredFields();
				for(int i = 0,len = fields.length; i < len; i++){
					fields[i].setAccessible(true);
					if(fields[i].get(obj) != null){
						map.put(fields[i].getName(),fields[i].get(obj));
					}
				}
				c = c.getSuperclass();
			}
		}catch (Exception e){
			throw  new RuntimeException(e);
		}
		return map;
	}


    /**
     * 获取对象的属性名称
     *
     * @param obj 待获取的对象
     * @return 返回属性值不为空的对象的属性名称列表
     */
    public static List<Field> getAllField(Object obj) {
        Class<? extends Object> c = obj.getClass();
        List<Field> list = new ArrayList<Field>();
        try {
            Field[] fields = c.getDeclaredFields();
            for(int i = 0; i < fields.length; i++){
                fields[i].setAccessible(true);
                list.add(fields[i]);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }


    /**
     * 获取类的所有属性，包括获取父类的所有属性
     *
     * @param classz class类型
     * @return 返回类属性列表
     */
    public static List<Field> getDeclaredFields(Class<?> classz){
        return getDeclaredFields(classz, new ArrayList<Field>());
    }

    /**
     * 递归方法获取当前及其父类的所有属性
     *
     * @param classz class类型
     * @param fields 类属性列表
     * @return 返回类属性列表
     */
    private static List<Field> getDeclaredFields(Class<?> classz, List<Field> fields){
        fields.addAll(Arrays.asList(classz.getDeclaredFields()));
        classz = classz.getSuperclass();
        if(classz == null){
            return fields;
        }
        // 递归调用
        return getDeclaredFields(classz, fields);
    }
}
