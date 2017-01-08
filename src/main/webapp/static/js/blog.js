var _hmt = _hmt || [];
(function() {
	var hm = document.createElement("script");
	hm.src = "https://hm.baidu.com/hm.js?52aab7c4b6078350eff50efdb81f030a";
	var s = document.getElementsByTagName("script")[0];
	s.parentNode.insertBefore(hm, s);
})();

(function(){
    var bp = document.createElement('script');
    var curProtocol = window.location.protocol.split(':')[0];
    if (curProtocol === 'https') {
        bp.src = 'https://zz.bdstatic.com/linksubmit/push.js';        
    }
    else {
        bp.src = 'http://push.zhanzhang.baidu.com/push.js';
    }
    var s = document.getElementsByTagName("script")[0];
    s.parentNode.insertBefore(bp, s);
})();

(function() {
	document.write(unescape('%3Cdiv id="bdcs"%3E%3C/div%3E'));
	var bdcs = document.createElement('script');
	bdcs.type = 'text/javascript';
	bdcs.async = true;
	bdcs.src = 'http://znsv.baidu.com/customer_search/api/js?sid=13482344310341259490'
			+ '&plate_url='
			+ encodeURIComponent(window.location.href)
			+ '&t='
			+ Math.ceil(new Date() / 3600000);
	var s = document.getElementsByTagName('script')[0];
	s.parentNode.insertBefore(bdcs, s);
})();

function checkData() {
	var q = $.trim($("#q").val());
	if (q.replace(/\s+/g,'') == '') {
		alert("请输入您要查询的关键字！");
		return false;
	}
	return true;
}

function search() {
	if (checkData()) {
		$.post("search.shtml", {
			"q" : $("#q").val()
		}, function(result) {
			document.body.innerHTML = result
		})
	}
}

//加载余下的博客类别
function showOtherType() {
	$('.otherType').show();
	$('#showOtherType').hide();
}

//提交评论
function submitComment() {
	var userName = $("#userName").val();
	var email = $("#email").val();
	var website = $("#website").val();
	var content = $("#content").val();
	var vCode = $("#vCode").val();
	if (userName.trim() == '') {
		alert("请输入昵称！");
		return;
	}
	/* if (!/^(\w)+(\.\w+)*@(\w)+((\.\w+)+)$/.test(email)) {
		alert("请输入格式正确的邮箱！");
		return;
	} */
	if (content.trim() == '') {
		alert("请输入评论内容！");
		return;
	}
	if (vCode.trim() == '') {
		alert("请填写验证码！");
		return;
	}
	var params = {
		"content" : content,
		"vCode" : vCode,
		"userName" : userName,
		"email" : email,
		"website" : website,
		"blogId" : $("#blogId").val()
	};
	$.post("comment/save.shtml", params, function(result) {
		if (result.success) {
			alert("评论提交成功，审核通过后显示！");
			$("#userName").val('');
			$("#email").val('');
			$("#website").val('');
			$("#content").val('');
			$("#vCode").val('');
			//window.location.reload();
		} else {
			$(".errorInfo").html(result.errorInfo);
		}
	}, "json");
}

//加载余下的评论
function showOtherComment() {
	$('.otherComment').show();
	$('#showOtherComment').hide();
}

//显示评论框
function showCommentForm() {
	$('.commentForm').show();
	$('#showCommentForm').hide();
}