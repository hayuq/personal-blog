<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>添加链接</title>
<%@include file="/WEB-INF/admin/head.jsp" %>
</head>
<body onload="showMsg()">
	<div class="place">
		<span>位置：</span>
		<ul class="placeul">
			<li><a href="javascript:void(0)">首页</a></li>
			<li><a href="${ctx}/link/list.do">链接管理</a></li>
			<li>添加链接</li>
		</ul>
	</div>
	<div class="rightinfo">
		<form action="${ctx}/link/add.do" method="post" onsubmit="return checkLinkData()">
			<table class="table">
				<tr>
					<td style="width:60px">链接名称</td>
					<td><input name="linkName" id="linkName" type="text" class="scinput" style="width:200px"/></td>
				</tr>
				<tr>
					<td style="width:60px">链接地址</td>
					<td><input name="linkUrl" id="linkUrl" type="text" class="scinput" style="width:200px"/> 请以http://或https://开头</td>
				</tr>
				<tr>
		   			<td style="width:60px">显示顺序</td>
		   			<td><input name="orderNo" id="orderNo" type="text" class="scinput"/></td>
		   		</tr>
			</table>
			<input type="submit" class="scbtn" value="保存"/>
			<input type="button" class="scbtn" value="返回" onclick="history.back()" />
			<input type="hidden" id="msg" value="${msg }"/>
		</form>
	</div>
</body>
</html>