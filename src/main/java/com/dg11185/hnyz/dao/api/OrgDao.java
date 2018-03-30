package com.dg11185.hnyz.dao.api;

import com.dg11185.hnyz.bean.base.Org;
import com.dg11185.hnyz.dao.BaseDAO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author zouwei
 * @Datetime 2018-03-29 18:42
 * @Copyright 全国邮政电子商务运营中心
 */
@Repository
public class OrgDao extends BaseDAO {

    public List<Org> getOrgs() {
        String sql = "select * from tb_org";
        return queryForList(sql, Org.class);
    }

}

