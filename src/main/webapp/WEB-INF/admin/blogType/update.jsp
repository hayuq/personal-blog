<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改类别</title>
<%@include file="/WEB-INF/admin/head.jsp" %>
</head>
<body>
	<div class="place">
		<span>位置：</span>
		<ul class="placeul">
			<li><a href="javascript:void(0)">首页</a></li>
			<li><a href="blogType/list.do">类别管理</a></li>
			<li>修改类别</li>
		</ul>
	</div>
	<div class="rightinfo">
		<form action="blogType/update.do" method="post">
			<table class="table">
				<tr>
					<td style="width:60px">类别名称<input type="hidden" name="typeId" value="${blogType.typeId }"/></td>
					<td><input name="typeName" type="text" class="scinput" value="${blogType.typeName }"/></td>
				</tr>
				<tr>
		   			<td style="width:60px">排序</td>
		   			<td><input name="orderNo" type="text" class="scinput" value="${blogType.orderNo }"/></td>
		   		</tr>
			</table>
			<input type="submit" class="scbtn" value="保存"/>
			<input type="button" class="scbtn" value="返回" onclick="history.back()" />
		</form>
	</div>
</body>
</html>