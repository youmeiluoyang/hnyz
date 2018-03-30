package com.dg11185.hnyz.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.dg11185.hnyz.bean.Member.Member;
import com.dg11185.hnyz.bean.common.APIResponse;
import com.dg11185.hnyz.common.constant.SysConstant;
import com.dg11185.hnyz.common.constant.WXConstant;
import com.dg11185.hnyz.service.api.UserService;
import com.dg11185.hnyz.service.api.WeixinService;
import com.dg11185.util.network.HttpClientUtils;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * @Author zouwei
 * @Datetime 2018-03-29 19:28
 * @Copyright 全国邮政电子商务运营中心
 */

@Controller
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private WeixinService weixinService;

    @RequestMapping("/bindOrg.do")
    @ResponseBody
    public APIResponse bindOrg(HttpSession session, @RequestParam String orgNo) {
        Member member = (Member) session.getAttribute(SysConstant.WX_USER);
        if (member == null) {
            return new APIResponse("ERROR", "notLogined", null);
        }
        Member oldMember = userService.getUserByOpenid(member.getOpenId());

        if (Strings.isNullOrEmpty(oldMember.getOrgNo())) {
            oldMember.setScore(oldMember.getScore() + 5);
        }

        oldMember.setOrgNo(orgNo);
        userService.update(oldMember);
        session.setAttribute(SysConstant.WX_USER, oldMember);
        return new APIResponse(APIResponse.SUCCESS);
    }

    @RequestMapping("/bindAddress.do")
    @ResponseBody
    public APIResponse bindAddress(HttpSession session, @RequestParam String address) {
        Member member = (Member) session.getAttribute(SysConstant.WX_USER);
        if (member == null) {
            return new APIResponse("ERROR", "notLogined", null);
        }
        Member oldMember = userService.getUserByOpenid(member.getOpenId());

        if (Strings.isNullOrEmpty(oldMember.getAddress())) {
            oldMember.setScore(oldMember.getScore() + 10);
        }

        oldMember.setAddress(address);
        userService.update(oldMember);
        session.setAttribute(SysConstant.WX_USER, oldMember);
        return new APIResponse(APIResponse.SUCCESS);
    }

    @RequestMapping("/bindPhone.do")
    @ResponseBody
    public APIResponse bindPhone(HttpSession session, @RequestParam String phone) {
        Member member = (Member) session.getAttribute(SysConstant.WX_USER);
        if (member == null) {
            return new APIResponse("ERROR", "notLogined", null);
        }
        Member oldMember = userService.getUserByOpenid(member.getOpenId());

        if (Strings.isNullOrEmpty(oldMember.getTelephone())) {
            oldMember.setScore(oldMember.getScore() + 5);
        }

        oldMember.setTelephone(phone);
        userService.update(oldMember);
        session.setAttribute(SysConstant.WX_USER, oldMember);
        return new APIResponse(APIResponse.SUCCESS);
    }

    @RequestMapping("/bindCard.do")
    @ResponseBody
    public APIResponse bindCard(HttpSession session, @RequestParam String name, @RequestParam String cardNo) {
        Member member = (Member) session.getAttribute(SysConstant.WX_USER);
        if (member == null) {
            return new APIResponse("ERROR", "notLogined", null);
        }
        Member oldMember = userService.getUserByOpenid(member.getOpenId());

        if (Strings.isNullOrEmpty(oldMember.getCardNo())) {
            oldMember.setScore(oldMember.getScore() + 5);
        }

        oldMember.setAccountName(name);
        oldMember.setCardNo(cardNo);
        userService.update(oldMember);
        session.setAttribute(SysConstant.WX_USER, oldMember);
        return new APIResponse(APIResponse.SUCCESS);
    }

    @RequestMapping("/isFollow")
    @ResponseBody
    public APIResponse isFollow(@RequestParam String openId) {
        String accessToken = weixinService.getAccessToken();
        boolean isFollow = false;
        try{
            String url = String.format(WXConstant.USER_INFO, accessToken, openId);
            JSONObject jsonObject = JSONObject.parseObject(HttpClientUtils.getByHttps(url));
            if (!jsonObject.containsKey("errcode")) {
                if ("1".equals(jsonObject.getString("subscribe"))) {
                    isFollow = true;
                }
            }
        }catch (Exception e) {}
        APIResponse res = new APIResponse(APIResponse.SUCCESS);
        res.initedData().put("isFollow", isFollow);
        return res;
    }


    @RequestMapping("/getUser.do")
    @ResponseBody
    public APIResponse getUser(@RequestParam String openId) {

        Member oldMember = userService.getUserByOpenid(openId);

        APIResponse res =  new APIResponse(APIResponse.SUCCESS);
        res.initedData().put("user", oldMember);
        return res;
    }


}
