package com.dg11185.hnyz.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.dg11185.hnyz.bean.common.APIResponse;
import com.dg11185.hnyz.bean.common.wx.Fans;
import com.dg11185.hnyz.bean.common.wx.FansDetail;
import com.dg11185.hnyz.common.config.SysConfig;
import com.dg11185.hnyz.common.constant.SysConstant;
import com.dg11185.hnyz.common.constant.WXConstant;
import com.dg11185.hnyz.service.api.WeixinService;
import com.dg11185.hnyz.service.api.WxApiService;
import com.dg11185.hnyz.util.LogUtil;
import com.dg11185.hnyz.util.wx.WxMessageUtil;
import com.dg11185.util.network.HttpClientUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.net.URLEncoder;
import java.util.Map;

/**
 * @author xiesp
 * @description
 * @date 4:25 PM  4/28/2018
 * @copyright 全国邮政电子商务运营中心
 */
@Controller
@RequestMapping("/api/wx")
public class WxApiController {


    private static Logger log = LoggerFactory.getLogger(WxApiController.class);
    @Autowired
    private WxApiService wxApiService;
    @Autowired
    private WeixinService weixinService;
    /**
     * 判断用户是否已经登录
     * @param session
     * @return result.data.isLogin 0：未登录；1：已登录
     */
    @RequestMapping(value = "/isLogin.do",method = RequestMethod.POST)
    @ResponseBody
    public APIResponse isLogin(String test, String testOpenid, HttpSession session) {
        try {
            APIResponse rsp = new APIResponse(APIResponse.SUCCESS);
            if (StringUtils.isNotBlank(test) && "test".equals(test) &&"oioFts1-YOJ2QXb5dG4GyM7t6Hzs1".equals(testOpenid)) {	// *************************测试代码
                Fans fans = new Fans();
                fans.setOpenid("oioFts1-YOJ2QXb5dG4GyM7t6Hzs1");
                fans.setHeadimgurl("http://thirdwx.qlogo.cn/mmopen/vi_32/w0gsRnCGlXhzybrJKa93wMnLxNCO9PekicJyxun1EN50QGGZG70ibic6MRsUJAKGFqR6mjsX06e1oca9V6xNyZFHg/132");
                fans.setNickname("xiesp");
                rsp.initedData().put("isLogin", true);
                rsp.initedData().put("fan", fans);
                session.setAttribute(SysConstant.WX_FRONT_USER, fans);
                log.info("【组件统一登录】测试用户登录");
                return rsp;
            }
            Fans fan= (Fans) session.getAttribute(SysConstant.WX_FRONT_USER);
            if (fan != null) {
                session.setAttribute(SysConstant.WX_FRONT_USER, fan);
                rsp.initedData().put("isLogin", true);
                rsp.initedData().put("fan", fan);
                log.info("【组件统一登录】当前用户已登录");
            } else {
                log.info("【组件统一登录】当前用户未登录");
                rsp.initedData().put("isLogin", false);
            }
            return rsp;
        } catch (Exception e) {
            log.error("【组件统一登录】:判断用户是否登录发生异常:" + LogUtil.getTrace(e));
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
    public String toLogin(String scope, @RequestParam String callBackUrl) {
        try {
            // scope默认为snsapi_base；snsapi_base：仅获得openid; snsapi_userinfo：获得用户全部信息
            scope = StringUtils.isBlank(scope) ? "snsapi_base" : scope;
            // 告知微信回调的地址
            String redirectUri = SysConfig.getProperty("project.domain") + "/api/wx" + "/loginCallBack.do";
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
            log.info("【组件统一登录】微信网页授权回调返回：{}", atJson);
            // 成功获取accessToken
            if (!atJson.containsKey("errcode")) {
                Fans fans= new Fans();
                // 需要获得完整用户信息
                if (atJson.getString("scope").contains("snsapi_userinfo")) {
                    // 根据openid，请求微信接口获得用户信息
                    String uiUrl = WXConstant.WEB_USER_INFO
                            + "access_token=" + atJson.getString("access_token")
                            + "&openid=" + atJson.getString("openid")
                            + "&lang=zh_CN";
                    JSONObject uiJson = JSONObject.parseObject(HttpClientUtils.getRequest(uiUrl));
                    if (!uiJson.containsKey("errcode")) {
                        fans.setOpenid(uiJson.getString("openid"));// 获得最新用户信息
                        fans.setHeadimgurl(uiJson.getString("headimgurl"));
                        fans.setNickname(uiJson.getString("nickname"));

                    }
                } else {// 不需要获得完整用户信息，仅需获得openid
                    fans.setOpenid(atJson.getString("openid"));
                }
                // 将用户保存到会话
                session.setAttribute(SysConstant.WX_FRONT_USER, fans);
            }

        } catch (Exception e) {
            log.error("【组件统一登录】微信网页授权回调系统异常。 code={}, state={}, {}", code, state, LogUtil.getTrace(e));
        }
        return "redirect:" + callBackUrl;
    }


    /**
     * 获取粉丝信息,可以判定是不是粉丝
     * @param session
     * @return
     */
    @RequestMapping(value = "/getFansInfo.do",method = RequestMethod.POST)
    @ResponseBody
    public APIResponse getUserInfo(HttpSession session) {
            APIResponse rsp = new APIResponse(APIResponse.SUCCESS);
        try{
            Fans fans = (Fans) session.getAttribute(SysConstant.WX_FRONT_USER);
            FansDetail fansDetail = wxApiService.getFansDetail(fans.getOpenid());
            rsp.initedData().put("fans", fansDetail);
        }catch (Exception e){
            rsp.setStatus(APIResponse.INNER_ERROR);
            log.error("【组件统一登录】:获取粉丝详情发生错误:" + LogUtil.getTrace(e));
        }
        return rsp;
    }


    /**
     * 获取jS接口签名
     * @return
     */
    @RequestMapping(value = "/getJsApiSign.do",method = RequestMethod.POST)
    @ResponseBody
    public APIResponse getJsApiSign(String url) {
        APIResponse rsp = new APIResponse(APIResponse.SUCCESS);
        try{
            String jsTicket = weixinService.gerJsApiToken();
            System.out.println(url);
            Map<String,String> sign = WxMessageUtil.wxJSApiSign(url,jsTicket);
            rsp.initedData().put("sign", sign);
        }catch (Exception e){
            rsp.setStatus(APIResponse.INNER_ERROR);
            log.error("【微信接口】:获取JSAPI 签名发生失败:" + LogUtil.getTrace(e));
        }
        return rsp;
    }

}
