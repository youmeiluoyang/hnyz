package com.dg11185.dao.da.sqlexecutor;

import com.dg11185.dao.da.config.DaoConfig;
import com.dg11185.dao.da.sql.Sql;
import com.dg11185.dao.da.sql.conveter.ConvertResult;
import com.dg11185.dao.da.statics.StaticsMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author xiesp
 * @description
 * @date 2:16 PM  11/29/2017
 * @copyright 全国邮政电子商务运营中心
 */
public abstract class AbstractSqlExecutor implements SqlExecutor{

    private final static Logger log = LoggerFactory.getLogger(AbstractSqlExecutor.class);



    /**
     * log and make stastics
     * @param sql
     * @param castTime
     * @param execTime
     */
    protected void logAndStasicsSql(Sql sql, long execTime, long castTime, boolean isSucc){
        StaticsMap.makeStastics(sql.getId(),execTime,castTime,isSucc);
        ConvertResult convertResult = sql.getConvertedResult();
        StringBuilder sb = new StringBuilder("Executed Sql:").append(sql.getId())
                .append(",Exec-Time:").append(execTime).append(",Cast-Time:").append(castTime).append(",Exec-Status:").append(isSucc)
                .append(convertResult.toString());
        String text = "";
        //if Error,print sql
        if(!isSucc){
            text = "[ERROR-OCCURRED]"+sb.toString();
            log.error(text);
        }else{
            //in debug,print sql
            if(DaoConfig.isDebug()){
                text = "[DEBUG-MODE]"+sb.toString();
                log.info(text);
            }
            //then test for execution time
            else if (execTime > DaoConfig.getWarnTimeOut()){
                text = "[TIME-EXCEED]"+sb.toString();
                log.warn(text);
            }
        }
    }

}
