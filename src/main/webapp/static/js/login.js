//登录页面样式
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

// 重写alert
window.alert = function(msg, options){
	var _default = {icon: 0};
	$.extend(_default, options);
	layer.msg(msg, _default);
}

function reset() {
	$("#username").val('');
	$("#password").val('');
}

function login() {
	var username = $("#username").val();
	var password = $("#password").val();
	if(username.replace(/\s+/g,"") == ""){
		alert("请输入用户名");
		return;
	}
	if(password.replace(/\s+/g,"") == ""){
		alert("请输入密码");
		return;
	}
	var data = {
		"username": username,
		"password": password
	};
	$.post("admin/userLogin.do", data, function(result) { 
		if (result) {
			alert(result);
			return;
		}
		window.location.href = "admin/index.do";
	});
}