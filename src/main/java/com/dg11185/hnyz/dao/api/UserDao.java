package com.dg11185.hnyz.dao.api;

import com.dg11185.hnyz.bean.User;
import com.dg11185.hnyz.dao.BaseDAO;
import org.springframework.stereotype.Repository;

/**
 * @Author zouwei
 * @Datetime 2018-03-27 11:18
 */
@Repository
public class UserDao extends BaseDAO  {

    public void saveOrUpdateUser(User newUser) {
        String sql = "select count(*) from ";
    }

}
