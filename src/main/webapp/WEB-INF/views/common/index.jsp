<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/taglib.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <meta charset="UTF-8">
    <title>首页</title>
    <script type="text/javascript">
	</script>
  </head>
  
  <body>
  	<div class="location_min"></div>
      <div class="main_right">
          <div class="pcnyData clearfix">
              <div class="pcnyCol" style="background-color:#25ADF7" >
                  <div class="num waitCount">${totalCount.mQuoteCount }</div>
                  <div class="data_tip">人工<br/>报价</div>
              </div>
              <div class="pcnyCol" style="background-color:#8AB704"  onClick="window.location='${ctx}/insurance/manualOrderList.do?firstMenu=61&secondMenu=63'">
                  <div class="num failCount">${totalCount.mInsOrderCount }</div>
                  <div class="data_tip">预约<br/>购买</div>
              </div>
          </div>
         
          <div class="pcnyList" id="dataTable">
          </div>

      </div>

    <!--弹框/s -->
    <div class="pop">
        
    </div>
    <!--弹框/e -->
</body>
</html>
