<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false"
	contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<base href="<%=basePath%>"/>
<link href="favicon.ico" rel="SHORTCUT ICON">
<link rel="stylesheet" href="static/bootstrap3/css/bootstrap.min.css">
<link rel="stylesheet" href="static/bootstrap3/css/bootstrap-theme.min.css">
<link rel="stylesheet" href="static/css/blog.css">
<link rel="stylesheet" href="static/jquery-easyui-1.3.3/themes/default/easyui.css">

<script type="text/javascript" src="static/js/jquery.min.js"></script>
<script type="text/javascript" src="static/js/blog.js"></script>
<script type="text/javascript" src="static/bootstrap3/js/bootstrap.min.js"></script>
<script type="text/javascript" src="static/jquery-easyui-1.3.3/jquery.easyui.min.js"></script>

<!-- ueditor代码高亮 -->
<link href="static/ueditor/third-party/SyntaxHighlighter/shCoreDefault.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="static/ueditor/third-party/SyntaxHighlighter/shCore.js"></script>
