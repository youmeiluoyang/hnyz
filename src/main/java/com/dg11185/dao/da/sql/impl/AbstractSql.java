package com.dg11185.dao.da.sql.impl;

import com.dg11185.dao.da.sql.Sql;
import com.dg11185.dao.da.sql.conveter.ConvertResult;
import com.dg11185.dao.da.sql.conveter.ParameterConverter;
import com.dg11185.dao.da.sql.conveter.PlaceholderParameterConverter;
import com.dg11185.dao.da.sql.conveter.SqlParam;
import com.dg11185.dao.exception.NullIdColumnException;
import com.dg11185.dao.exception.NullSeqException;
import com.dg11185.dao.exception.NullTableException;
import com.dg11185.dao.util.ClassCahceMap;
import com.dg11185.util.common.StrUtil;
import com.dg11185.util.concurrent.annotation.ThreadConfined;

import java.util.List;
import java.util.Map;

/**
 * @author xiesp
 * @description
 * @date 3:13 PM  12/1/2017
 * @copyright 全国邮政电子商务运营中心
 */
public abstract class AbstractSql implements Sql{

    //FIXME here the format is dependent to the specific parameterConverter!!!
    protected static final ParameterConverter parameterConverter = new PlaceholderParameterConverter("?");
    protected final String id;
    protected final Class<?> resultClass;

    //whether this Sql has params,some sql will delay the param existence until geExecSql() is called!
    @ThreadConfined
    protected boolean hasParam =false;
    @ThreadConfined
    protected ConvertResult sqlConvertedResult;
    @ThreadConfined
    protected SqlParam param;

    protected AbstractSql(String id,Class<?> resultClass){
        this.id = id;
        this.resultClass = resultClass;
    }


    @Override
    public String getId() {
        return this.id;
    }


    @Override
    public Class<?> getResultClass() {
        return this.resultClass;
    }


    //ignore null fields
    @Override
    public void setParam(Object object){
        if(object instanceof List){
            List<Object[]> list = (List<Object[]>)object;
            this.param = new SqlParam(list);
        }else{
            Map<String,Object> paramMap= ClassCahceMap.getAllNotNullFileValues(object);
            this.setParam(paramMap);
        }
    }

    @Override
    public void setParam(Map<String, Object> paramMap) {
        this.param = new SqlParam(paramMap);
        //every time a new param is set,convertedResult is set to null
        this.sqlConvertedResult = null;
        //and hasParam is set to true
        this.hasParam = true;
    }

    @Override
    public SqlParam getParam() {
        return this.param;
    }


    @Override
    public ConvertResult getConvertedResult() {
        //if has already been converted,then return it
        //some sql may convert sql in advance for some purpose
        if(sqlConvertedResult == null){
            sqlConvertedResult = parameterConverter.convertSql(this);
        }
        return sqlConvertedResult;
    }


    public boolean isHasParam() {
        return hasParam;
    }

    public void setHasParam(boolean hasParam) {
        this.hasParam = hasParam;
    }

    /**
     * Sometimes a class object representing a pojo should satisfied some requirements<br>
     * uo to now ,the requirement is listed as below<br>
     * type is analysed as bits,1 is seqName,2 is id column ,4 is table<br>
     * 1 sequence is specified<br>
     * 2 IdColumn is Specified<br>
     * 4 Table is Specified
     * @param clazz
     */
    protected void testClass(Class<?> clazz,int type){
        boolean checkSeq = (type&0x01) > 0;
        if(checkSeq){
            String seqName = ClassCahceMap.getClassCache(clazz).getSeqName();
            if(StrUtil.isEmpty(seqName)){
                throw new NullSeqException(clazz.getSimpleName() +" did not specified a sequence! ");
            }
        }
        boolean checkIdColumn = (type&0x02) > 0;
        if(checkIdColumn){
            String idColumnName = ClassCahceMap.getClassCache(clazz).getIdColumnName();
            //IdColumn must be specified in the class
            if(StrUtil.isEmpty(idColumnName)){
                throw new NullIdColumnException(clazz.getSimpleName()  + " did not specified an id Column!");
            }
        }
        boolean checkTable = (type & 0x04) > 0;
        if(checkTable){
            String tableName = ClassCahceMap.getClassCache(clazz).getTableName();
            if(StrUtil.isEmpty(tableName)){
                throw new NullTableException(clazz.getSimpleName()  + " did not specified to a table!");
            }
        }
    }


}
