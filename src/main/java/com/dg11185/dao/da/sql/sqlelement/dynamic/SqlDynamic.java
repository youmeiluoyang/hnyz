package com.dg11185.dao.da.sql.sqlelement.dynamic;

import com.dg11185.dao.da.sql.sqlelement.SqlElement;
import com.dg11185.dao.da.sql.sqlelement.dynamic.result.DynamicResult;

import java.util.Map;

/**
 * @author xiesp
 * @description
 * @date 6:58 PM  11/30/2017
 * @copyright 全国邮政电子商务运营中心
 */
public interface SqlDynamic extends SqlElement {




    DynamicResult getResult(Map<String, Object> paramMap);
}
