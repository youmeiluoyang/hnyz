package com.dg11185.dao.da.statics;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author xiesp
 * @description
 * @date 5:16 PM  12/4/2017
 * @copyright 全国邮政电子商务运营中心
 */
public class StaticsMap {

    private static final ConcurrentMap<String,SqlStatics> MAP = new ConcurrentHashMap<>();


    public static void makeStastics(String id,long execTime,long castTime,boolean isSucc){
        SqlStatics sqlStatics = MAP.get(id);
        if(sqlStatics == null){
            sqlStatics = new SqlStatics(id);
            MAP.put(id,sqlStatics);
        }
        sqlStatics.statics(execTime,castTime,isSucc);
    }


    public static List<SqlStatics> getAllStastics(){
        List<SqlStatics> list = new ArrayList<>();
        for(SqlStatics sqlStatics:MAP.values()){
            list.add(sqlStatics);
        }
        return list;
    }

    public static SqlStatics getSqlStastics(String id){
        return MAP.get(id);
    }
}
