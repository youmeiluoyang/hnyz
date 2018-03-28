<%@ page contentType="application/json;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%
	response.setStatus(401);
%>
{"status":"error", "message":"401-当前请求未授权，您没有操作权限."}
