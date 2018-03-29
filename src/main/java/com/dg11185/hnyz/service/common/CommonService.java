package com.dg11185.hnyz.service.common;

import com.dg11185.hnyz.bean.common.Menu;

import java.util.List;
import java.util.Map;

/**
 * @author xiesp
 * @description
 * @date 9:56 AM  3/29/2018
 * @copyright 全国邮政电子商务运营中心
 */
public interface CommonService {


    /**
     * 返回系统的菜单
     * @return
     */
    Map<String,List<Menu>> getMenu();
}
