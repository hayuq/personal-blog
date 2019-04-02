<%@page import="java.io.File"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" import="com.baidu.ueditor.ActionEnter"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%
    request.setCharacterEncoding("utf-8");
	response.setHeader("Content-Type" , "text/html");
	
	String rootPath = application.getRealPath( "/" );
	String result = new ActionEnter(request, rootPath).exec();

	String action = request.getParameter("action");
	if(action != null && ("listfile".equals(action) || "listimage".equals(action))){
	    rootPath = rootPath.replace("\\", "/");
	    result = result.replaceAll(rootPath, "");
	}
	out.write(result);
	
%>