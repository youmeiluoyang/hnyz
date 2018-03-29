package com.dg11185.hnyz.service.api.impl;

import com.dg11185.hnyz.bean.base.Org;
import com.dg11185.hnyz.dao.api.OrgDao;
import com.dg11185.hnyz.service.api.OrgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author zouwei
 * @Datetime 2018-03-29 18:42
 * @Copyright 全国邮政电子商务运营中心
 */
@Service
public class OrgServiceImpl implements OrgService {

    @Autowired
    private OrgDao orgDao;

    @Override
    public List<Org> getOrg() {
        return orgDao.getOrg();
    }
}
