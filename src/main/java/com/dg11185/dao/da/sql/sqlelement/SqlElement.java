package com.dg11185.dao.da.sql.sqlelement;

/**
 * @author xiesp
 * @description
 * @date 8:17 AM  12/1/2017
 * @copyright 全国邮政电子商务运营中心
 */
public interface SqlElement{


    /**
     * whether this Element is Dynamic
     * @return
     */
    boolean isDynamic();

    /**
     * return the sql to be executed
     * @return
     */
    String getText();

}
