<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<!DOCTYPE html>
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
			<li><a href="blogType/list.do">博客类别管理</a></li>
			<li>类别列表</li>
		</ul>
	</div>

	<div class="rightinfo">
		<table class="tablelist">
			<thead>
				<tr>
					<!-- <th><input type="checkbox" onclick="selectAll('chk',this)" /></th> -->
					<th>序号</th>
					<th>类别名称</th>
					<th>排序</th>
					<th>文章数</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<c:if test="${not empty blogTypeList }">
					<c:forEach var="blogType" items="${blogTypeList}" varStatus="index">
						<tr>
							<%-- <td><input name="chk" type="checkbox" value="${blogType.typeId }" /></td> --%>
							<td>${index.count }</td>
							<td><a class="tablelink" href="blog.shtml?cat=${blogType.typeId }" target="_blank">
								${blogType.typeName }</a></td>
							<td>${blogType.orderNo }</td>
							<td>${blogType.blogCount}</td>
							<td>
								<a href="blogType/toUpdate.do?id=${blogType.typeId }" target="_self" class="tablelink"><img class="detail" src="static/images/admin/ico06.png" />修改</a> 
								<a href="javascript:void(0)" class="tablelink" onclick="deleteBlogType('${blogType.typeId}')"> <img src="static/images/admin/t03.png" />删除</a>
							</td>
						</tr>
					</c:forEach>
				</c:if>
			</tbody>
		</table>
		
		<div class="pagin">
			<div class="message">
				 <!-- <input name="" type="button" class="scbtn" value="删除所选" onclick="deleteSelected('chk','blogType/deletes.do')" /> -->
				共<i class="blue">&nbsp;${pagination.totalCount }&nbsp;</i>条记录，每页&nbsp;<i
					class="blue">${pagination.pageSize }</i>&nbsp;条，当前显示第&nbsp;<i
					class="blue">${pagination.currentPage }&nbsp;/&nbsp;${pagination.totalPage }&nbsp;</i>页
			</div>
			<ul class="paginList"> ${pageCode } </ul>
		</div>
	</div>
	<script type="text/javascript">
	function deleteBlogType(id) {
		//查询该类别下是否存在文章
		$.post("blogType/search.do", {"id":id}, function(result) {
			if (result.count == 0) {
				confirm('确定删除该类别吗？', function() {
				    window.location.href = 'blogType/delete.do?id=' + id;
				});
			} else if (result.count > 0) {
				confirm('该类别下有' + result.count + '篇文章，删除将会把文章移出此分类，是否继续？', function() {
					window.location.href = 'blogType/batch_delete.do?id=' + id;
				});
			}
		},"json");
	}
	</script>
</body>
</html>