package com.dg11185.hnyz.dao.system;

import com.dg11185.hnyz.bean.system.Admin;
import com.dg11185.hnyz.dao.BaseDAO;
import org.springframework.stereotype.Repository;

/**
 * @author xiesp
 * @description
 * @date 9:59 AM  3/29/2018
 */
@Repository
public class SystemDao extends BaseDAO{


    /**
     * 获取管理员
     * @param loginName
     * @return
     */
    public Admin getAdmin(String loginName){
        String sql = "select * from tb_admin t   where t.loginName = ?";
        Admin admin = this.queryForBean(sql,new Object[]{loginName},Admin.class);
        return admin;
    }


    /**
     * 修改管理员
     * @return
     */
    public int updateAdmin(Admin admin){
       return this.update(admin,"ids");
    }

}
