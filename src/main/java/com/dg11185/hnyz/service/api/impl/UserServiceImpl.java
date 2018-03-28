package com.dg11185.hnyz.service.api.impl;

import com.dg11185.hnyz.bean.User;
import com.dg11185.hnyz.dao.api.UserDao;
import com.dg11185.hnyz.service.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author zouwei
 * @Datetime 2018-03-27 11:17
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public void saveOrUpdateUser(User newUser) {
        userDao.saveOrUpdateUser(newUser);
    }

    @Override
    public User getUserByOpenid(String openid) {
        return null;
    }
}
