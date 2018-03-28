<%@ page contentType="application/json;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" isErrorPage="true" %>
<%
	response.setStatus(400);
%>
{"status":"PARAMS_ERROR", "message":"参数异常，不完整或非法."}

