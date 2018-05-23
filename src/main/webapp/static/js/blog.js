/*var _hmt = _hmt || [];
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
})();*/

window._bd_share_config = {
		"common" : {
			"bdSnsKey" : {},
			"bdText" : "",
			"bdMini" : "2",
			"bdMiniList" : false,
			"bdPic" : "",
			"bdStyle" : "0",
			"bdSize" : "16"
		},
		"share" : {},
		"image" : {
			"viewList" : [ "qzone", "tsina", "tqq", "renren", "weixin" ],
			"viewText" : "分享到：",
			"viewSize" : "16"
		},
		"selectShare" : {
			"bdContainerClass" : null,
			"bdSelectMiniList" : [ "qzone", "tsina", "tqq", "renren", "weixin" ]
		}
	};
	with (document)
		0[(getElementsByTagName('head')[0] || body)
				.appendChild(createElement('script')).src = 'static/api/js/share.js?v=89860593.js?cdnversion='
				+ ~(-new Date() / 36e5)];
//回顶部
$(function() {
	$(window).scroll(function() {
		var scroll_top = $(document).scrollTop();
		if (scroll_top >= 100) {
			$(".return_top").fadeIn();
		} else {
			$(".return_top").fadeOut();
		}
	});
	$(".return_top").click(function() {
		$("html,body").stop(true).animate({
			scrollTop : "0"
		}, 100);
	});
});

/**
 * 重写alert
 */
window.alert = function(msg, options){
	var _default = {icon: 0};
	$.extend(_default, options);
	layer.msg(msg, _default);
}

function checkData() {
	var q = $.trim($("#q").val());
	if (q.replace(/\s+/g,'') == '') {
		alert("请输入您要查询的关键字");
		return false;
	} 
	return true;
}

function search() {
	if (checkData()) {
		$.post("search.shtml", {
			"q" : encodeURIComponent($("#q").val().substr(0,20))
		}, function(result) {
			$(body).html(result);
		})
	}
}

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
			"q" : encodeURIComponent($("#q").val())
		}, function(result) {
			$(body).html(result);
		})
	}
}

//代码自动换行
$(function(){        
    $("table.syntaxhighlighter").each(function () {
        if (!$(this).hasClass("nogutter")) {
            var $gutter = $($(this).find(".gutter")[0]);
            var $codeLines = $($(this).find(".code .line"));
            $gutter.find(".line").each(function (i) {
                $(this).height($($codeLines[i]).height());
                $($codeLines[i]).height($($codeLines[i]).height());
            });
        }
    });
});

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
	if (userName.replace(/\s+/g,"") == '') {
		alert("请输入昵称");
		return;
	}
	if (!/^(\w)+(\.\w+)*@(\w)+((\.\w+)+)$/.test(email)) {
		alert("请输入格式正确的邮箱");
		return;
	} 
	if(website.replace(/\s+/g,"") != "" && !/^(http|https):\/\/[\w\-_]+(\.[\w\-_]+)+([\w\-\.,@?^=%&amp;:/~\+#]*[\w\-\@?^=%&amp;/~\+#])?/.test(website)){
		alert("请输入格式正确的网址");
		return;
	}
	if (content.replace(/\s+/g,"") == '') {
		alert("请输入评论内容");
		return;
	}
	if (vCode.replace(/\s+/g,"") == '') {
		alert("请填写验证码");
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
			$(".errorInfo").html("");
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