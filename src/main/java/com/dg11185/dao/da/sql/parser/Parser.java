package com.dg11185.dao.da.sql.parser;

import java.util.Map;

/**
 * parse dynamic sql condition
 * @author xiesp
 * @description
 * @date 9:37 AM  12/1/2017
 * @copyright 全国邮政电子商务运营中心
 */
public interface Parser {


    /**
     *
     * @param paramMap
     * @return
     */
    boolean parse(Map<String, Object> paramMap);

}
