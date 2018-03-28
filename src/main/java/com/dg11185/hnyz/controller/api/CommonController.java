package com.dg11185.hnyz.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.dg11185.hnyz.bean.User;
import com.dg11185.hnyz.bean.common.APIResponse;
import com.dg11185.hnyz.common.config.SysConfig;
import com.dg11185.hnyz.common.constant.SysConstant;
import com.dg11185.hnyz.common.constant.WXConstant;
import com.dg11185.hnyz.service.api.UserService;
import com.dg11185.hnyz.util.LogUtil;
import com.dg11185.util.network.HttpClientUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.net.URLEncoder;

/**
 * @Author zouwei
 * @Datetime 2018-03-27 08:53
 * @Copyright 全国邮政电子商务运营中心
 */

@Controller
@RequestMapping("/api")
public class CommonController {

    private static Logger log = LoggerFactory.getLogger(CommonController.class);

    @Autowired
    private UserService userService;

    @RequestMapping(value="wiki.do")
    public String wiki() {
        return "wiki";
    }

    /**
     * 判断用户是否已经登录
     * @param scope (snsapi_base:不弹出授权页面，直接跳转，只能获取用户openid; snsapi_userinfo:弹出授权页面，可通过openid拿到昵称、性别、所在地。并且，即使在未关注的情况下，只要用户授权，也能获取其信息)
     * @param session
     * @return result.data.isLogin 0：未登录；1：已登录
     */
    @RequestMapping("/isLogin.do")
    @ResponseBody
    public APIResponse isLogin(String test, String testOpenid, String scope, HttpSession session) {
        try {
            APIResponse rsp = new APIResponse(APIResponse.SUCCESS);
            if (StringUtils.isNotBlank(test) && "test".equals(test)) {	// *************************测试代码
                User user = new User();
                user.setIds(1000000);
                user.setOpenid(StringUtils.isBlank(testOpenid) ? "123456" : testOpenid);
                session.setAttribute(SysConstant.WX_USER, user);
            }
            rsp.initedData().put("isLogin", session.getAttribute(SysConstant.WX_USER) != null);
            return rsp;
        } catch (Exception e) {
            return new APIResponse(APIResponse.INNER_ERROR);
        }
    }


    /**
     * 跳转至微信中以获得授权CODE
     * @param scope (snsapi_base:不弹出授权页面，直接跳转，只能获取用户openid; snsapi_userinfo:弹出授权页面，可通过openid拿到昵称、性别、所在地。并且，即使在未关注的情况下，只要用户授权，也能获取其信息)
     * @param callBackUrl 登录后希望的回调地址，传入时注意URLENCODE
     * @return
     */
    @RequestMapping("/toLogin.do")
    public String toLogin(String scope, @RequestParam String callBackUrl, HttpSession session) {
        try {
            // scope默认为snsapi_base；snsapi_base：仅获得openid; snsapi_userinfo：获得用户全部信息
            scope = StringUtils.isBlank(scope) ? "snsapi_base" : scope;
            // 告知微信回调的地址
            String redirectUri = SysConfig.getProperty("project.domain") + "/api/" + "/loginCallBack.do";
            // 重定向的目标地址
            String url = "redirect:" + WXConstant.OAUTH2_CODE
                    + "appid=" + WXConstant.APP_ID
                    + "&redirect_uri=" + URLEncoder.encode(redirectUri, "utf-8")
                    + "&response_type=code"
                    + "&scope=" + scope
                    + "&state=" + URLEncoder.encode(callBackUrl, "utf-8")
                    + "#wechat_redirect";

            log.info("【组件统一登录】重定向以获得CODE, callBackUrl={}", callBackUrl);
            return url;
        } catch (Exception e) {
            log.error("【组件统一登录】重定向以获得CODE, 系统异常, callBackUrl={},{}", callBackUrl, LogUtil.getTrace(e));
            return "redirect:" + callBackUrl;
        }
    }

    /**
     * 统一处理微信登录回调，本地登录后回调callBackUrl
     * @param code 微信传入
     * @param state 微信传入，自定义参数，callBackUrl
     * @param session
     * @return
     */
    @RequestMapping("/loginCallBack.do")
    public String loginCallBack(String code, String state, HttpSession session) {
        // 之前传入的前端回调
        String callBackUrl = state;
        try {
            // 根据CODE，请求微信接口获得accessToken
            String atUrl = WXConstant.OAUTH2_ACCESS_TOKEN
                    + "appid=" + WXConstant.APP_ID
                    + "&secret=" + WXConstant.SECRET
                    + "&code=" + code
                    + "&grant_type=authorization_code";
            JSONObject atJson = JSONObject.parseObject(HttpClientUtils.getRequest(atUrl));
            // 成功获取accessToken
            if (!atJson.containsKey("errcode")) {
                User user = null;
                // 需要获得完整用户信息
                if (atJson.getString("scope").contains("snsapi_userinfo")) {
                    // 根据openid，请求微信接口获得用户信息
                    String uiUrl = WXConstant.USER_INFO
                            + "access_token=" + atJson.getString("access_token")
                            + "&openid=" + atJson.getString("openid")
                            + "&lang=zh_CN";
                    JSONObject uiJson = JSONObject.parseObject(HttpClientUtils.getRequest(uiUrl));
                    if (!uiJson.containsKey("errcode")) {    // 获得最新用户信息
                        User newUser = uiJson.toJavaObject(User.class);
                        newUser.setPrivilegess(uiJson.getString("privilege"));
                        // 插入或更新数据
                        userService.saveOrUpdateUser(newUser);
                    }
                    // 获得数据库中的数据
                    user = userService.getUserByOpenid(atJson.getString("openid"));

                } else {// 不需要获得完整用户信息，仅需获得openid
                    user = new User();
                    user.setOpenid(atJson.getString("openid"));
                }
                if (user != null) {
                    // 将用户保存到会话
                    session.setAttribute(SysConstant.WX_USER, user);
                }
            }

        } catch (Exception e) {
            log.error("【组件统一登录】系统异常。 code={}, state={}, {}", code, state, LogUtil.getTrace(e));
        }
        return "redirect:" + callBackUrl;
    }


    /**
     * 获得用户完整信息
     * @param session
     * @return
     */
    @RequestMapping("/getUserInfo.do")
    @ResponseBody
    public APIResponse getUserInfo(HttpSession session) {
        User user = (User) session.getAttribute(SysConstant.WX_USER);
        APIResponse rsp = new APIResponse(APIResponse.SUCCESS);
        rsp.initedData().put("user", user);
        return rsp;
    }

}
