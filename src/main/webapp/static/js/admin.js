$(function() {
	// 下拉框
	$(".select").uedSelect({
		width : 150
	});
	$('.tablelist tbody tr:odd').addClass('odd');

	// 左侧功能菜单导航切换
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
});

//重写alert
window.alert = function(msg, options, callback) {
	var _default = {
		title: '提示',
		skin: 'layui-layer-molv',
		shade: 0.1,
		icon: 0
	};
	$.extend(_default, options);
	layer.alert(msg, _default, function(index) {
		layer.close(index);
		(typeof(callback) === "function") && callback("ok");
	});
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
	})
}

function savePassword() {
	var newpwd = $("#newpwd").val();
	var oldpwd = $("#oldpwd").val();
	if (oldpwd.trim() == '') {
		alert("请输入原密码");
		return;
	}
	if (newpwd.trim() == '') {
		alert("请输入新密码");
		return;
	}
	var params = {
		"userName" : $("#userName").val(),
		"newpwd" : newpwd,
		"oldpwd" : oldpwd
	};
	$.post(ctxPath + "blogger/modifyPassword.do", params, function(result) {
		if (result.code == 200) {
			layer.close(index);
			alert("密码修改成功，下次登录生效", {icon: 1});
		} else {
			alert(result.msg, {icon: 2});
		}
	})
}

function checkBlog() {
	var title = $("#title").val();
	var typeId = $("#type").val();
	var content = editor.getContent();
	$("#typeName").val($("#type option:selected").text());
	if (title.replace(/\s+/g,"") == '') {
		alert("请填写标题");
		return false;
	}
	if (typeId == 0) {
		alert("请选择博客类别");
		return false;
	}
	if (content.replace(/\s+/g,"") == '') {
		alert("博客内容不允许为空");
		return false;
	}
	var summary = editor.getContentTxt();
	$("#summary").val(summary.length > 200 ? summary.substr(0, 200) + "..." : summary);
	return true;
}

function checkBlogType() {
	var typeName = $("#typeName").val();
	var orderNo = $("#orderNo").val();
	if (typeName.replace(/\s+/g,"") == '') {
		alert("请填写类别名称");
		return false;
	}
	if (orderNo.replace(/\s+/g,"") == '') {
		alert("请输入显示顺序");
		return false;
	}
	if (!/^\d+$/.test(orderNo)) {
		alert("显示顺序只能是数字");
		return false;
	}
	return true;
}

function checkLinkData() {
	var linkName = $("#linkName").val();
	var linkUrl = $("#linkUrl").val();
	var orderNo = $("#orderNo").val();
	if (linkName.replace(/\s+/g,"") == '') {
		alert("请输入链接名称");
		return false;
	}
	if (linkUrl.replace(/\s+/g,"") == '') {
		alert("请输入链接地址");
		return false;
	}
	if(!/^(http|https):\/\/[\w\-_]+(\.[\w\-_]+)+([\w\-\.,@?^=%&amp;:/~\+#]*[\w\-\@?^=%&amp;/~\+#])?/.test(linkUrl)) {
		alert("请输入格式正确的链接地址");
		return false;
	}
	if (orderNo.replace(/\s+/g,"") == '') {
		alert("请输入显示顺序");
		return false;
	}
	if (!/^\d+$/.test(orderNo)) {
		alert("显示顺序只能是数字");
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
	$.post(ctxPath + "comment/audit.do", data, function(result) {
		if (result.code == 200) {
			alert("审核成功", {icon: 1});
		} else {
			alert(result.msg, {icon: 2});
		}
	});
}

function deleteOne(url, id) {
	confirm('确定删除该条数据吗？', function() {
		$.post(url, {id: id}, function(result) {
			if (result.code == 200) {
				alert("删除成功", {icon: 1}, function() {					
					window.location.reload();
				});
			} else {
				alert(result.msg, {icon: 2});
			}
		});
	});
}