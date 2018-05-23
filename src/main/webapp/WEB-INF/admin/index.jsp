<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>博客后台管理系统</title>
<%@include file="/WEB-INF/admin/head.jsp" %>
</head>
<!-- 后台页面主框架 -->
<frameset rows="50,*" cols="*" frameborder="no" border="0" framespacing="0">
  <frame src="admin/top.do" name="topFrame" scrolling="no" noresize="noresize" id="topFrame" title="topFrame" />
  <frameset cols="187,*" frameborder="no" border="0" framespacing="0">
    <frame src="admin/left.do" name="leftFrame" scrolling="no" noresize="noresize" id="leftFrame" title="leftFrame" />
    <frame src="blog/list.do" name="rightFrame" id="rightFrame" title="rightFrame" />
  </frameset>
</frameset>
</html>