<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@include file="/WEB-INF/admin/head.jsp" %>
<title>欢迎登录后台管理系统</title>
<script language="javascript">
	$(function() {
		$('.loginbox').css({
			'position' : 'absolute',
			'left' : ($(window).width() - 350) / 2,
			'top' : ($(window).height() - 373) / 2
		});
		$(window).resize(function() {
			$('.loginbox').css({
				'position' : 'absolute',
				'left' : ($(window).width() - 350) / 2,
				'top' : ($(window).height() - 373) / 2
			});
		})
	});
</script>

</head>

<body
	style="background-image: url(static/images/admin/banner_slide_01.jpg); background-repeat: no-repeat; background-position: center top; overflow: hidden;">
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
			<span style="font-size:16px;color:#afc5d2">博客系统管理登录</span>
				<div style="margin-bottom:20px;margin-top:20px">
					<input id="username" type="text" class="loginuser" placeholder="用户名"/>
				</div>
				<div style="margin-bottom:20px">
					<input id="password" type="password" class="loginpwd" placeholder="密码"/>
				</div>
				<div style="float:left;padding-left:60px">
					<input type="button" class="loginbtn" value="登录" onclick="login()"/>
					<input type="button" class="resetbtn" value="重置" onclick="reset()"/>
				</div>
			<!-- <ul>
				<li><input id="username" type="text" class="loginuser" value="admin" /></li>
				<li><input id="password" type="password" class="loginpwd" value="密码" onclick="JavaScript:this.value=''" /></li>
				<li>
					<input type="button" class="loginbtn" value="登录" onclick="login()"/>
					<label><input name="" type="checkbox" value="" checked="checked" />记住密码</label>
					<label><a href="#">忘记密码？</a></label>
				</li>
			</ul> -->
		</div>
	</div>
<script type="text/javascript">
	function login(){
		var username = $("#username").val();
		var password = $("#password").val();
		$.post("admin/userLogin.do",{"username":username,"password":password},function(result){
			if(result){
				alert(result);
				return;
			}
			window.location.href="admin/index.do";
		});
	}
	
	function reset(){
		$("#username").val('');
		$("#password").val('');
	}
</script>
</body>
</html>
