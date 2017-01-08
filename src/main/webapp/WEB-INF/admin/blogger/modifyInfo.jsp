<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改个人信息</title>
<%@include file="/WEB-INF/admin/head.jsp" %>
</head>
<body onload="showMsg()">
	<div class="place">
		<span>位置：</span>
		<ul class="placeul">
			<li><a href="javascript:void(0)">首页</a></li>
			<li><a href="javascript:void(0)">个人信息管理</a></li>
			<li>修改个人信息</li>
		</ul>
	</div>
	
	<div class="rightinfo">
		<form action="blogger/modifyInfo.do" method="post" onsubmit="return checkInfo()" enctype="multipart/form-data">
			<table class="table">
				<tr>
					<td style="width:50px">昵称<input type="hidden" id="id" name="id" value="${blogger.id}"/><input type="hidden" id="msg" value="${msg}"/></td>
					<td><input id="nickName" name="nickName" type="text" class="scinput" value="${blogger.nickName }"/></td>
				</tr>
				<tr>
					<td style="width:50px">头像</td>
					<td><input type="file" id="img" name="img" onchange="previewImg(this)"/>
					<img id="preview" src="images/avatar/${blogger.imageUrl }" alt="图片" width="100px" height="100px"/></td>
				</tr>
				<tr>
					<td style="width:50px">签名</td>
					<td>
						<input type="text" class="scinput" name="signature" id="sign" value="${blogger.signature }" style="width:400px"/>
					</td>
				</tr>
				<tr>
					<td style="width:50px">个人简介</td>
					<td>
						<textarea id="editor" name="profile" id="profile">${blogger.profile }</textarea>
						<script type="text/javascript" src="static/js/ueditor.js"></script>
					</td>
				</tr>
			</table>
			<input type="submit" class="scbtn" value="保存"/>
			<input type="button" class="scbtn" value="返回" onclick="history.back()" />
		</form>
	</div>
</body>
</html>