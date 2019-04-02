<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" trimDirectiveWhitespaces="true"%>
<!DOCTYPE html>
<html>
<head>
	<title>搜索'${q }'的结果 - ${blogger.nickName }的博客</title>
	<%@include file="/WEB-INF/front/head.jspf" %>
</head>
<body>
	<jsp:include page="/WEB-INF/front/common/top.jsp" />
	<main class="container">
		<div class="row">
			<div class="col-md-9">
				<div class="data_list">
					<div class="data_list_title">
						<img src="${ctx}/static/images/search_icon.png" /> 搜索&nbsp;<font
							color="red">${q }</font>&nbsp;的结果
						&nbsp;(搜索到&nbsp;<font color="red">${resultCount}</font>&nbsp;篇相关文章)
					</div>
					<div class="datas search">
						<c:choose>
						<c:when test="${empty blogList }">
						<div align="center" style="padding-top: 20px">未查询到结果，请换个关键字试试看！</div>
						</c:when>
						<c:otherwise>
						<ul>
							<c:forEach var="blog" items="${blogList }">
							<li class="list">
								<span class="title">
									<a href="${ctx}/blog/articles/${blog.id}.shtml" style="text-decoration: none" target="_blank">${blog.title }</a>
								</span>
								<div>
									<p>
										<span>
											<%-- 分类：<a href="blog.shtml?cat=${blog.blogType.typeId }" style="color: #990">${blog.blogType.typeName }</a> --%>
										  	<font color="#929292">发布时间：</font>${blog.releaseDateStr}&nbsp;&nbsp;&nbsp;<font color="#929292">浏览</font> ${blog.reading } <font color="#929292"> 次</font>
										</span> 
									</p>
									<p>${blog.summary }</p>
									<span class="link">
										<a href="${ctx}/blog/articles/${blog.id}.shtml" target="_blank">${basePath }blog/articles/${blog.id}.shtml</a>
									</span>
								</div>
							</li>
							</c:forEach>
						</ul>
						</c:otherwise>
						</c:choose>
					</div>
				</div>
				<c:if test="${not empty blogList && resultCount > 10}">
				<ul class="pagination">${pageCode }</ul>
				</c:if>
			</div>
			<jsp:include page="/WEB-INF/front/common/right.jsp" />
		</div>
	</main>
	<jsp:include page="/WEB-INF/front/common/foot.jsp"/>
</body>
</html>
