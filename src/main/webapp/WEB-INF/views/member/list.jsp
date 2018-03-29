<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/taglib.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <meta charset="UTF-8">
    <title>-会员列表</title>
  </head>
  
  <body>
  	<div class="location_min"></div>
      <div class="main_right">
          <div class="pcnyList" id="dataTable">

          </div>
          <jsp:include page="/WEB-INF/views/common/paging.jsp" flush="true">
              <jsp:param  value="${page.curPage }" name="curPage"/>
              <jsp:param  value="${page.totalRows }" name="totalRows"/>
              <jsp:param  value="${page.totalPages }" name="totalPages"/>
              <jsp:param  value="${page.beginPageNo }" name="beginPageNo"/>
              <jsp:param  value="${page.endPageNo }" name="endPageNo"/>
          </jsp:include>
      </div>


    <!--弹框/s -->
    <div class="pop">
        
    </div>
    <!--弹框/e -->
</body>
</html>
