package com.dg11185.dao.da.sql.impl.reflect;

import com.dg11185.dao.da.sql.Sql;
import com.dg11185.dao.da.sql.conveter.SqlParam;
import com.dg11185.dao.util.ClassCache;
import com.dg11185.dao.util.ClassCahceMap;
import com.dg11185.dao.util.SqlUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xiesp
 * @description
 * @date 4:16 PM  12/1/2017
 * @copyright 全国邮政电子商务运营中心
 */
public class QueryByIdReflectSql extends AbstractReflectSql implements Sql{

    private final String keyColumn;

    public QueryByIdReflectSql(Object o,String keyColumn){
        super(o,ReflectType.UPDATE);
        this.keyColumn = keyColumn;
        this.setHasParam(true);
    }




    @Override
    String generateSql() throws Exception {
        //test table
        this.testClass(object.getClass(),0x04);
        ClassCache classCache = ClassCahceMap.getClassCache(object.getClass());
        String columnName = keyColumn;
        if(keyColumn == null){
            //test id column
            this.testClass(object.getClass(),0x02);
            columnName = classCache.getIdColumnName();
        }
        String tableName = classCache.getTableName();
        StringBuilder stringBuilder = new StringBuilder("select * from ").append(tableName).append(" where ")
                .append(columnName).append(" = ").append(SqlUtil.wrapSqlParameter(columnName));
        //set Param
        Map<String,Object> paramMap = new HashMap();
        paramMap.put(columnName,classCache.getField(columnName).get(object));
        this.param = new SqlParam(paramMap);
        return stringBuilder.toString();
    }


    @Override
    public Sql cloneSql() {
        return new QueryByIdReflectSql(object,keyColumn);
    }
}
