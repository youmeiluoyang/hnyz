package com.dg11185.hnyz.common.constant;

import com.dg11185.hnyz.common.config.ResourceConfig;

/**
 * @Author zouwei
 * @Datetime 2018-03-27 09:59
 * @Copyright 全国邮政电子商务运营中心
 */

public class WXConstant {

    public final static String APP_ID = "";
    public final static String SECRET = "";

    // 网页授权 请求预授权code
    public final static String OAUTH2_CODE = ResourceConfig.getWxApiConfig().getProperty("address.oauth2.authorize");

    // 网页授权 请求access_token（网页授权token）
    public final static String OAUTH2_ACCESS_TOKEN = ResourceConfig.getWxApiConfig().getProperty("address.oauth2.component.accessToken");

    // 网页授权 获取用户信息
    public final static String USER_INFO = ResourceConfig.getWxApiConfig().getProperty("address.userinfo");


}
