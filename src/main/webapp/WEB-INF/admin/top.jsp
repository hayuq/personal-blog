<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%@include file="/WEB-INF/admin/head.jsp" %>
</head>
<body style="color:#f1fafa;background:rgba(0,0,0,0.6)">
	<div class="topleft">
    	<span style="font-size:16px;padding:10px">博客系统后台管理</span>
    </div>
        
    <div class="topright"> 
	    <ul>
		    <!-- <li><span><img src="static/images/admin/help.png" title="帮助"  class="helpimg"/></span><a href="#">帮助</a></li> -->
		    <li><a href="javascript:void(0)" onclick="window.parent.location.href='index.shtml'">回首页</a></li>
		    <li><a href="javascript:void(0)" onclick="window.parent.location.href='admin/logout.do'">退出</a></li>
		    <li>  欢迎您：<strong>${currentUser.userName }</strong> </li>
	    </ul>
	     
	    <div class="user">
		    <!-- <i>消息</i>
		    <b>5</b> -->
	    </div>
    </div>
</body>
</html>