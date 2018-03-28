package com.dg11185.dao.da.sql.impl.reflect;

import com.dg11185.dao.da.sql.Sql;
import com.dg11185.dao.util.ClassCahceMap;

/**
 * @author xiesp
 * @description
 * @date 4:41 PM  12/2/2017
 * @copyright 全国邮政电子商务运营中心
 */
public class NextSeqReflectSql extends AbstractReflectSql implements Sql {

    public NextSeqReflectSql(Class<?> clazz){
        super(clazz,ReflectType.NEXT_SEQ,Long.class);
    }


    @Override
    public Sql cloneSql() {
        return new NextSeqReflectSql((Class<?>)object);
    }

    @Override
    String generateSql() throws Exception {
        Class<?> clazz = (Class<?>)object;
        this.testClass(clazz,0x01);
        String seqName = ClassCahceMap.getClassCache(clazz).getSeqName();
        StringBuilder sb = new StringBuilder("select ");
        sb.append(seqName).append(".nextVal  from dual");
        return sb.toString();
    }
}
