<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>修改密码</title>
<%@include file="/WEB-INF/admin/head.jsp" %>
</head>
<body>
	<div class="place">
		<span>位置：</span>
		<ul class="placeul">
			<li><a href="javascript:void(0)">首页</a></li>
			<li><a href="javascript:void(0)">个人管理</a></li>
			<li>修改密码</li>
		</ul>
	</div>
	
	<div class="rightinfo">
			<table class="table">
				<tr>
					<td style="width:50px">账号</td>
					<td><input type="text" id="userName" value="${currentUser.userName }" class="scinput" readonly/></td>
				</tr>
				<tr>
					<td style="width:50px">原密码</td>
					<td><input type="text" id="oldpwd" class="scinput"/></td>
				</tr>
				<tr>
					<td style="width:50px">新密码</td>
					<td><input type="text" id="newpwd" class="scinput"/></td>
				</tr>
			</table>
			<input type="button" class="scbtn" value="保存" onclick="savePassword()"/>
			<input type="button" class="scbtn" value="返回" onclick="history.back()"/>
			<span id="errorInfo" style="font-size:16px"></span>
		</form>
	</div>
</body>
</html>