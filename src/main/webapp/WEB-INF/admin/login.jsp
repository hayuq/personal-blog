<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>欢迎登录后台管理系统</title>
<%@include file="/WEB-INF/admin/head.jsp" %>
</head>
<body class="loginbg">
	<div id="mainBody">
		<div id="cloud1" class="cloud"></div>
		<div id="cloud2" class="cloud"></div>
	</div>

	<div class="logintop">
		<span>欢迎登录后台管理平台</span>
		<ul>
			<li><a href="#">回首页</a></li>
			<li><a href="#">帮助</a></li>
			<li><a href="#">关于</a></li>
		</ul>
	</div>

	<div class="loginbody">
		<div class="loginbox">
			<span class="title">博客系统管理登录</span>
				<div class="userwrapper">
					<input id="username" type="text" class="loginuser" placeholder="用户名"/>
				</div>
				<div class="pwdwrapper">
					<input id="password" type="password" class="loginpwd" placeholder="密码"/>
				</div>
				<div class="btnwrapper">
					<input type="button" class="loginbtn" value="登录" onclick="login()"/>
					<input type="button" class="resetbtn" value="重置" onclick="reset()"/>
				</div>
			<%-- <ul>
				<li><input id="username" type="text" class="loginuser" value="admin" /></li>
				<li><input id="password" type="password" class="loginpwd" value="密码" onclick="JavaScript:this.value=''" /></li>
				<li>
					<input type="button" class="loginbtn" value="登录" onclick="login()"/>
					<label><input name="" type="checkbox" value="" checked="checked" />记住密码</label>
					<label><a href="#">忘记密码？</a></label>
				</li>
			</ul> --%>
		</div>
	</div>
</body>
</html>
