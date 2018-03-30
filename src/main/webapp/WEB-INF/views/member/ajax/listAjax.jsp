<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<table class="tb_result">
    <tr>
        <th>openId</th>
        <th>昵称</th>
        <th>头像</th>
        <th>手机号码</th>
        <th>账户名</th>
        <th>卡号</th>
        <th>地址</th>
        <th>注册时间</th>
        <th>积分</th>
        <%--<th width="198">操作</th>--%>
    </tr>
    <c:forEach items="${wrap.list }" var="mem" varStatus="vsr">
        <tr>
            <td>${mem.openId}</td>
            <td>${mem.nickName }</td>
            <td>${mem.headImgUrl }</td>
            <td>${mem.telephone }</td>
            <td>${mem.accountName}</td>
            <td>${mem.cardNo}</td>
            <td>${mem.address}</td>
            <td>${mem.createDate}</td>
            <td>${mem.score}</td>
<%--            <td>
                <a class="fblue" href="${ctx}/admin/showAdmin.do?adminId=${admin.ids}">修改用户</a>
            </td>--%>
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
