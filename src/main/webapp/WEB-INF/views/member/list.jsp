<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/taglib.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
     <script type="text/javascript" src="${basepath}/resources/js/member/memberList.js"></script>
    <meta charset="UTF-8">
    <title>-会员列表</title>
  </head>
  
  <body>
  	<div class="location_min"></div>
      <div class="main_right">

          <form id="searchForm" onsubmit="return false;">
              <input type="hidden" name="pageNum" id="pageNum" value="1"/>
              <div class="search_box_car fr">
                  <label>账户名/手机号码</label>
                  <input type="text" name="keywords" placeholder="请输入" />
                  <input type="button" value="搜索" onClick="doSearch()"/>
              </div>
          </form>


          <div class="pcnyList" id="dataTable">

          </div>
      </div>


    <!--弹框/s -->
    <div class="pop" >

    </div>
    <!--弹框/e -->
</body>


</html>
