/*var _hmt = _hmt || [];
(function() {
	var hm = document.createElement("script");
	hm.src = "https://hm.baidu.com/hm.js?52aab7c4b6078350eff50efdb81f030a";
	var s = document.getElementsByTagName("script")[0];
	s.parentNode.insertBefore(hm, s);
})();*/

$(function() {
	$(".select").uedSelect({
		width : 150
	});
	$('.tablelist tbody tr:odd').addClass('odd');
});

//重写alert
window.alert = function(msg, options) {
	var _default = {icon: 0};
	$.extend(_default, options);
	layer.msg(msg, _default);
}

//重写confirm
window.confirm = function(msg, callback) {
	layer.confirm(msg, {
		title: '提示',  
		shade: 0.1,
		skin: 'layui-layer-molv',
		icon: 3,
		btn: ['确定','取消']
	}, function() { //确定事件
		(typeof(callback) === "function") && callback("ok");
	});
}

$(function() {
	//左侧功能菜单导航切换
	$(".menuson .header").click(function() {
		var $parent = $(this).parent();
		$(".menuson>li.active").not($parent)
			.removeClass("active open")
			.find('.sub-menus').hide();
		$parent.addClass("active");
		if (!!$(this).next('.sub-menus').size()) {
			if ($parent.hasClass("open")) {
				$parent.removeClass("open").find('.sub-menus').hide();
			} else {
				$parent.addClass("open").find('.sub-menus').show();
			}
		}
	});

	$('.title').click(function() {
		var $ul = $(this).next('ul');
		$('dd').find('.menuson').slideUp();
		if ($ul.is(':visible')) {
			$(this).next('.menuson').slideUp();
		} else {
			$(this).next('.menuson').slideDown();
		}
	});
})

function checkBlog() {
	var title = $("#title").val();
	var type = $("#type").val();
	var content = editor.getContent();
	var summary = editor.getContentTxt();
	$("#summary").val(summary.length > 200 ? summary.substr(0, 200) + "..." : summary);
	$("#typeName").val($("#type option:selected").text());
	if (title.trim() == '') {
		alert("请填写标题");
		return false;
	}
	if (content.trim() == '') {
		alert("博客内容不允许为空");
		return false;
	}
	return true;
}

//图片上传前预览
function previewImg(file) {
	var prevImg = document.getElementById('preview');
	if (file.files && file.files[0]) {
		//创建FileReader对象
		var reader = new FileReader();
		reader.onload = function(evt) {
			prevImg.src = evt.target.result;
		}
		// 读取File对象的数据
		// 当FileReader对象通过readAsDataURL读取数据成功后，就会触发load事件。
		reader.readAsDataURL(file.files[0]);
	} else {
		prevImg.src = file.value;
	}
}

//显示提示信息
function showMsg() {
	var msg = $("#msg").val();
	if (msg) alert(msg);
}

//修改密码
function savePwd() {
	var newpwd = $("#newpwd").val();
	var repwd = $("#repwd").val();
	var oldpwd = $("#oldpwd").val();
	if (oldpwd.trim() == '') {
		$("#errorInfo").css("color", "red");
		$("#errorInfo").html("请输入原密码");
		return;
	}
	if (newpwd.trim() == '') {
		$("#errorInfo").css("color", "red");
		$("#errorInfo").html("请输入新密码");
		return;
	}
	if (repwd.trim() == '') {
		$("#errorInfo").css("color", "red");
		$("#errorInfo").html("请再次输入新密码");
		return;
	}
	var params = {
		"id" : $("#id").val(),
		"newpwd" : newpwd,
		"oldpwd" : oldpwd,
		"repwd" : repwd
	};
	$.post("blogger/modifyPassword.do", params, function(result) {
		if (result == '修改成功') {
			$("#errorInfo").css("color", "black");
			$("#errorInfo").html("修改成功,下次登录时生效");
		} else {
			$("#errorInfo").css("color", "red");
			$("#errorInfo").html(result);
		}
	});
}

function checkInfo() {
	var nickName = $("#nickName").val();
	var sign = $("#sign").val();
	var content = editor.getContent();
	if (nickName.trim() == '') {
		alert("请填写昵称");
		return false;
	}
	if (sign.trim() == '') {
		alert("请填写签名");
		return false;
	}
	if (content.trim() == '') {
		alert("个人简介不允许为空");
		return false;
	}
	return true;
}

function reply() {
	var id = $("#id").val();
	var isPass = $("#isPass").val();
	var reply = $("#review").val();
	var data = {
		"id": id,
		"isPass" : isPass,
		"reply": reply
	};
	$.post("comment/audit.do", data, function(result) {
		alert(result.success ? "提交成功" : "提交失败");
	});
}