package com.dg11185.hnyz.service.api;

import com.dg11185.hnyz.bean.base.Org;

import java.util.List;

/**
 * @Author zouwei
 * @Datetime 2018-03-29 18:42
 * @Copyright 全国邮政电子商务运营中心
 */

public interface OrgService {
    List<Org> getOrgs();

    Org getOrg(String orgNo);
}
