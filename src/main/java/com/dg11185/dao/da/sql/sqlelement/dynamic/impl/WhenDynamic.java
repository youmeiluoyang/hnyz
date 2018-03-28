package com.dg11185.dao.da.sql.sqlelement.dynamic.impl;

import com.dg11185.dao.da.sql.sqlelement.dynamic.SqlDynamic;

/**
 * Delegate to IfDynamic
 * @author xiesp
 * @description
 * @date 12:44 PM  12/1/2017
 * @copyright 全国邮政电子商务运营中心
 */
public class WhenDynamic extends IfDynamic implements SqlDynamic{

    public WhenDynamic(String condition,String passText){
        super("when",condition,passText);
    }
}
