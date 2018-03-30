package com.dg11185.hnyz.service.member;

import com.dg11185.hnyz.bean.Member.Member;
import com.dg11185.hnyz.bean.Member.MemberIncreaseForm;
import com.dg11185.hnyz.bean.common.PageWrap;
import com.dg11185.hnyz.bean.common.SearchForm;

import java.util.List;

/**
 * @author xiesp
 * @description
 * @date 5:25 PM  3/29/2018
 */
public interface MemberService {



    /**
     * 统计各网点用户增长数量
     * @return
     */
    List<MemberIncreaseForm> queryMemberIncrease();


    /**
     * 分页查询用户
     *
     * @return
     */
    PageWrap<Member> queryMemberByPage(SearchForm searchForm);

}
