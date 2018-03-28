package com.dg11185.dao.da.sql.conveter;

import com.alibaba.fastjson.JSONObject;
import com.dg11185.util.concurrent.annotation.Immutable;

import java.util.List;

/**
 * @author xiesp
 * @description
 * @date 6:56 PM  11/28/2017
 * @copyright 全国邮政电子商务运营中心
 */
@Immutable
public class ConvertResult {

    private final String convertedSql;
    private final List<Object> paramList;
    private final List<Object[]> paramListArr;

    //all fields not null
    public ConvertResult(String convertedSql, List<Object> paramList,List<Object[]> paramListArr){
        this.convertedSql = convertedSql;
        this.paramList = paramList;
        this.paramListArr = paramListArr;
    }


    private ConvertResult(ConvertResult sqlConvertedResult){
        this.convertedSql = sqlConvertedResult.convertedSql;
        this.paramList = sqlConvertedResult.paramList;
        this.paramListArr = sqlConvertedResult.paramListArr;
    }

    public ConvertResult clone(){
        return new ConvertResult(this);
    }

    public String getConvertedSql() {
        return convertedSql;
    }

    public List<?> getParamList() {
        return paramList;
    }

    public List<Object[]> getParamListArr() {
        return paramListArr;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder("\n").append(convertedSql).append(" [params==>");
        Object obj = paramListArr.size()>0?paramListArr:paramList;
        sb.append(JSONObject.toJSONString(obj));
        return sb.append("]").toString();
    }

}
