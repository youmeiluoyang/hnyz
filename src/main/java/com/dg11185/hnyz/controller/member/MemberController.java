package com.dg11185.hnyz.controller.member;

import com.dg11185.hnyz.bean.Member.Member;
import com.dg11185.hnyz.bean.Member.MemberIncreaseForm;
import com.dg11185.hnyz.bean.common.PageRequest;
import com.dg11185.hnyz.bean.common.PageWrap;
import com.dg11185.hnyz.bean.common.SearchForm;
import com.dg11185.hnyz.service.member.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author xiesp
 * @description
 * @date 5:24 PM  3/29/2018
 */
@Controller
@RequestMapping("/member/*")
public class MemberController {

    @Autowired
    private MemberService memberService;


    /**
     * 统计用户增长
     */
    @RequestMapping(value = "memberIncrease.do")
    @ResponseBody
    public  List<MemberIncreaseForm> memberIncrease(HttpServletRequest request, HttpSession session) {
        List<MemberIncreaseForm> list = memberService.queryMemberIncrease();
        return list;
    }


    /**
     * 用户列表
     */
    @RequestMapping(value = "list.do")
    public  String  list(HttpServletRequest request, HttpSession session, PageRequest pageRequest, Model model) {
        return "member/list";
    }

    /**
     * 异步返回列表数据
     */
    @RequestMapping(value = "listAjax.do")
    public  String  listAjax(HttpServletRequest request, HttpSession session, SearchForm searchForm, Model model) {
        PageWrap<Member> wrap = memberService.queryMemberByPage(searchForm);
        model.addAttribute("wrap",wrap);
        return "member/ajax/listAjax";
    }


}
