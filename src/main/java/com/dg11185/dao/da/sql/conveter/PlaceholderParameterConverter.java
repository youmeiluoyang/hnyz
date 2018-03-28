package com.dg11185.dao.da.sql.conveter;

import com.dg11185.dao.Constants;
import com.dg11185.dao.da.sql.Sql;
import com.dg11185.dao.exception.SqlConvertException;
import com.dg11185.dao.util.SqlUtil;
import com.dg11185.util.common.DateUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

/**
 * @author xiesp
 * @description
 * @date 4:44 PM  11/28/2017
 * @copyright 全国邮政电子商务运营中心
 */
public class PlaceholderParameterConverter implements ParameterConverter {

    private final String placeHolder;

    public PlaceholderParameterConverter(String placeHolder){
        this.placeHolder = placeHolder;
    }


    @Override
    public ConvertResult convertSql(Sql sqlObject) {
        String finalSql = "";
        List<Object> list = new ArrayList<>();
        List<Object[]> listArr = new ArrayList<>();
        String sql = sqlObject.getExecSql();
        //is param has been set
        if(sqlObject.isHasParam()){
            SqlParam param= sqlObject.getParam();
            Matcher matcher = Constants.PARAMETER_PATTERN.matcher(sql);
            //paramMap needs prepare
            if(param.isMap()){
                Map<String,Object> paramMap = param.getParamMap();
                while (matcher.find()) {
                    //extract name
                    String parameterName = SqlUtil.extractSqlParameter(sql.substring(matcher.start(),matcher.end()));
                    Object parameterValue = paramMap.get(parameterName);
                    if(parameterValue == null){
                        throw new SqlConvertException(parameterName+"'s value is not specified!");
                    }
                    //Test if is "In" sub-clause
                    if(parameterValue instanceof List){
                        boolean finalFit = false;
                        for(int i = matcher.start()-1;i>0;i--){
                            char c = sql.charAt(i);
                            if(c==' ' || c== '\n'){
                                continue;
                            }
                            else if(c =='n'||c=='N'){
                                char next = sql.charAt(i-1);
                                if(next == 'i' || next =='I'){
                                    finalFit = true;
                                    break;
                                }else {
                                    break;
                                }
                            }else{
                                break;
                            }
                        }
                        //is In Clause,build parameters
                        if(finalFit){
                            StringBuilder sbIn = new StringBuilder("(");
                            List<Object> inParams = (List<Object>) parameterValue;
                            list.addAll(inParams);
                            for(int k = 0;k<inParams.size();k++){
                                sbIn.append("?,");
                            }
                            sbIn.setLength(sbIn.length() - 1);
                            sbIn.append(")");
                            sql = matcher.replaceFirst(sbIn.toString());
                        }else{
                            throw new SqlConvertException(parameterName+"'s type is List,but in clause is missing");
                        }
                    }
                    //Test Date
                    else if(parameterValue instanceof Date){
                        String dateStr =  DateUtil.formatDate((Date)parameterValue, "yyyy-MM-dd HH:mm:ss");
                        String dateParameter = "to_date(?, 'yyyy-MM-dd HH24:mi:ss')";
                        list.add(dateStr);
                        sql = matcher.replaceFirst(dateParameter);
                    }
                    else{
                        list.add(parameterValue);
                        sql = matcher.replaceFirst(placeHolder);
                    }
                    matcher = Constants.PARAMETER_PATTERN.matcher(sql);
                }
                finalSql = sql;
            }else{
                finalSql = matcher.replaceAll(placeHolder);
                //this list is the final param
                listArr = param.getParamList();
            }
        }
        //simply return final sql
        else{
            finalSql = sqlObject.getExecSql();
        }
        ConvertResult convertedResult = new ConvertResult(finalSql,list,listArr);
        return convertedResult;
    }


}
