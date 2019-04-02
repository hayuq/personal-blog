<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"
    isELIgnored="false" trimDirectiveWhitespaces="true"%>
<!DOCTYPE html>
<html>
<head>
	<title>欢迎登录博客后台管理系统</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
	<link href="${ctx}/static/css/admin.css" rel="stylesheet">
	<link href="${ctx}/favicon.ico" rel="SHORTCUT ICON">
	<link href="${ctx}/static/bootstrap3/css/bootstrap.min.css" rel="stylesheet">
    <link href="${ctx}/static/bootstrap3/css/bootstrap-theme.min.css" rel="stylesheet">
</head>
<body class="loginbg" onload="showErrMsg()">
	<div id="mainbody">
		<div id="cloud1" class="cloud"></div>
		<div id="cloud2" class="cloud"></div>
	</div>
	<div class="logintop">
		<span>欢迎登录博客后台管理系统</span>
		<ul>
			<li><a href="${ctx}/">回首页</a></li>
			<li><a href="#">帮助</a></li>
			<li><a href="${ctx}/about.shtml">关于</a></li>
		</ul>
	</div>
	<div class="loginbody">
		<div class="loginbox">
			<span class="title">hayuq博客后台管理系统</span>
			<form id="loginForm" action="${ctx }/admin/userLogin.do" method="post" onsubmit="return checkLogin()">
				<div class="userwrapper">
				    <input id="rememberMe" name="rememberMe" type="hidden" value="false">
				    <input id="redirectUrl" name="redirectUrl" type="hidden">
				    <input id="csrfToken" name="csrfToken" type="hidden" value="${requestScope.csrfToken }">
					<input id="username" name="username" type="text" class="loginuser" placeholder="用户名">
				</div>
				<div class="pwdwrapper">
					<input id="password" name="password" type="password" class="loginpwd" placeholder="密码">
				</div>
				<div>
				    <span style="float:left;">
					    <input type="checkbox" name="remember_me" id="remember_me">
					    <label for="remember_me">记住我</label>
				    </span>
				    <span style="float:right;">
				        <a href="#" class="link">忘记密码？</a>
				        <input type="hidden" id="errMsg" value="${error }">
				    </span>
					<!-- <input type="button" class="loginbtn" value="登录" onclick="login()">
					<input type="button" class="resetbtn" value="重置" onclick="$('#loginForm')[0].reset()"> -->
				</div>
				<div>
				    <input type="submit" class="btn btn-info" id="loginbtn" value="登录">
				</div>
			</form>
		</div>
	</div>
	<script>var ctxPath = '${ctx}/';</script>
	<script src="${ctx}/static/js/jquery.min.js"></script>
	<script src="${ctx}/static/layer/layer.js"></script>
	<script>
	    // 重写alert
	    window.alert = function(msg, options){
	        var _default = {icon: 0};
	        $.extend(_default, options);
	        layer.msg(msg, _default);
	    }

	    // 显示错误提示信息
	    function showErrMsg() {
	        var msg = $("#errMsg").val();
	    	msg && alert(msg);
	    }
	    
	    $(function() {
	    	$('input[name="remember_me"]').on('change', function() {
	    		console.log(this, this.checked)
	    		$('#rememberMe').val(this.checked);
	    	})
	    });
	    
	    function checkLogin() {
	    	var username = $('#username').val();
	        var password = $('#password').val();
	        if(username.trim() === '') {
	            alert("请输入用户名");
	            return false;
	        }
	        if(password.trim() === '') {
	            alert("请输入密码");
	            return false;
	        }
	        return true;
	    }
	</script>
	<%-- <script src="${ctx}/static/js/login.js"></script> --%>
    <script src="${ctx}/static/js/cloud.js"></script>
</body>
</html>
