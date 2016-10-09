<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/WEB-INF/admin/head.jsp" %>
<title>博客后台管理系统</title>
</head>
<!-- 后台页面主框架 -->
<frameset rows="88,*" cols="*" frameborder="no" border="0" framespacing="0">
  <frame src="admin/top.do" name="topFrame" scrolling="no" noresize="noresize" id="topFrame" title="topFrame" />
  <frameset cols="187,*" frameborder="no" border="0" framespacing="0">
    <frame src="admin/left.do" name="leftFrame" scrolling="no" noresize="noresize" id="leftFrame" title="leftFrame" />
    <frame src="blog/list.do" name="rightFrame" id="rightFrame" title="rightFrame" />
  </frameset>
</frameset>
</html>