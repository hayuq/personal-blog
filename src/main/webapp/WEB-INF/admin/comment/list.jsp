<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>评论列表</title>
<%@include file="/WEB-INF/admin/head.jsp" %>
</head>
<body>
	<div class="place">
		<span>位置：</span>
		<ul class="placeul">
			<li><a href="javascript:void(0)">首页</a></li>
			<li><a href="comment/list.do">评论管理</a></li>
			<li>评论列表</li>
		</ul>
	</div>

	<div class="rightinfo">
		<div class="tools">
			<form action="comment/list.do" method="post">
				<ul class="seachform">
					<li><label>评论时间</label><input name="firstDate" type="text" class="scinput" value="${entry.firstDate }" onfocus="WdatePicker()"/></li>
					<li><label>至</label><input name="secondDate" type="text" value="${entry.secondDate }" class="scinput" onfocus="WdatePicker()"/></li>
					<li><label>评论人</label><input name="userName" type="text" class="scinput" value="${entry.userName }"/></li>
					<li><label>是否通过</label>
						<div class="vocation">
							<select class="select" name="isPass">
								<option value="">全部</option>
								<option value="1" <c:if test="${entry.isPass == true }">selected="selected"</c:if>>审核通过</option>
								<option value="0" <c:if test="${entry.isPass == false }">selected="selected"</c:if>>待审核</option>
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
					<th>序号</th>
					<th>评论文章</th>
					<th>评论内容</th>
					<th>评论时间</th>
					<th>评论人</th>
					<th>审核状态</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<c:if test="${not empty commentList }">
					<c:forEach var="comment" items="${commentList}" varStatus="index">
						<tr>
							<td><input name="chk" type="checkbox" value="${comment.id }" /></td>
							<td>${index.count }</td>
							<td class="ellipsis" title="${comment.blog.title }">
							    <a class="tablelink" target="_blank" href="blog/articles/${comment.blog.id }.shtml">
							        ${comment.blog.title }
							    </a>
							</td>
							<td>${fn:substring(comment.content, 0, 30) }</td>
							<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${comment.commentDate }" /></td>
							<td>${comment.userName }</td>
							<td>
								<c:if test="${comment.isPass == true}">审核通过</c:if>
								<c:if test="${comment.isPass == false}">待审核</c:if>
							</td>
							<td class="op">
								<a href="javascript:void(0)" onclick="window.location.href='comment/detail.do?id=${comment.id }'" target="_self" class="tablelink"><img class="detail" src="static/images/admin/ico06.png" />详情</a> 
								<a href="javascript:void(0)" class="tablelink" onclick="confirm('确定删除该条数据吗？', function() {window.location.href='comment/delete.do?id=${comment.id }';})"> <img src="static/images/admin/t03.png" />删除</a>
							</td>
						</tr>
					</c:forEach>
				</c:if>
			</tbody>
		</table>
		
		<div class="pagin">
			<div class="message">
				 <input name="" type="button" class="scbtn" value="删除所选" onclick="deleteSelected('chk','comment/deletes.do')" />
				共<i class="blue">&nbsp;${pagination.totalCount }&nbsp;</i>条记录，
				每页&nbsp;<i class="blue">${pagination.pageSize }</i>&nbsp;条，
				当前显示第&nbsp;<i class="blue">${pagination.currentPage }&nbsp;/&nbsp;${pagination.totalPage }&nbsp;</i>页
			</div>
			<ul class="paginList"> ${pageCode } </ul>
		</div>
	</div>
</body>
</html>