package com.dg11185.hnyz.service.api;

import com.dg11185.hnyz.bean.Member.Member;

/**
 * @Author zouwei
 * @Datetime 2018-03-27 11:16
 */

public interface UserService {

    void saveOrUpdateUser(Member newUser);

    Member getUserByOpenid(String openid);

    void update(Member oldMember);
}
