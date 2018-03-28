package com.dg11185.hnyz.controller.system;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *  用户登录、退出登录业务流程控制层
 * @author xiesp
 * @description
 * @date 5:25 PM  3/26/2018
 * @copyright 全国邮政电子商务运营中心
 */
@Controller
@RequestMapping("/system/*")
public class LoginController {

    /**
     * 跳转到登录页面
     */
    @RequestMapping(value = "login.do", method = RequestMethod.GET)
    public String loginPage(HttpServletRequest request, HttpSession session, Model model) {

        return "/system/login";
    }

}
