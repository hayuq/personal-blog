<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" trimDirectiveWhitespaces="true"%>
<!DOCTYPE html>
<html>
<head>
	<title>该页面不存在</title>
	<meta charset="UTF-8">
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <link rel="stylesheet" href="${ctx}/static/css/error.css"/>
</head>
<body class="body-bg">
	<div class="main">
	    <p class="title">
			<span>对不起，您要访问的页面不存在!</span>
			请检查您访问的路径是否正确。
		</p>
	    <a href="${ctx}/" class="btn">返回首页</a>
	</div>
</body>
</html>