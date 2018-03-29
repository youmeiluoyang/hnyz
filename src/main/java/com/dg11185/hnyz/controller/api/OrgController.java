package com.dg11185.hnyz.controller.api;

import com.dg11185.hnyz.bean.base.Org;
import com.dg11185.hnyz.bean.common.APIResponse;
import com.dg11185.hnyz.service.api.OrgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Author zouwei
 * @Datetime 2018-03-29 18:40
 * @Copyright 全国邮政电子商务运营中心
 */

@Controller
@RequestMapping("/api/org")
public class OrgController {

    @Autowired
    private OrgService orgService;

    @RequestMapping("/getOrg.do")
    @ResponseBody
    public APIResponse getOrg(String orgNo) {
        List<Org> org = orgService.getOrg();
        APIResponse res = new APIResponse();
        res.initedData().put("org", org);
        return res;
    }
}
