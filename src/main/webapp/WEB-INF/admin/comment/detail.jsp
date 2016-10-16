<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>评论详情</title>
<%@include file="/WEB-INF/admin/head.jsp" %>
</head>
<body>
	<div class="place">
		<span>位置：</span>
		<ul class="placeul">
			<li><a href="javascript:void(0)">首页</a></li>
			<li><a href="comment/list.do">评论管理</a></li>
			<li>评论详情</li>
		</ul>
	</div>
	
	<div class="rightinfo">
			<table class="table">
				<tr>
					<td style="width:50px">评论人<input type="hidden" id="id" value="${comment.id }"/></td>
					<td>${comment.userName }</td>
				</tr>
				<tr>
					<td style="width:50px">评论时间 </td>
					<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${comment.commentDate }" /></td>
				</tr>
				<tr>
					<td style="width:50px">评论文章 </td>
					<td>${comment.blog.title }</td>
				</tr>
				<tr>
					<td style="width:50px">是否通过</td>
					<td>
						<select class="select" id="isPass" name="isPass">
							<option value="1" <c:if test="${comment.isPass == true }">selected="selected"</c:if>>通过</option>
							<option value="0" <c:if test="${comment.isPass == false }">selected="selected"</c:if>>不通过</option>
						</select>
					</td>
				</tr>
				<tr>
					<td style="width:50px">评论内容</td>
					<td colspan="2">
						<textarea id="content" rows="8" cols="80" class="sctextarea" disabled="disabled">${comment.content }</textarea>
					</td>
				</tr>
				<tr>
					<td style="width:50px">回复</td>
					<td colspan="2">
						<textarea id="review" rows="8" cols="80" class="sctextarea">${comment.reply }</textarea>
					</td>
				</tr>
			</table>
			<input type="button" class="scbtn" value="提交" onclick="reply()"/>
			<input type="button" class="scbtn" value="返回" onclick="history.back()" />
	</div>
<script type="text/javascript">
	function reply(){
		var id = $("#id").val();
		var isPass = $("#isPass").val();
		var reply = $("#review").val();
		$.post("comment/review.do",{"id":id,"isPass":isPass,"reply":reply},function(result){
			if(result.success)
				alert("提交成功");
			else
				alert("提交失败");
		});
	}
</script>
</body>
</html>