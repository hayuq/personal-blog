<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"
    isELIgnored="false" trimDirectiveWhitespaces="true"%>
<!DOCTYPE html>
<html>
<head>
<base href="${basePath }"/>
<link href="static/css/admin.css" rel="stylesheet"/>
<link href="favicon.ico" rel="SHORTCUT ICON">
<script type="text/javascript" src="static/js/jquery.min.js"></script>
<script type="text/javascript" src="static/layer/layer.js"></script>
<script type="text/javascript" src="static/js/login.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>欢迎登录博客后台管理系统</title>
</head>
<body class="loginbg">
	<div id="mainBody">
		<div id="cloud1" class="cloud"></div>
		<div id="cloud2" class="cloud"></div>
	</div>

	<div class="logintop">
		<span>欢迎登录博客后台管理系统</span>
		<ul>
			<li><a href="/">回首页</a></li>
			<li><a href="#">帮助</a></li>
			<li><a href="about.shtml">关于</a></li>
		</ul>
	</div>

	<div class="loginbody">
		<div class="loginbox">
			<span class="title">hayuq博客后台管理系统</span>
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
		</div>
	</div>
    <script type="text/javascript" src="static/js/cloud.js"></script>
</body>
</html>
