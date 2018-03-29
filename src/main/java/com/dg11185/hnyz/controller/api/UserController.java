package com.dg11185.hnyz.controller.api;

import com.dg11185.hnyz.bean.common.APIResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author zouwei
 * @Datetime 2018-03-29 19:28
 * @Copyright 全国邮政电子商务运营中心
 */

@Controller
@RequestMapping("/api/user")
public class UserController {

    @RequestMapping("/bindOrg.do")
    @ResponseBody
    public APIResponse bindOrg(String orgNo) {
        return new APIResponse();
    }

    @RequestMapping("/bindAddress.do")
    @ResponseBody
    public APIResponse bindAddress(String orgNo) {
        return new APIResponse();
    }

    @RequestMapping("/bindPhone.do")
    @ResponseBody
    public APIResponse bindPhone(String orgNo) {
        return new APIResponse();
    }

    @RequestMapping("/bindCard.do")
    @ResponseBody
    public APIResponse bindCard(String orgNo) {
        return new APIResponse();
    }
}
