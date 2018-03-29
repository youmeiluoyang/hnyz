package com.dg11185.hnyz.dao.member;

import com.dg11185.hnyz.bean.Member.Member;
import com.dg11185.hnyz.bean.Member.MemberIncreaseForm;
import com.dg11185.hnyz.bean.common.PageRequest;
import com.dg11185.hnyz.bean.common.PageWrap;
import com.dg11185.hnyz.dao.BaseDAO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author xiesp
 * @description
 * @date 5:25 PM  3/29/2018
 */
@Repository
public class MemberDao extends BaseDAO {


    /**
     * 统计各网点用户增长数量
     *
     * @return
     */
    public List<MemberIncreaseForm> queryMemberIncrease() {
        String sql = "select count(a.ids) count,b.names orgName from tb_member a RIGHT JOIN tb_org b on b.orgNo = a.orgNo group by b.names";
        List<MemberIncreaseForm> list = this.queryForList(sql, MemberIncreaseForm.class);
        return list;
    }


    /**
     * 分页查询用户
     *
     * @return
     */
    public PageWrap<Member> queryMemberByPage() {
        String sql = "select * from tb_member";
        PageRequest pageRequest = new PageRequest();
        PageWrap<Member> wrap = this.queryForPage(sql,new Object[]{},pageRequest,Member.class);
        return wrap;
    }

}

