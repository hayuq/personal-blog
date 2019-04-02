<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/WEB-INF/admin/head.jsp" %>
</head>
<body style="color:#f1fafa;background:rgba(0,0,0,0.6)">
	<div class="topleft">
    	<span style="font-size:16px;padding:10px">博客系统后台管理</span>
    </div>
        
    <div class="topright"> 
	    <ul>
		    <li><a href="javascript:void(0)" onclick="window.parent.location.href='${ctx}/'">回首页</a></li>
		    <li><a href="javascript:void(0)" onclick="window.parent.location.href='${ctx}/admin/logout.do'">退出</a></li>
		    <li>  欢迎您：<strong>${currentUser.userName }</strong> </li>
	    </ul>
    </div>
</body>
</html>