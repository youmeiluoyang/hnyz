<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/taglib.jsp" %>
<div class="btm_pannel bw-paging">
    <div class="pannel_tip bw-paging-tip">当前记录总数为<span class="forange bw-range"><c:out value="${param.totalRows }"/></span>条</div>
    <div class="page bw-pages">
    	<span class="bw-pages-wrap">
	    	<c:if test="${param.curPage>=1}">
	            <a href="javascript:void(0);" class="prev bw-paging-prev" onclick="pageJump(${param.curPage > 1 ? param.curPage-1 : 1},null,'${param.searchFormName }')">上一页</a>
	        </c:if>
	        <span class="bw-page-detail">${param.curPage}/${param.totalPages}</span>
	        <c:if test="${param.curPage<=param.totalPages+0}">
	        	<a href="javascript:void(0);" class="next bw-paging-next" onclick="pageJump(${param.curPage < param.totalPages+0 ? param.curPage+1 : param.totalPages},null,'${param.searchFormName }')">下一页</a>
	        </c:if>
        </span>
        <input type="text" name="toPage_" value="${param.curPage}" class="bw-skip-page" />
        <input type="hidden" name="totalPages_" value="${param.totalPages}" />
        <button class="bw-skip" onclick="pageJumpBtn();" id="pageJumpBtn">跳转</button>
    </div>
</div>
