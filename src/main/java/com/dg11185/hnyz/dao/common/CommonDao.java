package com.dg11185.hnyz.dao.common;

import com.dg11185.hnyz.bean.common.Menu;
import com.dg11185.hnyz.dao.BaseDAO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author xiesp
 * @description
 * @date 3:10 PM  3/29/2018
 */
@Repository
public class CommonDao extends BaseDAO{



    /**
     * 获取菜单列表
     * @return
     */
    public List<Menu> getMenu(){
        //注意排序
        String sql = "select * from tb_menu order by ids asc";
        List<Menu> list = this.queryForList(sql,Menu.class);
        return list;
    }


}
