<%@ page language="java" pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>

    <!--全局页面变量 -->
    <script type="text/javascript">
        var $basepath = '${basepath}';
        var ctx = '${ctx}';
        var base = '${base}';
        var env = '${env}';

        //===== 百度统计接入 ---开始 ===
        var _hmt = _hmt || [];
        (function() {
            var hm = document.createElement("script");
            hm.src = "https://hm.baidu.com/hm.js?${env == 'prod' ?
            '3aeddf87837fb3131df89d67625f22fd' : '8ca55011589b1d698dee6daf1614a3d5'}";
            var s = document.getElementsByTagName("script")[0];
            s.parentNode.insertBefore(hm, s);
        })();
        //===== 百度统计接入 ---结束 ===
    </script>
</head>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="base" value="${pageContext.request.contextPath}"/>
<c:set var="env" value="${applicationScope.env}"/>