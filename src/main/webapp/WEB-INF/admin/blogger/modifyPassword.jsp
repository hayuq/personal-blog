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
					<td style="width:50px">原密码<input type="hidden" name="id" id="id" value="${currentUser.id }"/></td>
					<td>
						<input type="text" name="oldpwd" id="oldpwd" class="scinput" required="required"/>
					</td>
				</tr>
				<tr>
					<td style="width:50px">新密码</td>
					<td><input type="text" name="newpwd" id="newpwd" class="scinput" required="required"/></td>
				</tr>
				<tr>
					<td style="width:60px">确认新密码</td>
					<td><input type="text" name="repwd" id="repwd" class="scinput" required="required"/></td>
				</tr>
			</table>
			<input type="button" class="scbtn" value="保存" onclick="savePwd()"/>
			<input type="button" class="scbtn" value="返回" onclick="history.back()"/>
			<span id="errorInfo" style="font-size:16px"></span>
		</form>
	</div>
</body>
</html>