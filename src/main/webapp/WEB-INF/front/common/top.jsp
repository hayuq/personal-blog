<%@page import="com.june.util.Constants"%>
<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false"
	contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"%>
	<div class="row banner">
		<div class="col-md-4" style="float: left">
			<h3>${blogger.nickName }的博客</h3>
			<p>${blogger.signature }</p>
		</div>
		<!-- <div class="col-md-8" style="padding-top: 20px; float: right;">
			<iframe style="float: right;" width="300" scrolling="no" height="60"
				frameborder="0" allowtransparency="true"
				src="http://i.tianqi.com/index.php?c=code&id=12&icon=1&num=5"></iframe>
		</div> -->
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
						<a class="navbar-brand" href=""><font color="#000">首页</font></a>
					</div>
	
					<div class="collapse navbar-collapse"
						id="bs-example-navbar-collapse-1">
						<ul class="nav navbar-nav navbar" style="font-size: 16px;">
							<li><a href="about.shtml"><font color="#000">关于</font></a></li>
							<li><a href="<%=Constants.NEWS_CATEGORY_EN %>.shtml"><font color="#000"><%=Constants.NEWS_CATEGORY %></font></a></li>
							<li><a href="<%=Constants.LIFE_CATEGORY_EN %>.shtml"><font color="#000"><%=Constants.LIFE_CATEGORY %></font></a></li>
							<li><a href="admin/login.do" target="_blank"><font color="#000">管理博客</font></a></li>
						</ul>
					</div>
				</div>
			</nav>
		</div>
	</div>