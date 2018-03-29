package com.dg11185.hnyz.service.common;

import com.dg11185.hnyz.bean.common.ResponseForm;
import com.dg11185.hnyz.bean.system.Admin;

/**
 * @author xiesp
 * @description
 * @date 9:56 AM  3/29/2018
 * @copyright 全国邮政电子商务运营中心
 */
public interface SystemService {

    /**
     * 根据用户名查找管理员
     * @param loginName
     * @return
     */
    Admin getAdmin(String loginName);

    /**
     * 用户登录逻辑
     * @return
     */
    ResponseForm tryLogin(String loginName,String pwd);


    /**
     * 修改密码
     */
    ResponseForm changePwd(String oldPwd,String newPwd,Admin admin);

}
