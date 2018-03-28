package com.dg11185.dao.da.sql.conveter;

import java.util.List;
import java.util.Map;

/**
 * @author xiesp
 * @description
 * @date 10:00 AM  12/4/2017
 * @copyright 全国邮政电子商务运营中心
 */
public class SqlParam {

    private final boolean isMap;
    private final Map<String,Object> paramMap;
    //special params array list
    private final List<Object[]> paramList;
    public SqlParam(Map<String,Object> paramMap){
        this.isMap = true;
        this.paramMap = paramMap;
        this.paramList = null;
    }

    public  SqlParam(List<Object[]> paramList){
        this.paramList = paramList;
        this.paramMap = null;
        this.isMap = false;
    }


    public boolean isMap() {
        return isMap;
    }

    public Map<String, Object> getParamMap() {
        return paramMap;
    }

    public List<Object[]> getParamList() {
        return paramList;
    }
}
