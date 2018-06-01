<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/taglib.jsp"%>
<!DOCTYPE>
<html>
<head>
    <script type="text/javascript" src="${basepath}/resources/js/echarts.common.min.js"></script>
    <script type="text/javascript" src="${basepath}/resources/js/zrender.min.js"></script>
    <script type="text/javascript" src="${basepath}/resources/js/member/quesStat.js"></script>
    <link rel="stylesheet" href="${ctx }/resources/css/quesStat.css"/>
    <meta charset="UTF-8">
    <title>问卷统计</title>
</head>

<body>
<div class="location_min"></div>
<div class="main_right" style="overflow: auto">
    <div class="row">
        <div class="col col-lg-4"><div class="echart"></div></div>
        <div class="col col-lg-4"><div class="echart"></div></div>
        <div class="col col-lg-4"><div class="echart"></div></div>
    </div>
    <div class="row">
        <div class="col col-lg-4"><div class="echart"></div></div>
        <div class="col col-lg-4"><div class="echart"></div></div>
        <div class="col col-lg-4"><div class="echart"></div></div>
    </div>
    <div class="row">
        <div class="col col-lg-4"><div class="echart"></div></div>
        <div class="col col-lg-4"><div class="echart"></div></div>
        <div class="col col-lg-4"><div class="echart"></div></div>
    </div>
    <div class="row">
        <div class="col col-lg-4"><div class="echart"></div></div>
        <div class="col col-lg-4"><div class="echart"></div></div>
        <div class="col col-lg-4"><div class="echart"></div></div>
    </div>

</div>


<!--弹框/s -->
<div class="pop" >

</div>
<!--弹框/e -->
</body>


</html>
