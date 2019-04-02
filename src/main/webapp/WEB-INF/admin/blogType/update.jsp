<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改类别</title>
<%@include file="/WEB-INF/admin/head.jsp" %>
</head>
<body onload="showMsg()">
	<div class="place">
		<span>位置：</span>
		<ul class="placeul">
			<li><a href="javascript:void(0)">首页</a></li>
			<li><a href="${ctx}/blogType/list.do">类别管理</a></li>
			<li>修改类别</li>
		</ul>
	</div>
	<div class="rightinfo">
		<form action="${ctx}/blogType/update.do" onsubmit="return checkBlogType()" method="post">
			<table class="table">
				<tr>
					<td style="width:60px">类别名称<input type="hidden" name="typeId" id="typeId" value="${blogType.typeId }"/></td>
					<td><input name="typeName" id="typeName" type="text" class="scinput" value="${blogType.typeName }"/></td>
				</tr>
				<tr>
		   			<td style="width:60px">显示顺序</td>
		   			<td><input name="orderNo" id="orderNo" type="text" class="scinput" value="${blogType.orderNo }"/></td>
		   		</tr>
			</table>
			<input type="submit" class="scbtn" value="保存"/>
			<input type="button" class="scbtn" value="返回" onclick="history.back()" />
			<input type="hidden" id="msg" value="${msg }"/>
		</form>
	</div>
</body>
</html>