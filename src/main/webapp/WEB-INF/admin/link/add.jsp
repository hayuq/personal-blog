<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>添加链接</title>
<%@include file="/WEB-INF/admin/head.jsp" %>
</head>
<body>
	<div class="place">
		<span>位置：</span>
		<ul class="placeul">
			<li><a href="javascript:void(0)">首页</a></li>
			<li><a href="link/list.do">链接管理</a></li>
			<li>添加链接</li>
		</ul>
	</div>
	<div class="rightinfo">
		<form action="link/add.do" method="post">
			<table class="table">
				<tr>
					<td style="width:60px">链接名称</td>
					<td><input name="linkName" type="text" class="scinput" style="width:200px"/></td>
				</tr>
				<tr>
					<td style="width:60px">链接地址</td>
					<td><input name="linkUrl" type="text" class="scinput" style="width:200px"/> 默认以http://开头</td>
				</tr>
				<tr>
		   			<td style="width:60px">显示顺序</td>
		   			<td><input name="orderNo" type="text" class="scinput"/></td>
		   		</tr>
			</table>
			<input type="submit" class="scbtn" value="保存"/>
			<input type="button" class="scbtn" value="返回" onclick="history.back()" />
		</form>
	</div>
</body>
</html>