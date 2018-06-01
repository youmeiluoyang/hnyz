<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<table class="tb_result">
    <tr>
        <th>昵称</th>
        <th>头像</th>
        <th>奖品</th>
        <th>中奖时间</th>
        <th>状态</th>
        <th>单号</th>
        <th>操作</th>
    </tr>
    <c:forEach items="${wrap.list }" var="re" varStatus="vsr">
        <tr>
            <td>${re.nickName}</td>
            <td><img style="width: 30px;height: 30px;" src="${re.imgUrl}"/></td>
            <td>${re.rewardNames}</td>
            <td>${re.hitTime}</td>
            <td>
                <c:choose>
                    <c:when test="${re.state == 0}">
                        未发放
                    </c:when>
                    <c:when test="${re.state == 1}">
                        已发放
                    </c:when>
                    <c:when test="${re.state == 2}">
                        发放失败
                    </c:when>
                </c:choose>

            </td>
            <td>${re.billNo}</td>
            <td>
                <c:if test="${re.state == 0}">
                    <button class="btn btn-success btn-small updateState"  ids="${re.ids}">发放奖品</button>
                </c:if>
            </td>
        </tr>
    </c:forEach>
</table>
<jsp:include page="/WEB-INF/views/common/paging.jsp" flush="true">
    <jsp:param  value="${wrap.pageNum}" name="curPage"/>
    <jsp:param  value="${wrap.totalNum}" name="totalRows"/>
    <jsp:param  value="${wrap.totalPage}" name="totalPages"/>
    <jsp:param  value="1" name="beginPageNo"/>
    <jsp:param  value="5" name="endPageNo"/>
</jsp:include>
