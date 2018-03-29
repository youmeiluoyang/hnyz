package com.dg11185.hnyz.common.constant;

import com.dg11185.hnyz.common.config.SysConfig;

/**
 * 一些常用的配置
 */
public class ConfigConstant {

    // 系统配置文件
    public static final String SYS_CONFIGFILE = "/sysConfig.properties";
    /** 静态资源上传绝对路径 **/
    public static final String RES_ROOT_PATH = SysConfig.getProperty("res.root.path");

}
