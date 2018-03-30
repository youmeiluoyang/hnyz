package com.dg11185.hnyz.util;

import com.dg11185.dao.annotation.NotDbColumn;
import com.dg11185.util.common.DateUtil;
import com.dg11185.util.common.ReflectionUtil;
import org.apache.commons.lang.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;

/**
 * 定义自动生成对象的增删改查SQL语句的通用方法工具类
 * 
 * @author xdweleven
 * @version 1.0
 * @since 2016-09-18
 * @Copyright 2016 全国邮政电子商务运营中心 All rights reserved.
 */
public class SQLUtil {

    /** 数据表自增序列的前缀  */
    public static final String SEQ_PREFIX = "SEQ_TB_WXYX_";

	/**
	 * 自动生成插入指定对象的SQL语句,不包括对象属性的值为空的字段
	 * 
	 * @param obj 待生成插入SQL语句的对象
	 * @param tableName 待插入语句对应的数据库表的名称
	 * @return 返回一个包含SQL语句、SQL语句参数值及参数值类型的Map对象
	 */
	public static Map<String, Object> generateInsertExceptNull(Object obj, String tableName) {
		StringBuilder columnStrBuf = new StringBuilder(); // 记录数据表的字段名称
		StringBuilder paramStrBuf = new StringBuilder(); // 记录SQL语句对应插入的占位符
		List<Object> paramValues = new ArrayList<Object>(); // 记录对象参数值
		List<Integer> paramsType = new ArrayList<Integer>(); // 记录参数值类型
		// 查询待插入对象的属性值不为空的属性名称
		List<Object> fieldList = ReflectionUtil.getNotNullField(obj);
		try {
			for (int i = 0; i < fieldList.size(); i++) {
				Field field = (Field) fieldList.get(i);
				field.setAccessible(true);
				// 记录对象属性名称
				columnStrBuf.append(field.getName());
				if (i != fieldList.size() - 1) {
					columnStrBuf.append(",");
				}
				
				// 记录插入SQL语句的参数占位符
				if("class java.util.Date".equals(field.getType().toString())
						&& field.get(obj) != null){
					String timeStr = DateUtil.formatDate((Date)field.get(obj), "yyyy-MM-dd HH:mm:ss");
					paramStrBuf.append("to_date(?, 'yyyy-MM-dd HH24:mi:ss')");
					paramValues.add(timeStr);
					// 记录对象属性的数据类型
					paramsType.add(getOrclDataType(field.getType().toString()));
				}else{
					paramStrBuf.append("?");
					paramValues.add(field.get(obj));
					// 记录对象属性的数据类型
					paramsType.add(getOrclDataType(field.getType().toString()));
				}
				
				if (i != fieldList.size() - 1) {
					paramStrBuf.append(",");
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		// 生成插入操作的SQL语句
		StringBuilder sb = new StringBuilder();
		sb.append("insert into ");
		sb.append(tableName);
		sb.append(" (");
		sb.append(columnStrBuf);
		sb.append(") ");
		sb.append("values");
		sb.append(" (");
		sb.append(paramStrBuf);
		sb.append(")");
		// 将生成的SQL语句、SQL语句参数值及各参数值的数据类型用map保存并返回
		Map<String, Object> sqlMap = new HashMap<String, Object>();
		sqlMap.put("sql", sb.toString());
		sqlMap.put("paramsValues", paramValues.toArray());
		sqlMap.put("paramsTypes", paramsType.toArray());
		return sqlMap;
	}

	/**
	 * 自动生成插入指定对象的SQL语句,包括对象属性的值为空的字段，不包括自增长主键,若不存在，调用时直接置为null.
	 * 
	 * @param obj 待生成插入SQL语句的对象
	 * @param tableName 待插入语句对应的数据库表的名称
	 * @param keyColumn 数据表主键名称
	 * @return 返回一个包含SQL语句、SQL语句参数值及参数值类型的Map对象
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public static Map<String, Object> generateInsertWithNull(Object obj, String tableName, String keyColumn)
            throws IllegalArgumentException, IllegalAccessException {
		StringBuilder columnStrBuf = new StringBuilder();
		StringBuilder paramStrBuf = new StringBuilder(); // 记录SQL语句对应插入的占位符
		List<Object> columnNameList = new ArrayList<Object>(); // 记录数据表的字段名称
		List<Object> paramValues = new ArrayList<Object>(); // 记录对象参数值
		List<Integer> paramsType = new ArrayList<Integer>(); // 记录参数值类型
		Field[] fields = obj.getClass().getDeclaredFields();
		for(int i = 0; i < fields.length; i++){
			Annotation annotation =  fields[i].getAnnotation(NotDbColumn.class);
			if(annotation != null){
				if(i == fields.length -1){
					columnStrBuf.deleteCharAt(columnStrBuf.length() -1);
					paramStrBuf.deleteCharAt(paramStrBuf.length() -1);
				}
				continue;
			}

			fields[i].setAccessible(true);
			// 记录对象属性名称
			columnStrBuf.append(fields[i].getName());
			columnNameList.add(fields[i].getName());
			if (i != fields.length - 1) {
				columnStrBuf.append(",");
			}
			if(fields[i].getName().equalsIgnoreCase(keyColumn)){ // 主键列的值用自增序列赋值
                paramStrBuf.append(SEQ_PREFIX)
                        .append(StringUtils.upperCase(obj.getClass().getSimpleName()))
                        .append(".NEXTVAL");
			}else{ // 非主键列记录插入SQL语句的参数占位符
				if("class java.util.Date".equals(fields[i].getType().toString())
						&& fields[i].get(obj) != null){
					String timeStr = DateUtil.formatDate((Date)fields[i].get(obj), "yyyy-MM-dd HH:mm:ss");
					paramStrBuf.append("to_date(?, 'yyyy-MM-dd HH24:mi:ss')");
					paramValues.add(timeStr);
					// 记录对象属性的数据类型
					paramsType.add(getOrclDataType(fields[i].getType().toString()));
				}else{
					paramStrBuf.append("?");
					paramValues.add(fields[i].get(obj));
					// 记录对象属性的数据类型
					paramsType.add(getOrclDataType(fields[i].getType().toString()));
				}
				
			}
			if (i != fields.length - 1) {
				paramStrBuf.append(",");
			}
		}
		// 生成插入操作的SQL语句
		StringBuilder sb = new StringBuilder();
		sb.append("insert into ");
		sb.append(tableName);
		sb.append(" (");
		sb.append(columnStrBuf);
		sb.append(") ");
		sb.append("values");
		sb.append(" (");
		sb.append(paramStrBuf);
		sb.append(")");
		// 将生成的SQL语句、SQL语句的列名称用map保存并返回
		Map<String, Object> sqlMap = new HashMap<String, Object>();
		sqlMap.put("sql", sb.toString());
		sqlMap.put("columnNameList", columnNameList.toArray());
		sqlMap.put("paramsValues", paramValues.toArray());
		sqlMap.put("paramsTypes", paramsType.toArray());
		return sqlMap;
	}
	
	
	/**
	 * 自动生成更新指定对象的SQL语句
	 * 
	 * @param obj 待生成更新SQL语句的对象
	 * @param tableName 待更新语句对应的数据库表的名称
	 * @param keyColumn 待更新记录的限定字段
	 * @param includeNull SQL语句是否包含控制，true包含，false不包含
	 * @return 返回一个包含SQL语句及参数值的数组
	 */
	public static Object[] generateUpdate(Object obj, String tableName, String keyColumn, boolean includeNull) {
		StringBuilder columnSB = new StringBuilder();
		List<Object> params = new ArrayList<Object>();
		Object keyValue = null;
		// 获取属性值不为空的数据表字段名称
		List<Object> fieldList = includeNull ? ReflectionUtil.getAllField(obj) : ReflectionUtil.getNotNullField(obj);
		try {
			for (int i = 0; i < fieldList.size(); i++) {
				Field field = (Field) fieldList.get(i);
				Annotation annotation =  field.getAnnotation(NotDbColumn.class);
				if(annotation != null){
					if(i == fieldList.size() -1){
						columnSB.deleteCharAt(columnSB.length() -1);
					}
					continue;
				}
				field.setAccessible(true);

				if (field.getName().equalsIgnoreCase(keyColumn)) {
					keyValue = field.get(obj);
				} else {
					columnSB.append(field.getName());	
					if(field.get(obj) != null){
						columnSB.append("=?");
						params.add(field.get(obj));
					}
					if (i != fieldList.size() - 1) {
						columnSB.append(",");
					}
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		if (keyValue == null) {
			throw new IllegalArgumentException("数据表 [" + tableName+ "] 中的字段'"+keyColumn+"'的值不能为空.");
		}else{
			params.add(keyValue);
		}
		StringBuilder sb = new StringBuilder();
		sb.append("update ");
		sb.append(tableName);
		sb.append(" set ");
		if(columnSB.length() >= 0){
			sb.append(columnSB);
		}else{
			sb.append(keyColumn);
			sb.append("=? ");
			params.add(keyValue);
		}		
		sb.append(" where ");
		sb.append(keyColumn);
		sb.append("=? ");
		return new Object[] { sb.toString(), params.toArray() };
	}
	
	/**
	 * 返回java数据类型对应的Oracle数据库的数据类型值
     *
	 * @param javaType java数据类型
	 * @return 返回Oracle数据表的字段数据类型
	 */
	public static int getOrclDataType(String javaType){
		if("class java.lang.String".equals(javaType)){
			return java.sql.Types.VARCHAR;
		}else if("class java.lang.Integer".equals(javaType) || "int".equals(javaType)){
			return java.sql.Types.INTEGER;
		}else if("class java.lang.Double".equals(javaType) || "double".equals(javaType)){
			return java.sql.Types.DOUBLE;
		}else if("class java.lang.Float".equals(javaType) || "float".equals(javaType)){
			return java.sql.Types.FLOAT;
		}else if("char".equals(javaType)){
			return java.sql.Types.CHAR;
		}else if("class java.lang.Long".equals(javaType) || "long".equals(javaType)){
			return java.sql.Types.NUMERIC;
		}else if("class java.util.Date".equals(javaType)){
			return java.sql.Types.DATE;
		}else{
			return java.sql.Types.VARCHAR;
		}
	}

	/**
	 * 生成SQL语句中的where子句及where子句中参数值
     *
	 * @param obj where条件子句的对象
	 * @return 返回条件不为空的where子句
	 * @throws Exception 
	 * @throws  
	 */
	public static Map<String, Object> generateWhereStr(Object obj) throws  Exception{
		StringBuilder whereStrBuf = new StringBuilder(); // where子句
		List<Object> whereParamValues = new ArrayList<Object>(); // where子句中的参数值
		whereStrBuf.append(" where 1 = 1 ");
		if(obj != null){
			Field[] fields = obj.getClass().getDeclaredFields();
			for(int i = 0; i < fields.length; i++){
				fields[i].setAccessible(true);
				Object columnName = fields[i].get(obj);
				if(columnName != null && !"".equals(columnName)){
					whereStrBuf.append(" and ");
					whereStrBuf.append(fields[i].getName());
					whereStrBuf.append("=?");
					whereParamValues.add(columnName);
				}
			}			
		}
		Map<String, Object> whereMap = new HashMap<String, Object>();
        whereMap.put("whereStr", whereStrBuf.toString());
		whereMap.put("whereParamValues", whereParamValues.toArray());
		return whereMap;
	}

	/**
	 * 生成类似insert into user(username, password, memo)values(:username, :password, :memo)的SQL语句
     *
	 * @param obj 待生成的对象
	 * @param tableName 表名称
	 * @param keyColumn 主见名称，若为null，则表示无主见
	 * @return 返回SQL语句
	 */
	public static String generateNamedInsertSql(Object obj, String tableName, String keyColumn){
		StringBuilder sqlBuf = new StringBuilder();
		StringBuilder colBuf = new StringBuilder();
		StringBuilder paramBuf = new StringBuilder();
		Field[] fields = obj.getClass().getDeclaredFields();
		for(int i = 0; i < fields.length; i++){
			fields[i].setAccessible(true);
			colBuf.append(fields[i].getName());
			if(fields[i].getName().equalsIgnoreCase(keyColumn)){ // 主键列的值用自增序列赋值
                paramBuf.append(SEQ_PREFIX)
                        .append(StringUtils.upperCase(obj.getClass().getSimpleName()))
                        .append(".NEXTVAL");
			}else{ // 非主键列记录插入SQL语句的参数占位符
				paramBuf.append(":");
				paramBuf.append(fields[i].getName());
			}
			if (i != fields.length - 1) {
				colBuf.append(",");
				paramBuf.append(", ");
			}
		}
		// 生成插入操作的SQL语句
		sqlBuf.append("insert into ");
		sqlBuf.append(tableName);
		sqlBuf.append(" (");
		sqlBuf.append(colBuf);
		sqlBuf.append(") ");
		sqlBuf.append("values");
		sqlBuf.append(" (");
		sqlBuf.append(paramBuf);
		sqlBuf.append(")");
		return sqlBuf.toString();
	}
	
	
	/**
	 * 生成类似update user set username=:username, password=:password, memo=:memo 的SQL语句
     *
	 * @param obj 待生成的对象
	 * @param tableName 表名称
	 * @param keyColumn 主见名称，若为null，则表示无主见
	 * @return 返回SQL语句
	 */
	public static String generateNamedUpdateSql(Object obj, String tableName, String keyColumn){
		StringBuilder colBuf = new StringBuilder();
		// 获取属性值不为空的数据表字段名称
		List<Object> fieldList = ReflectionUtil.getNotNullField(obj);
		try {
			for (int i = 0; i < fieldList.size(); i++) {
				Field field = (Field) fieldList.get(i);
				field.setAccessible(true);
				if (!field.getName().equalsIgnoreCase(keyColumn)) {
					colBuf.append(field.getName());	
					colBuf.append("=:");
					colBuf.append(field.getName());	
					if (i != fieldList.size() - 1) {
						colBuf.append(", ");
					}
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		StringBuilder sqlBuf = new StringBuilder();
		sqlBuf.append("update ");
		sqlBuf.append(tableName);
		sqlBuf.append(" set ");
		if(colBuf.length() >= 0){
			sqlBuf.append(colBuf);
		}else{
			throw new IllegalArgumentException("Update SQL语句必须至少包含一个set子句.");
		}		
		if(keyColumn != null){
			sqlBuf.append(" where ");
			sqlBuf.append(keyColumn);
			sqlBuf.append("=:");
			sqlBuf.append(keyColumn);
		}
		return sqlBuf.toString();
	}


	/**
	 * 生成类似于where name = value and name=value的sql语句
	 * @param paramMap
	 * @return
	 */
	public static String generateSqlparamByMap(Map<String,Object> paramMap){
		StringBuilder sb = new StringBuilder();
		String orderValue = "";
		for(Map.Entry<String,Object> entry:paramMap.entrySet()){
			String name = entry.getKey();
			if(name.equals("#ORDER#")){
				orderValue = (String)entry.getValue();
				continue;
			}
			sb.append(" and ");
			sb.append(name).append("=").append(" ? ");
		}
		if(!StringUtils.isEmpty(orderValue)){
			paramMap.remove("#ORDER#");
			sb.append(" order by ").append(orderValue);
		}
		return sb.toString();
	}
}
