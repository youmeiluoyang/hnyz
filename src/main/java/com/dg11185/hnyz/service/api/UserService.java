package com.dg11185.hnyz.service.api;

import com.dg11185.hnyz.bean.User;

/**
 * @Author zouwei
 * @Datetime 2018-03-27 11:16
 */

public interface UserService {
    void saveOrUpdateUser(User newUser);

    User getUserByOpenid(String openid);
}
