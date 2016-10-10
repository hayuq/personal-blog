<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>修改密码</title>
<%@include file="/WEB-INF/admin/head.jsp" %>
</head>
<body>
	<div class="place">
		<span>位置：</span>
		<ul class="placeul">
			<li><a href="javascript:void(0)">首页</a></li>
			<li><a href="javascript:void(0)">个人管理</a></li>
			<li>修改密码</li>
		</ul>
	</div>
	
	<div class="rightinfo">
			<table class="table">
				<tr>
					<td style="width:50px">原密码<input type="hidden" id="id" value="${blogger.id }"/></td>
					<td>${blogger.password }</td>
				</tr>
				<tr>
					<td style="width:50px">新密码</td>
					<td><input type="text" id="pwd" class="scinput"/></td>
				</tr>
			</table>
			<input type="button" class="scbtn" value="保存" onclick="savePwd()"/>
	</div>
<script type="text/javascript">
	function savePwd(){
		$.post("blogger/modifyPassword.do",{"id":$("#id").val(),"pwd":$("#pwd").val()},function(result){
			if(result.success)
				alert("修改成功");
			else
				alert("修改失败");
		});
	}
</script>
</body>
</html>