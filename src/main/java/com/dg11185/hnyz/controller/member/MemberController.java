package com.dg11185.hnyz.controller.member;

import com.dg11185.hnyz.bean.Member.Member;
import com.dg11185.hnyz.bean.Member.MemberIncreaseForm;
import com.dg11185.hnyz.bean.common.PageRequest;
import com.dg11185.hnyz.bean.common.PageWrap;
import com.dg11185.hnyz.bean.common.ResponseForm;
import com.dg11185.hnyz.bean.common.SearchForm;
import com.dg11185.hnyz.bean.front.lottery.Reward;
import com.dg11185.hnyz.bean.front.question.Question;
import com.dg11185.hnyz.service.api.front.LotteryService;
import com.dg11185.hnyz.service.api.front.QuestionService;
import com.dg11185.hnyz.service.member.MemberService;
import com.dg11185.hnyz.util.LogUtil;
import com.dg11185.util.common.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

/**
 * @author xiesp
 * @description
 * @date 5:24 PM  3/29/2018
 */
@Controller
@RequestMapping("/member/*")
public class MemberController {

    private static Logger log = LoggerFactory.getLogger(MemberController.class);

    @Autowired
    private MemberService memberService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private LotteryService lotteryService;


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



    /**
     * 获取问卷的统计信息
     */
    @RequestMapping(value = "quesStat.do")
    public  String  quesStat(HttpServletRequest request, HttpSession session) {
        return "member/quesStat";
    }



    /**
     * 获取问卷的统计信息
     */
    @RequestMapping(value = "doQuesStat.do")
    @ResponseBody
    public ResponseForm doQuesStat(HttpServletRequest request, HttpSession session) {
        ResponseForm responseForm = new ResponseForm();
        try{
            List<Question> questions = questionService.statQuestion();
            responseForm.setDataEntry("stat",questions);
        }catch (Exception e){
            responseForm.setStatus(ResponseForm.ResponseStatus.ERROR.getName());
            log.error("[问卷统计]统计问卷发生错误:" + LogUtil.getTrace(e));
        }
        return  responseForm;
    }


    /**
     * 抽奖统计页面
     */
    @RequestMapping(value = "lotteryStat.do")
    public String lotteryStat(HttpServletRequest request, HttpSession session) {
        return "member/lottery";
    }


    /**
     * 中奖列表的数据
     */
    @RequestMapping(value = "lotteryListAjax.do")
    public  String  lotteryListAjax(Model model,SearchForm searchForm) {
        PageWrap<Reward> wrap = lotteryService.getRewardByPage(searchForm);
        model.addAttribute("wrap",wrap);
        return "member/ajax/lotteryListAjax";
    }


    /**
     *后台更新奖品状态
     */
    @RequestMapping(value = "updateReward.do")
    @ResponseBody
    public ResponseForm updateReward(String ids) {
        ResponseForm responseForm = new ResponseForm();
        try{
            this.lotteryService.updateReward(ids);
        }catch (Exception e){
            responseForm.setStatus(ResponseForm.ResponseStatus.ERROR.getName());
            log.error("[抽奖]更新奖品状态发生错误:" + LogUtil.getTrace(e));
        }
        return  responseForm;
    }


    /**
     * 导出图文统计列表
     * @author luosiyin
     * 2017年2月16日
     */
    @RequestMapping("export.do")
    public void export(HttpSession session, HttpServletResponse response, SearchForm searchForm) {
        searchForm.setPageSize(Integer.MAX_VALUE);
        OutputStream outputStream = null;
        try {
            //设置导出格式，导出文件名
            String filename = "中奖统计" + DateUtil.getNowDate("yyyyMMdd") + ".xls";
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment;filename=".
                    concat(String.valueOf(URLEncoder.encode(filename, "UTF-8"))));
            outputStream = response.getOutputStream();
            // 执行导出操作
            lotteryService.export(searchForm, outputStream);
        } catch (IOException e) {
            log.error("【菜单分析】导出报表系统异常。" + LogUtil.getTrace(e));
        } finally {
            try {
                outputStream.close();
            } catch (IOException e) {
                log.error("【菜单分析】导出报表系统异常。" + LogUtil.getTrace(e));
            }
        }
    }
}
