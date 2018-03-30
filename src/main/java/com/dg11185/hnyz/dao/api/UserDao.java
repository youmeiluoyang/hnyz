package com.dg11185.hnyz.dao.api;

import com.dg11185.hnyz.bean.Member.Member;
import com.dg11185.hnyz.dao.BaseDAO;
import org.springframework.stereotype.Repository;

/**
 * @Author zouwei
 * @Datetime 2018-03-27 11:18
 */
@Repository
public class UserDao extends BaseDAO  {

    public void saveOrUpdateUser(Member member) {
        String sql = "select count(*) from tb_member where openId = ?";
        if (queryForBean(sql, new Object[]{member.getOpenId()}, Integer.class) > 0) {
            String update = "update tb_member set nickname = ?,headimgurl = ? where openId = ?";
            saveOrUpdate(update, member.getNickName(), member.getHeadImgUrl(), member.getOpenId());
        }else {
            String insert = "insert into tb_member (openId, nickName, headimgurl) values (?, ?, ?)";
            saveOrUpdate(insert, member.getOpenId(), member.getNickName(), member.getHeadImgUrl());
        }
    }

    public Member getUserByOpenid(String openid) {
        String sql = "select * from tb_member where openId = ?";
        Member member = queryForBean(sql, new Object[]{openid}, Member.class);
        return member;
    }
}
