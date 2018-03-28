<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/common/taglib.jsp" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="Cache-Control" content="no-siteapp"/>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=9,IE=10,IE=11,IE=edge,chrome=1">
    <meta name="description" content="中国邮政微信服务号管理系统">
    <title>中国邮政微信服务号管理系统</title>
    <link href="${basepath}/resources/css/big-wx.css" rel="stylesheet"/>
    <link href="${basepath}/resources/css/login.css" rel="stylesheet"/>
    <link href="${basepath}/resources/css/dialog.css" rel="stylesheet"/>
    <script type="text/javascript" src="${basepath}/resources/js/jquery-1.11.0.min.js"></script>
    <script type="text/javascript" src="${basepath}/resources/js/jquery.md5.js"></script>
    <script type="text/javascript" src="${basepath}/resources/js/jsencrypt.min.js"></script>
    <script type="text/javascript">
        $(function () {
            var regxp = /[^0-9a-zA-Z_\u4e00-\u9fa5]/;
            // 这里主要是为了进入登录页时，保证是浏览器顶层进入，而不是局部进入登录页
            var tag = '${tag}';
            if (tag != "refreshed") {
                window.top.location = "${basepath}/common/login.do?tag=refreshed&msg=${msg}&userName=${userName}&pubnumType=service";
            }

            // 点击获取新的验证码
            $(".bw-login-captcha").click(function () {
                $(".bw-login-captcha").attr("src", "${basepath}/captcha.do?t=" + Math.random());
            });

            $("#loginForm").submit(function () {
                var userName = $("#userName").val().replace(/^\s+|\s+$/g, ""); // 用户名
                var userPwd = $("#userPwd").val(); // 密码
                var captcha = $("#captcha").val(); // 验证码
                if (userName == "") {
                    $(".msg").html("操作提示：用户名不能为空!");
                    $("#userName").focus();
                    $("#userName").select();
                    return false;
                }
                if (regxp.test(userName)) {
                    $(".msg").html("操作提示：登录名不合法!");
                    $("#userName").focus();
                    $("#userName").select();
                    return false;
                }
                if (userPwd == "") {
                    $(".msg").html("操作提示：登录密码不能为空!");
                    $("#userPwd").focus();
                    $("#userPwd").select();
                    return false;
                }
                if (captcha == "") {
                    $(".msg").html("操作提示：验证码不能为空!");
                    $("#captcha").focus();
                    $("#captcha").select();
                    return false;
                }
                // 先对登录密码进行MD5加密，再通过RSA公钥进行加密传输
                var encryptUtil = new JSEncrypt();
                encryptUtil.setPublicKey($("input[name=pubkey]").val());
                $("input[name=userPwd]").val(encryptUtil.encrypt($.md5(userPwd)));
                $("input[name=userName]").val(encryptUtil.encrypt(userName));
                return true;
            });
        });
    </script>
</head>
<body class="bw-login-2 service">
<div class="bw-wrap">
    <div class="bw-header">
        <div class="bw-title"><i class="bw-icon"></i>中国邮政微信服务号管理系统</div>
    </div>
    <div class="bw-login-container">
        <form id="loginForm" action="${basepath}/common/login.do" method="post">
            <div class="bw-login-content">
                <img src="${basepath}/resources/images/i-service-small.png" class="login-pic">
                <p class="login-type">微信服务号账号登录</p>
                <input class="bw-login-input" type="text" value="${userName}" id="userName" placeholder="请输入登录账号">
                <input class="bw-login-input" type="password" id="userPwd" placeholder="请输入登录密码">
                <input class="bw-login-input-captcha" type="text" name="captcha" id="captcha" placeholder="请输入验证码">
                <img class="bw-login-captcha" src="${basepath}/captcha.do" title="看不清？点击重新获取"/>
                <div class="bw-login-tips">
                    <%-- <span class="bw-remember-account"><i class="bw-selected-orange"></i><span>记住账号</span></span>--%>
                    <%--<a href='javascript:createAlertMsgMainWin("技术小伙伴们正在紧张研发中哦，敬请期待^v^", "red");' class="bw-forget-password">忘记密码？</a>--%>
                </div>
                <div class="bw-btnarea">
                    <input type="hidden" name="userName" value=""/>
                    <input type="hidden" name="userPwd" value=""/>
                    <input type="hidden" name="pubnumType" value="${pubnumType}"/>
                    <input type="hidden" name="pubkey" value="${pubkey}"/>
                    <input type="submit" class="bw-btn-login" value="登录">
                </div>
                <div class="bw-msg">
                    <span class="msg">${msg}</span>
                </div>
                <%--<div class="bw-how-do howToUse"><a href="##">怎么操作?</a></div>--%>
            </div>
        </form>
    </div>
</div>
</body>
</html>