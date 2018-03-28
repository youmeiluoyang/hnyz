package com.dg11185.dao.da.sql.impl.reflect;

import com.dg11185.dao.da.sql.Sql;
import com.dg11185.dao.da.sql.conveter.SqlParam;
import com.dg11185.dao.util.ClassCache;
import com.dg11185.dao.util.ClassCahceMap;
import com.dg11185.dao.util.SqlUtil;
import com.dg11185.util.common.StrUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xiesp
 * @description
 * @date 4:23 PM  12/1/2017
 * @copyright 全国邮政电子商务运营中心
 */
public class DeleteReflectSql extends AbstractReflectSql implements Sql{

    private final String keyColumn;

    public DeleteReflectSql(Object o,String keyColumn){
        super(o,ReflectType.DELETE);
        this.keyColumn = keyColumn;
        this.setHasParam(true);
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
        Map<String,Object> paramMap = new HashMap();
        StringBuilder sb = new StringBuilder("delete from ").append(classCache.getTableName())
                .append(" where ").append(idColumnName).append(" = ").append(SqlUtil.wrapSqlParameter(idColumnName));
        //finally set param map
        paramMap.put(idColumnName,classCache.getFieldMap().get(idColumnName).get(object));
        this.param= new SqlParam(paramMap);
        return sb.toString();
    }


    @Override
    public Sql cloneSql() {
        return new DeleteReflectSql(object,keyColumn);
    }
}
