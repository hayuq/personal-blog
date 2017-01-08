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
			<li><a href="link/list.do">链接管理</a></li>
			<li>修改链接</li>
		</ul>
	</div>
	<div class="rightinfo">
		<form action="link/update.do" method="post" enctype="multipart/form-data">
			<table class="table">
				<tr>
					<td style="width:60px">链接名称<input type="hidden" name="id" value="${link.id }"/></td>
					<td><input name="linkName" type="text" class="scinput" style="width:200px" value="${link.linkName }"/></td>
				</tr>
				<tr>
					<td style="width:60px">链接地址</td>
					<td><input name="linkUrl" type="text" class="scinput" style="width:200px" value="${link.linkUrl }"/> 默认以http://开头</td>
				</tr>
				<tr>
		   			<td style="width:60px">显示顺序</td>
		   			<td><input name="orderNo" type="text" class="scinput" value="${link.orderNo }"/></td>
		   		</tr>
			</table>
			<input type="submit" class="scbtn" value="保存"/>
			<input type="button" class="scbtn" value="返回" onclick="history.back()" />
			<input type="hidden" id="msg" value="${msg }"/>
		</form>
	</div>
</body>
</html>