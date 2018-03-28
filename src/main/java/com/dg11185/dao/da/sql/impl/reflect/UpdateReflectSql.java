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
 * @date 4:01 PM  12/1/2017
 * @copyright 全国邮政电子商务运营中心
 */
@Immutable
public class UpdateReflectSql extends AbstractReflectSql implements Sql {


    private final String keyColumn;

    public UpdateReflectSql(Object o,String keyColumn){
        super(o,ReflectType.UPDATE);
        this.keyColumn = keyColumn;
        this.hasParam = true;
    }


    @Override
    String generateSql() throws Exception {
        //test table exist
        this.testClass(object.getClass(),0x04);
        ClassCache classCache = ClassCahceMap.getClassCache(object);
        String idColumnName = keyColumn;
        if(StrUtil.isEmpty(idColumnName)){
            //test idColumn exist
            this.testClass(object.getClass(),0x02);
            idColumnName = classCache.getIdColumnName();
        }
        StringBuilder sb = new StringBuilder("update ").append(classCache.getTableName())
                .append(" set ");
        Map<String,Object> paramMap = new HashMap();
        for(Map.Entry<String,Field> entry:classCache.getFieldMap().entrySet()){
            String fieldName = entry.getKey();
            Field field = entry.getValue();
            Object value = field.get(object);
            if(value!=null){
                if(!fieldName.equals(idColumnName)){
                    //add parameter
                    sb.append(fieldName).append(" = ");
                    String wrapParameter = SqlUtil.wrapSqlParameter(fieldName);
                    sb.append(wrapParameter).append(",");
                }
                //add param value
                paramMap.put(fieldName,value);
            }
        }
        //truncate last char
        sb.setLength(sb.length() -1);
        sb.append(" where ").append(idColumnName).append(" = ").append(SqlUtil.wrapSqlParameter(idColumnName));
        //set param map
        this.param = new SqlParam(paramMap);
        return sb.toString();
    }

    @Override
    public Sql cloneSql() {
        return new UpdateReflectSql(object,keyColumn);
    }
}
