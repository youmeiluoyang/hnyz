package com.dg11185.dao.da.sql.sqlelement.dynamic.result;

/**
 * @author xiesp
 * @description
 * @date 1:08 PM  12/1/2017
 * @copyright 全国邮政电子商务运营中心
 */
public class DefaultDynamicResult implements DynamicResult{

    protected final boolean flag;
    protected final String passText;

    public DefaultDynamicResult(boolean flag,String passText){
        this.flag = flag;
        this.passText = passText;
    }

    @Override
    public boolean isPass() {
        return flag;
    }

    @Override
    public String getPassText() {
        return passText;
    }
}
