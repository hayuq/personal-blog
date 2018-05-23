<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>写博客</title>
<%@include file="/WEB-INF/admin/head.jsp" %>
</head>
<body onload="showMsg()">
	<div class="place">
		<span>位置：</span>
		<ul class="placeul">
			<li><a href="javascript:void(0)">首页</a></li>
			<li><a href="blog/list.do">博客管理</a></li>
			<li>写博客</li>
		</ul>
	</div>
	<div class="rightinfo">
		<form action="blog/add.do" method="post" onsubmit="return checkBlog()" enctype="multipart/form-data">
			<table class="table">
				<tr>
					<td style="width:50px">博客标题</td>
					<td><input id="title" name="title" type="text" class="scinput" style="width:300px"/></td>
				</tr>
				<tr>
					<td style="width:50px">所属类别<input type="hidden" name="typeName" id="typeName"/></td>
					<td>
						<select class="select" id="type" name="typeId">
							<option value='0'>--请选择--</option>
								<c:forEach var="blogType" items="${blogTypeList }">
									<option value="${blogType.typeId }">${blogType.typeName }</option>
								</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td style="width:50px">显示图片</td>
					<td>
					    <input type="file" id="img" name="img" onchange="previewImg(this)" accept="image/*"/>
						<img id="preview" width="100px" height="100px"/>
					</td>
				</tr>
				<tr>
					<td style="width:50px">博客内容<input type="hidden" name="summary" id="summary"/></td>
					<td>
						<textarea id="editor" name="content"></textarea>
						<script type="text/javascript" src="static/js/ueditor.js"></script>
					</td>
				</tr>
				<tr>
		   			<td style="width:50px">关键字：</td>
		   			<td><input type="text" id="keyword" name="keyword" class="scinput" style="width:300px"/>&nbsp;(多个关键字中间用空格隔开)</td>
		   		</tr>
			</table>
			<input name="" type="submit" class="scbtn" value="发布博客"/>
			<input name="" type="button" class="scbtn" value="返回" onclick="history.back()" />
			<input type="hidden" id="msg" value="${msg }"/>
		</form>
	</div>
</body>
</html>