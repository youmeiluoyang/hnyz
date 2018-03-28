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
public class BatchUpdateReflectSql extends AbstractReflectSql implements Sql{


    protected  final String keyColumn;
    private final  List<Object> objs;
    public BatchUpdateReflectSql(List<Object> list, String keyColumn){
        super(list.get(0),ReflectType.BATCH_INSERT);
        this.objs = list;
        this.hasParam = true;
        this.keyColumn = keyColumn;
    }


    @Override
    public String generateSql() throws Exception {
        //using first item as template!!!!
        Object obj = objs.get(0);
        //test table presentence
        this.testClass(obj.getClass(),0x04);
        ClassCache classCache = ClassCahceMap.getClassCache(obj);
        String idColumnName = keyColumn;
        if(StrUtil.isEmpty(idColumnName)){
            //test idColumn exist
            this.testClass(obj.getClass(),0x02);
            idColumnName = classCache.getIdColumnName();
        }
        StringBuilder sb = new StringBuilder("update ").append(classCache.getTableName())
                .append(" set ");
        List<Object[]> allParamList = new ArrayList<>();
        for(Map.Entry<String,Field> entry:classCache.getFieldMap().entrySet()){
            String fieldName = entry.getKey();
            if(fieldName.equals(idColumnName))
                continue;
            Field field = entry.getValue();
            Object value = field.get(obj);
            if(value!=null){
                //add parameter
                sb.append(fieldName).append(" = ");
                String wrapParameter = SqlUtil.wrapSqlParameter(fieldName);
                if(value instanceof Date){
                    wrapParameter  = new StringBuilder("to_date(").append(wrapParameter).append(",'yyyy-MM-dd HH24:mi:ss')").toString();
                }
                sb.append(wrapParameter).append(",");
                //add param value
            }
        }
        //truncate last char
        sb.setLength(sb.length() -1);
        sb.append(" where ").append(idColumnName).append(" = ").append(SqlUtil.wrapSqlParameter(idColumnName));
        //build parameter
        for(Object o:objs){
            List<Object> paramArr = new ArrayList<>();
            for(Map.Entry<String,Field> entry:classCache.getFieldMap().entrySet()){
                Object value = entry.getValue().get(o);
                if(value!=null){
                    if(value instanceof Date){
                        value =   DateUtil.formatDate((Date)value, "yyyy-MM-dd HH:mm:ss");
                    }
                    paramArr.add(value);
                }
            }
            allParamList.add(paramArr.toArray());
        }
        this.setParam(allParamList);
        return  sb.toString();
    }

    @Override
    public Sql cloneSql() {
        return new BatchUpdateReflectSql(objs,keyColumn);
    }
}
