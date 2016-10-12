<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<base href="<%=basePath%>"/>
<meta name="keywords" content="个人博客,学习笔记">
<meta name="description" content="Promising的博客，一位程序员的个人博客，用来记录学习和生活中的大小事。">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=yes" />
<link href="favicon.ico" rel="SHORTCUT ICON">
<link rel="stylesheet" href="static/bootstrap3/css/bootstrap.min.css">
<link rel="stylesheet" href="static/bootstrap3/css/bootstrap-theme.min.css">
<link rel="stylesheet" href="static/css/blog.css">
<script type="text/javascript" src="static/bootstrap3/js/jquery-1.11.2.min.js"></script>
<script type="text/javascript" src="static/bootstrap3/js/bootstrap.min.js"></script>

<!-- ueditor代码高亮 -->
<link href="static/ueditor/third-party/SyntaxHighlighter/shCoreDefault.css" rel="stylesheet" type="text/css" />  
<script type="text/javascript" src="static/ueditor/third-party/SyntaxHighlighter/shCore.js"></script>  
<script type="text/javascript">      
	SyntaxHighlighter.all();       
</script>
<script type="text/javascript">
	function checkData(){
		var q=document.getElementById("q").value.trim();
		if(q==null || q==""){
			alert("请输入您要查询的关键字！");
			return false;
		}else{
			return true;
		}
	}
	
	//回顶部
	  $(function(){
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
				}, 600);
			});
	  });
</script>
<div class="row banner">
	<div class="col-md-4" style="float:left">
		<h3>${blogger.nickName }的博客</h3>
		<p>${blogger.signature }</p>
	</div>
	<div class="col-md-8" style="padding-top:20px;float: right;">
		<iframe style="float: right;" width="300" scrolling="no" height="60"
			frameborder="0" allowtransparency="true"
			src="http://i.tianqi.com/index.php?c=code&id=12&icon=1&num=5"></iframe>
	</div>
</div>
<div class="row">
	<div class="col-md-12" style="padding-top: 10px">
		<nav class="navbar navbar-default">
		  <div class="container-fluid">
		    <!-- Brand and toggle get grouped for better mobile display -->
		    <div class="navbar-header">
		      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
		        <span class="sr-only">Toggle navigation</span>
		        <span class="icon-bar"></span>
		        <span class="icon-bar"></span>
		        <span class="icon-bar"></span>
		      </button>
		      <a class="navbar-brand" href="index.shtml"><font color="#000">首页</font></a>
		    </div>

		    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1" >
		      <ul class="nav navbar-nav navbar" style="font-size:16px;">
		        <li><a href="blogger/info.shtml"><font color="#000">关于我</font></a></li>
		        <li><a href="blogger/info.shtml"><font color="#000">生活点滴</font></a></li>
		        <li><a href="index.shtml"><font color="#000">程序相关</font></a></li>
		        <li><a href="index.shtml"><font color="#000">互联网络</font></a></li>
		        <li><a href="blogger/info.shtml"><font color="#000">留言板</font></a></li>
		      </ul>
		     <form action="blog/q.html" class="navbar-form navbar-right" role="search" method="post" onsubmit="return checkData()">
		        <div class="form-group" >
		          <input type="text" id="q" name="q" value="" class="form-control" placeholder="请输入关键字搜索...">
		        </div>
		        <button type="submit" class="btn btn-default">搜索</button>
		      </form>
		    </div>
		  </div>
		</nav>
	</div>
</div>