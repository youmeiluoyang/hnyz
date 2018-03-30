<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/taglib.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <meta charset="UTF-8">
    <title>首页</title>
      <script type="text/javascript" src="${basepath}/resources/js/echarts.common.min.js"></script>
      <script type="text/javascript" src="${basepath}/resources/js/common/index.js"></script>
  </head>
  
  <body>
  	<div class="location_min"></div>
      <div class="main_right">
          <h1 class="center">会员注册统计</h1>
          <div id="echart" style="width: 90%;height:90%;"></div>

          <div class="pcnyList" id="dataTable">
          </div>

      </div>

    <!--弹框/s -->
    <div class="pop">
        
    </div>
    <!--弹框/e -->
</body>
</html>
