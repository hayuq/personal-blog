<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>链接管理</title>
<%@include file="/WEB-INF/admin/head.jsp" %>
</head>
<body onload="showMsg()">
	<div class="place">
		<span>位置：</span>
		<ul class="placeul">
			<li><a href="javascript:void(0)">首页</a></li>
			<li><a href="link/list.do">链接管理</a></li>
			<li>链接列表</li>
		</ul>
	</div>

	<div class="rightinfo">
		<table class="tablelist">
			<thead>
				<tr>
					<th><input type="checkbox" onclick="selectAll('chk',this)" /><input type="hidden" id="msg"  value="${msg }"/></th>
					<th>序号<i class="sort"><img src="static/images/admin/px.gif" /></i></th>
					<th>链接名称</th>
					<th>链接地址</th>
					<th>显示顺序</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<c:if test="${not empty linkList }">
					<c:forEach var="link" items="${linkList}" varStatus="index">
						<tr>
							<td><input name="chk" type="checkbox" value="${link.id }" /></td>
							<td>${index.count }</td>
							<td><a target="_blank" class="tablelink" href="http://${link.linkUrl }">${link.linkName }</a></td>
							<td>${link.linkUrl }</td>
							<td>${link.orderNo }</td>
							<td>
								<a href="javascript:void(0)" class="tablelink" onclick="if(confirm('确定删除该条数据吗？')) window.location.href='link/delete.do?id=${link.id }'"> <img src="static/images/admin/t03.png" />删除</a>
							</td>
						</tr>
					</c:forEach>
				</c:if>
			</tbody>
		</table>
		
		<input name="" type="button" class="scbtn" value="删除所选" onclick="deleteSelected('chk','link/deletes.do')" />
		<%-- <div class="pagin">
			<div class="message">
				共<i class="blue">&nbsp;${pagination.totalCount }&nbsp;</i>条记录，每页&nbsp;<i
					class="blue">${pagination.pageSize }</i>&nbsp;条，当前显示第&nbsp;<i
					class="blue">${pagination.currentPage }&nbsp;/&nbsp;${pagination.totalPage }&nbsp;</i>页
			</div>
			<ul class="paginList"> ${pageCode } </ul>
		</div> --%>
	</div>
</body>
</html>