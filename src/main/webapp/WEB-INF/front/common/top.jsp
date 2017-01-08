<%@page import="com.xjc.util.Constants"%>
<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false"
	contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"%>
<meta name="keywords" content="个人博客,学习笔记，生活随记">
<meta name="description" content="Promising的博客，一位程序员的个人博客，用来记录学习和生活中的大小事。包括Java，.NET，Android，以及前端知识等。">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=yes" />
<%@include file="/WEB-INF/front/head.jsp" %>
<div class="row banner">
	<div class="col-md-4" style="float: left">
		<h3>${blogger.nickName }的博客</h3>
		<p>${blogger.signature }</p>
	</div>
	<div class="col-md-8" style="padding-top: 20px; float: right;">
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
					<button type="button" class="navbar-toggle collapsed"
						data-toggle="collapse" data-target="#bs-example-navbar-collapse-1"
						aria-expanded="false">
						<span class="sr-only">Toggle navigation</span> <span
							class="icon-bar"></span> <span class="icon-bar"></span> <span
							class="icon-bar"></span>
					</button>
					<a class="navbar-brand" href="/"><font color="#000">首页</font></a>
				</div>

				<div class="collapse navbar-collapse"
					id="bs-example-navbar-collapse-1">
					<ul class="nav navbar-nav navbar" style="font-size: 16px;">
						<li><a href="blogger.shtml"><font color="#000">关于我</font></a></li>
						<li><a href="life.shtml"><font color="#000"><%=Constants.LIFE_NAME %></font></a></li>
						<li><a href="admin/login.do" target="_blank"><font color="#000">管理博客</font></a></li>
						<%-- <li><a href="blog/about.shtml"><font color="#000">关于博客</font></a></li> --%>
					</ul>
					<%-- <form action="search.shtml" class="navbar-form navbar-right" role="search" method="post" onsubmit="return checkData()">
				        <div class="form-group" >
				          <input type="text" id="q" name="q" class="form-control" placeholder="请输入关键字搜索...">
				        </div>
				        <input type="submit" class="btn btn-default" value="搜索"/>
				    </form> --%>
				</div>
			</div>
		</nav>
	</div>
</div>