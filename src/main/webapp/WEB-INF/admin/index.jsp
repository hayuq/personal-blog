<%@ page language="java" isELIgnored="false" trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<title>博客后台管理系统</title>
<!-- Tell the browser to be responsive to screen width -->
<meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/WEB-INF/admin/head.jsp" %>
</head>
<!-- 后台页面主框架 -->
<frameset rows="50,*" cols="*" frameborder="no" border="0" framespacing="0">
	<frame src="${ctx}/admin/top.do" name="topFrame" scrolling="no" noresize="noresize" id="topFrame" title="topFrame" />
	<frameset cols="187,*" frameborder="no" border="0" framespacing="0">
		<frame src="${ctx}/admin/left.do" name="leftFrame" scrolling="no" noresize="noresize" id="leftFrame" title="leftFrame" />
		<frame src="${ctx}/blog/list.do" name="rightFrame" id="rightFrame" title="rightFrame" />
	</frameset>
</frameset>

<%-- <body>
	<div class="wrapper">
		<div class="topleft">
	    	<span style="font-size:16px;padding:10px">博客系统后台管理</span>
	    </div>
        
		<div class="topright"> 
		    <ul>
			    <!-- <li><span><img src="static/images/admin/help.png" title="帮助"  class="helpimg"/></span><a href="#">帮助</a></li> -->
			    <li><a href="javascript:void(0)" onclick="window.parent.location.href='index.shtml'">回首页</a></li>
			    <li><a href="javascript:void(0)" onclick="logout()">退出</a></li>
			    <li>  欢迎您：<strong>${currentUser.userName }</strong> </li>
		    </ul>
		</div>
		<header class="main-header">
			<span style="font-size:16px;padding:10px">博客系统后台管理</span>
			<!-- Header Navbar: style can be found in header.less -->
			<nav class="navbar navbar-static-top" role="navigation">
				<!-- Sidebar toggle button-->
				<a href="#" class="sidebar-toggle" data-toggle="offcanvas"
					role="button"> <span class="sr-only">Toggle navigation</span>
				</a>
				<div style="float: left; color: #fff; padding: 15px 10px;">
					<span><i class="fa fa-user"></i>&nbsp;${currentUser.userName}</span>
				</div>
				<div class="navbar-custom-menu">
					<ul class="nav navbar-nav">
						<li><a class="pointer" onclick="updatePassword()"><i class="fa fa-lock"></i> &nbsp;修改密码</a></li>
						<li><a class="pointer" onclick="logout()"><i class="fa fa-sign-out"></i>&nbsp;退出系统</a></li>
					</ul>
				</div>
			</nav>
		</header>

		<!-- password layer -->
		<div id="password-layer" style="display: none;">
			<form class="form-horizontal">
				<div class="form-group">
					<div class="form-group">
						<div class="col-sm-2 control-label">账号</div>
						<span class="label label-success" style="vertical-align: bottom;">
							${currentUser.userName}
						</span>
					</div>
					<div class="form-group">
						<div class="col-sm-2 control-label">原密码</div>
						<div class="col-sm-10">
							<input type="password" class="form-control" name="oldpwd" id="oldpwd" placeholder="原密码">
						</div>
					</div>
					<div class="form-group">
						<div class="col-sm-2 control-label">新密码</div>
						<div class="col-sm-10">
							<input type="text" class="form-control" name="newpwd" id="newpwd" placeholder="新密码">
						</div>
					</div>
					<div class="form-group">
						<div class="col-sm-2 control-label">确认新密码</div>
						<div class="col-sm-10">
							<input type="text" class="form-control" name="repwd" id="repwd" placeholder="确认新密码">
						</div>
					</div>
				</div>
			</form>
		</div>
		<!-- ./password-layer -->

		<!-- blogType layer -->
		<div id="blogType-layer" style="display: none;">
			<form class="form-horizontal">
				<div class="form-group">
					<div class="form-group">
						<div class="col-sm-2 control-label">类别名称<input type="hidden" name="typeId" id="typeId" value="${blogType.typeId }"/></div>
						<div class="col-sm-10">
							<input type="text" class="form-control" name="typeName" id="typeName" placeholder="类别名称">
						</div>
					</div>
					<div class="form-group">
						<div class="col-sm-2 control-label">显示顺序</div>
						<div class="col-sm-10">
							<input type="text" class="form-control" value="${blogType.orderNo }" name="orderNo" id="orderNo" placeholder="显示顺序">
						</div>
					</div>
				</div>
			</form>
		</div>
		<!-- ./blogType-layer -->

		<!-- link layer -->
		<div id="link-layer" style="display: none;">
			<form class="form-horizontal">
				<div class="form-group">
					<div class="form-group">
						<div class="col-sm-2 control-label">链接名称</div>
						<span class="label label-success" style="vertical-align: bottom;">
							<input class="form-control" name="linkName" id="linkName" placeholder="链接名称">
						</span>
					</div>
					<div class="form-group">
						<div class="col-sm-2 control-label">链接地址</div>
						<div class="col-sm-10">
							<input type="password" class="form-control" name="linkUrl" id="linkUrl" placeholder="链接地址">
						</div>
					</div>
					<div class="form-group">
						<div class="col-sm-2 control-label">显示顺序</div>
						<div class="col-sm-10">
							<input type="text" class="form-control" name="orderNo" id="orderNo" placeholder="显示顺序">
						</div>
					</div>
				</div>
			</form>
		</div>
		<!-- ./link-layer -->
	</div>

	<script type="text/javascript" src="static/js/jquery.min.js"></script>
	<script type="text/javascript" src="static/layer/layer.js"></script>
	<script type="text/javascript" src="static/js/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="static/js/select-ui.min.js"></script>
	<script type="text/javascript" charset="utf-8" src="static/js/admin.js"></script>
	<script type="text/javascript" charset="utf-8" src="static/js/batch_delete.js"></script>
	<script type="text/javascript" charset="utf-8" src="static/ueditor/ueditor.config.js"></script>
	<script type="text/javascript" charset="utf-8" src="static/ueditor/ueditor.all.min.js"> </script>
	<script type="text/javascript" charset="utf-8" src="static/ueditor/lang/zh-cn/zh-cn.js"></script>
</body> --%>
</html>