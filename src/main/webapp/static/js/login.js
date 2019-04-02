// 重写alert
window.alert = function(msg, options){
	var _default = {icon: 0};
	$.extend(_default, options);
	layer.msg(msg, _default);
}

function login() {
	var username = $("#username").val();
	var password = $("#password").val();
	if(username.trim() == "") {
		alert("请输入用户名");
		return;
	}
	if(password.trim() == "") {
		alert("请输入密码");
		return;
	}
	// 禁用按钮，防止重复提交
	var $button = $('#loginbtn');
	if (!$button.hasClass('disabled')) {
		$button.addClass('disabled');
	}
	var rememberMe = $('#remember_me').attr('checked');
	console.log('rememberMe', rememberMe)
	$('#rememberMe').val(rememberMe);
	$.post(
		ctxPath + "admin/userLogin.do", 
		$('#loginForm').serialize(), 
		function(result) {
			if (result.code === 200) {
				window.location.href = ctxPath + "admin/index.do";
			} else {
				if (result.csrfToken) {
					$('#csrfToken').val(result.csrfToken);
				}
				alert(result.msg, {icon: 2});
			}
			if ($button.hasClass('disabled')) {
				$button.removeClass('disabled');
			}
		}, "json");
}