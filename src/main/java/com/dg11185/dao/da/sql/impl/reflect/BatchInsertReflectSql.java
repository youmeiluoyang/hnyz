package com.dg11185.dao.da.sql.impl.reflect;

import com.dg11185.dao.da.sql.Sql;
import com.dg11185.dao.util.ClassCache;
import com.dg11185.dao.util.ClassCahceMap;
import com.dg11185.dao.util.SqlUtil;
import com.dg11185.util.common.DateUtil;
import com.dg11185.util.common.StrUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author xiesp
 * @description
 * @date 8:29 AM  12/4/2017
 * @copyright 全国邮政电子商务运营中心
 */
public class BatchInsertReflectSql extends AbstractReflectSql implements Sql{


    private final  List<Object> objs;
    public BatchInsertReflectSql(List<Object> list){
        super(list.get(0),ReflectType.BATCH_INSERT);
        this.objs = list;
        this.hasParam = true;
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
        List<Object[]> allParamList = new ArrayList<>();
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
                if(value instanceof Date){
                    wrapParameter = new StringBuilder("to_date(").append(wrapParameter).append(",'yyyy-MM-dd HH24:mi:ss')").toString();
                }
                paramSb.append(wrapParameter).append(",");
            }
        }
        //truncate unnecessary char
        String str1 = sb.substring(0, sb.length() - 1) + ")";
        String str2 = paramSb.substring(0, paramSb.length() - 1) + ")";
        //finally set Param
        for(Object o:objs){
            List<Object> paramList = new ArrayList<>();
            for (Map.Entry<String, Field> entry : classCache.getFieldMap().entrySet()) {
                //value is supposed not to be null
                Object value = entry.getValue().get(o);
                if(value == null){
                    continue;
                }
                if(value instanceof Date){
                    value =   DateUtil.formatDate((Date)value, "yyyy-MM-dd HH:mm:ss");
                }
                paramList.add(value);
            }
            allParamList.add(paramList.toArray());
        }
        this.setParam(allParamList);
        return new StringBuilder(str1).append(str2).toString();
    }

    @Override
    public Sql cloneSql() {
        return new BatchInsertReflectSql(objs);
    }
}
