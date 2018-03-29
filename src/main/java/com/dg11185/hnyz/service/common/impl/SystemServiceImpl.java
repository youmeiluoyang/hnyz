package com.dg11185.hnyz.service.common.impl;

import com.dg11185.hnyz.bean.common.ResponseForm;
import com.dg11185.hnyz.bean.system.Admin;
import com.dg11185.hnyz.dao.system.SystemDao;
import com.dg11185.hnyz.service.common.SystemService;
import com.dg11185.hnyz.util.LogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xiesp
 * @description
 * @date 9:58 AM  3/29/2018
 * @copyright 全国邮政电子商务运营中心
 */
@Service
public class SystemServiceImpl implements SystemService{

    private final static Logger log = LoggerFactory.getLogger(SystemServiceImpl.class);
    @Autowired
    private SystemDao systemDao;

    @Override
    public Admin getAdmin(String loginName) {
        return systemDao.getAdmin(loginName);
    }


    @Override
    public ResponseForm tryLogin(String loginName,String pwd) {
        ResponseForm responseForm = new ResponseForm();
        try{
            Admin admin =this.getAdmin(loginName);
            if (admin == null){
                responseForm.setStatus(ResponseForm.ResponseStatus.ERROR.getName());
                //表示没有这个用户
                responseForm.setCode("-1");
                return responseForm;
            }
            if(pwd.equals(admin.getPwd())){
                responseForm.setDataEntry("admin",admin);
            }else{
                responseForm.setStatus(ResponseForm.ResponseStatus.ERROR.getName());
                //表示密码错误
                responseForm.setCode("-2");
            }
        }catch (Exception e){
            log.error("[用户登录]发生异常:" + LogUtil.getTrace(e));
            responseForm.setStatus(ResponseForm.ResponseStatus.ERROR.getName());
            responseForm.setCode("-3");
        }
        return responseForm;
    }


    @Override
    public ResponseForm changePwd(String oldPwd, String newPwd,Admin admin) {
        ResponseForm responseForm = new ResponseForm();
        try{
            if(admin.getPwd().equals(oldPwd)){
                admin.setPwd(newPwd);
                systemDao.updateAdmin(admin);
            }else{
                responseForm.setStatus(ResponseForm.ResponseStatus.ERROR.getName());
                //密码不对
                responseForm.setCode("-1");
            }
        }catch (Exception e){
            log.error("[修改密码]发生异常:" + LogUtil.getTrace(e));
            responseForm.setStatus(ResponseForm.ResponseStatus.ERROR.getName());
            responseForm.setCode("-2");
        }

        return responseForm;
    }
}
