package com.dg11185.hnyz.service.member;

import com.dg11185.hnyz.bean.Member.MemberIncreaseForm;

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

}
