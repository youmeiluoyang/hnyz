<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/common/taglib.jsp" %>
<div class="bw-left-nav">
    <ul class="bw-nav-list">
        <c:forEach items="${sessionScope.authPerms}" var="perms">
            <li class="bw-main-item">
                <h5 class="bw-breif"><i class="${perms.perm.iconName}"></i>${perms.perm.permName}</h5>
                <ul class="bw-items">
                    <c:forEach items="${perms.sonPerms}" var="sonPerm">
                        <li class="bw-item">
                            <h5 class="bw-title"><a href="${basepath}/${sonPerm.perm.permUrl}">${sonPerm.perm.permName}</a></h5>
                            <ul  class="bw-sub-titles">
                                <c:forEach items="${sonPerm.thirdPerms}" var="thirdPerm">
                                    <li  class="bw-sub-title">
                                        <h5 class="bw-title"><a href="${basepath}/${thirdPerm.permUrl}">${thirdPerm.permName}</a></h5>
                                    </li>
                                </c:forEach>
                            </ul>
                        </li>
                    </c:forEach>
                </ul>
            </li>
        </c:forEach>
    </ul>
</div>
