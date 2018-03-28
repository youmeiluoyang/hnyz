package com.dg11185.dao.da.statics;

/**
 * @author xiesp
 * @description
 * @date 5:12 PM  12/4/2017
 * @copyright 全国邮政电子商务运营中心
 */
public class SqlStatics {

    private long allExecTime = 0;
    private long allCastTime = 0;
    private long longestExecTime = 0;
    private long longestCastTime= 0;
    private int failCount = 0;
    private int execCount = 0;
    private final String sqlId;


    public SqlStatics(String id){
        this.sqlId = id;
    }


    public void statics(long execTime,long castTime,boolean isSucc){
        execCount++;
        if(isSucc){
            allExecTime +=execTime;
            allCastTime +=castTime;
            if(longestExecTime < execTime){
                longestExecTime = execTime;
            }if(longestCastTime < castTime){
                longestCastTime = castTime;
            }
        }else{
            failCount++;
        }
    }

    public String getSqlId() {
        return sqlId;
    }

    public long getAllExecTime() {
        return allExecTime;
    }

    public void increaseAllExecTime(long allExecTime) {
        this.allExecTime += allExecTime;
    }

    public long getAllCastTime() {
        return allCastTime;
    }

    public void increaseAllCastTime(long allCastTime) {
        this.allCastTime += allCastTime;
    }

    public long getLongestExecTime() {
        return longestExecTime;
    }



    public long getLongestCastTime() {
        return longestCastTime;
    }



    public int getFailCount() {
        return failCount;
    }


    public int getExecCount() {
        return execCount;
    }

}
