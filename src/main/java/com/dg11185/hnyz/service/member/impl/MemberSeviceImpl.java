package com.dg11185.hnyz.service.member.impl;

import com.dg11185.hnyz.bean.Member.Member;
import com.dg11185.hnyz.bean.Member.MemberIncreaseForm;
import com.dg11185.hnyz.bean.common.PageWrap;
import com.dg11185.hnyz.dao.member.MemberDao;
import com.dg11185.hnyz.service.member.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author xiesp
 * @description
 * @date 5:26 PM  3/29/2018
 */
@Service
public class MemberSeviceImpl implements MemberService{

    @Autowired
    private MemberDao memberDao;


    @Override
    public List<MemberIncreaseForm> queryMemberIncrease() {
        List<MemberIncreaseForm> list = memberDao.queryMemberIncrease();
        return list;
    }

    @Override
    public PageWrap<Member> queryMemberByPage() {
        PageWrap<Member> wrap = memberDao.queryMemberByPage();
        return wrap;
    }
}
