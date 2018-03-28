package com.dg11185.dao.da.sql.impl.reflect;

import com.dg11185.dao.da.sql.Sql;
import com.dg11185.dao.da.sql.conveter.SqlParam;
import com.dg11185.dao.util.ClassCache;
import com.dg11185.dao.util.ClassCahceMap;
import com.dg11185.dao.util.SqlUtil;
import com.dg11185.util.common.StrUtil;
import com.dg11185.util.concurrent.annotation.Immutable;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xiesp
 * @description
 * @date 3:51 PM  12/1/2017
 * @copyright 全国邮政电子商务运营中心
 */
@Immutable
public class InsertReflectSql extends AbstractReflectSql implements Sql{


    public InsertReflectSql(Object o){
        super(o,ReflectType.INSERT);
        this.setHasParam(true);
    }

    //for inherited purpose
    protected InsertReflectSql(Object o,ReflectType reflectType){
        super(o,reflectType);
        this.setHasParam(true);
    }

    @Override
    String generateSql() throws Exception {
        //test table presentence
        this.testClass(object.getClass(),0x04);
        ClassCache classCache = ClassCahceMap.getClassCache(object);
        Map<String,Field> fieldMap = classCache.getFieldMap();
        String tableName = classCache.getTableName();
        StringBuilder sb = new StringBuilder("insert into ").append(tableName).append("(");
        StringBuilder paramSb = new StringBuilder(" values (");
        //check if idColumn is presented
        String idColumn = classCache.getIdColumnName();
        Object idValue = null;
        if (!StrUtil.isEmpty(idColumn)) {
            idValue = fieldMap.get(idColumn).get(object);
        }
        boolean needSeq = true;
        if (StrUtil.isEmpty(idColumn) || (idValue != null)) {
            {
                needSeq = false;
            }
        }
        Map<String, Object> paramMap = new HashMap();
        //idColumn is specified,but null,so generate seq value
        if (needSeq) {
            sb.append(idColumn).append(",");
            paramSb.append(classCache.getSeqName()).append(".nextVal").append(",");
        }
        for (Map.Entry<String, Field> entry : fieldMap.entrySet()) {
            String fieldName = entry.getKey();
            if (needSeq) {
                if (fieldName.equals(idColumn)) {
                    continue;
                }
            }
            //insert all not null fields
            Field field = entry.getValue();
            Object value = field.get(object);
            if (value != null) {
                //add parameter
                sb.append(fieldName).append(",");
                String wrapParameter = SqlUtil.wrapSqlParameter(fieldName);
                paramSb.append(wrapParameter).append(",");
                //add param value
                paramMap.put(fieldName, value);
            }
        }
        //truncate unnecessary char
        String str1 = sb.substring(0, sb.length() - 1) + ")";
        String str2 = paramSb.substring(0, paramSb.length() - 1) + ")";
        //finally set Param
        this.param = new SqlParam(paramMap);
        return new StringBuilder(str1).append(str2).toString();
    }

    @Override
    public Sql cloneSql() {
        return new InsertReflectSql(this.object);
    }
}

