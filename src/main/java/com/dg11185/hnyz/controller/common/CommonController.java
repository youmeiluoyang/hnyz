package com.dg11185.hnyz.controller.common;

import com.dg11185.hnyz.service.common.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 一些常见的请求,如首页等等
 * @author xiesp
 * @description
 * @date 9:23 AM  3/29/2018
 */
@Controller
@RequestMapping("/common/*")
public class CommonController {


    @Autowired
    private CommonService commonService;

    /**
     * 首页
     */
    @RequestMapping(value = "index.do", method = RequestMethod.GET)
    public String index(HttpServletRequest request, HttpSession session) {
        session.setAttribute("menu",commonService.getMenu());
        return "/common/index";
    }
}
