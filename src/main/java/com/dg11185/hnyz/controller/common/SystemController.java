package com.dg11185.hnyz.controller.common;

import com.dg11185.hnyz.bean.common.ResponseForm;
import com.dg11185.hnyz.bean.system.Admin;
import com.dg11185.hnyz.common.constant.SysConstant;
import com.dg11185.hnyz.service.common.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *  用户登录、退出登录,首页业务流程控制层
 * @author xiesp
 * @description
 * @date 5:25 PM  3/26/2018
 */
@Controller
@RequestMapping("/system/*")
public class SystemController {



    @Autowired
    private SystemService systemService;

    /**
     * 跳转到登录页面
     */
    @RequestMapping(value = "login.do", method = RequestMethod.GET)
    public String login(HttpServletRequest request, HttpSession session, Model model) {
        return "/system/login";
    }


    /**
     * 验证登录
     */
    @RequestMapping(value = "toLogin.do")
    @ResponseBody
    public ResponseForm toLogin(HttpServletRequest request, HttpSession session) {
        ResponseForm responseForm = null;
        String name = request.getParameter("loginName");
        String pwd = request.getParameter("loginPwd");
        responseForm = systemService.tryLogin(name,pwd);
        if(responseForm.getDataEntry("admin")!=null){
            session.setAttribute(SysConstant.LOGIN_ADMIN,responseForm.getDataEntry("admin"));
        }
        return responseForm;
    }


    /**
     * 退出系统
     */
    @RequestMapping(value = "logout.do", method = RequestMethod.GET)
    public String logout(HttpServletRequest request, HttpSession session) {
        session.removeAttribute(SysConstant.LOGIN_ADMIN);
        return "/system/login";
    }

    /**
     * 修改密码
     */
    @RequestMapping(value = "changePwd.do")
    @ResponseBody
    public ResponseForm changePwd(HttpServletRequest request, HttpSession session) {
        ResponseForm responseForm = null;
        String oldPwd = request.getParameter("oldPwd");
        String newPwd = request.getParameter("newPwd");
        Admin admin = (Admin) session.getAttribute(SysConstant.LOGIN_ADMIN);
        responseForm = systemService.changePwd(oldPwd, newPwd,admin);
        return responseForm;
    }
}
