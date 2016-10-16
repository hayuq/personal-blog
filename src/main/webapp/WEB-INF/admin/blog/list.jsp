<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%@include file="/WEB-INF/admin/head.jsp" %>
</head>
<body>
	<div class="place">
		<span>位置：</span>
		<ul class="placeul">
			<li><a href="javascript:void(0)">首页</a></li>
			<li><a href="blog/list.do">博客管理</a></li>
			<li>博客列表</li>
		</ul>
	</div>

	<div class="rightinfo">
		<div class="tools">
			<form action="blog/list.do" method="post">
				<ul class="seachform">
					<li><label>标题</label><input name="title" type="text" class="scinput" value="${entry.id }"/></li>
					<li><label>发布时间</label><input name="firstDate" type="text" class="scinput" value="${entry.firstDate }" onfocus="WdatePicker()"/></li>
					<li><label>至</label><input name="secondDate" type="text" value="${entry.secondDate }" class="scinput" onfocus="WdatePicker()"/></li>
					<li><label>所属类别</label>
						<div class="vocation">
							<select class="select" name="typeId">
								<option value="">全部</option>
								<c:forEach var="blogType" items="${blogTypeList }">
									<option value="${blogType.typeId }" <c:if test="${entry.typeId == blogType.typeId }">selected="selected"</c:if>>${blogType.typeName }</option>
								</c:forEach>
							</select>
						</div></li>
					<li><input name="" type="submit" class="scbtn" value="查询" /></li>
				</ul>
			</form>
		</div>
		<table class="tablelist">
			<thead>
				<tr>
					<th><input type="checkbox" onclick="selectAll('chk',this)" /></th>
					<th>序号<i class="sort"><img src="static/images/admin/px.gif" /></i></th>
					<th>标题</th>
					<th>所属类别</th>
					<th>发布时间</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<c:if test="${not empty blogList }">
					<c:forEach var="blog" items="${blogList}" varStatus="index">
						<tr>
							<td><input name="chk" type="checkbox" value="${blog.id }" /></td>
							<td>${index.count }</td>
							<td><a target="_blank" class="tablelink" href="blog/article/${blog.id }.shtml">${blog.title }</a></td>
							<td><a target="_blank" class="tablelink" href="index.shtml?type=${blog.blogType.typeId }">${blog.blogType.typeName }</a></td>
							<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${blog.releaseDate }" /></td>
							<td>
								<a href="blog/toUpdate.do?id=${blog.id }" target="_self" class="tablelink"><img class="detail" src="static/images/admin/ico06.png" />修改</a> 
								<a href="javascript:void(0)" class="tablelink" onclick="if(confirm('确定删除该条数据吗？')) window.location.href='blog/delete.do?id=${blog.id }'"> <img src="static/images/admin/t03.png" />删除</a>
							</td>
						</tr>
					</c:forEach>
				</c:if>
			</tbody>
		</table>
		
		<div class="pagin">
			<div class="message">
				 <input name="" type="button" class="scbtn" value="删除所选" onclick="deleteSelected('chk','blog/deletes.do')" />
				共<i class="blue">&nbsp;${pagination.totalCount }&nbsp;</i>条记录，每页&nbsp;<i
					class="blue">${pagination.pageSize }</i>&nbsp;条，当前显示第&nbsp;<i
					class="blue">${pagination.currentPage }&nbsp;/&nbsp;${pagination.totalPage }&nbsp;</i>页
			</div>
			<ul class="paginList"> ${pageCode } </ul>${msg }
		</div>
	</div>
</body>
</html>