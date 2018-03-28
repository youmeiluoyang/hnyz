<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/common/taglib.jsp" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="Cache-Control" content="no-siteapp"/>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=9,IE=10,IE=11,IE=edge,chrome=1">
    <meta name="description" content="中国邮政微信公众号服务管理系统">
    <title>中国邮政微信公众号服务管理系统</title>
    <link href="${basepath}/resources/css/big-wx.css" rel="stylesheet"/>
    <link href="${basepath}/resources/css/login.css" rel="stylesheet"/>
    <link href="${basepath}/resources/css/dialog.css" rel="stylesheet"/>
    <script type="text/javascript" src="${basepath}/resources/js/jquery-1.11.0.min.js"></script>
    <script type="application/javascript">
        // 这里主要是为了进入登录页时，保证是浏览器顶层进入，而不是局部进入登录页
        var tag = '${tag}';
        if (tag != "refreshed") {
            window.top.location = "${basepath}/common/login.do?tag=refreshed";
        }
    </script>
</head>
<body class="bw-login">
<div class="bw-wrap">
    <div class="bw-login-main">
        <a class="service-login" href="${basepath}/common/login.do?pubnumType=service">
            <img src="${basepath}/resources/images/i-service.png" alt="">
            <p>中国邮政微信服务号管理系统</p>
        </a>
        <div class="middle-hr"></div>
        <a class="subscription-login" href="${basepath}/common/login.do?pubnumType=subscription">
            <img src="${basepath}/resources/images/i-subscript.png" alt="">
            <p>中国邮政微信订阅号管理系统</p>
        </a>
    </div>
    <img src="${basepath}/resources/images/login.gif" alt="" class="login-bg">
</div>
</body>
</html>