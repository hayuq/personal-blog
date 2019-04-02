<%@ page language="java" pageEncoding="utf-8" isELIgnored="false"
	contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"%>
<!DOCTYPE html>
<html>
<head>
	<title>${pageTitle }</title>
	<%@include file="/WEB-INF/front/head.jspf" %>
</head>
<body>
	<jsp:include page="/WEB-INF/front/common/top.jsp" />
	<main class="container">
		<div class="row">
			<div class="col-md-9">
				<div class="data_list">
					<div class="data_list_title">
						<i class="glyphicon glyphicon-th-list"></i> 
						${title }
					</div>
					<div class="datas">
						<c:choose>
						<c:when test="${empty blogList }">
						<div align="center" style="padding-top: 20px">没有那么多页</div>
						</c:when>
						<c:otherwise>
						<ul>
							<c:forEach var="blog" items="${blogList}">
							<li class="list">
							    <span class="title">
							      <a title="${blog.title }" href="${ctx}/blog/articles/${blog.id}.shtml">${blog.title }</a>
							    </span>
								<div class="img">
									<a href="${ctx}/blog/articles/${blog.id}.shtml" title="${blog.title }"> 
									   <img src="${ctx}/images/cover/${blog.image }" alt="${blog.title }">
									</a>
								</div>
								<div class="content">
									<span class="type">
									  <i class="glyphicon glyphicon-tag"></i>
									  <a href="${ctx}/blog.shtml?cat=${blog.blogType.typeId }"	style="color: #990">${blog.blogType.typeName }</a>
									</span> 
									<span class="time">
									  <i class="glyphicon glyphicon-time"></i>
									  <fmt:formatDate value="${blog.releaseDate }" pattern="yyyy-MM-dd HH:mm" />
									</span>
									<p class="summary">摘要：${blog.summary }</p>
								</div>
								<div class="info">
									<span class="reading">
									 <i class="glyphicon glyphicon-eye-open"></i>
									   浏览(<a href="${ctx}/blog/articles/${blog.id}.shtml">${blog.reading}</a>)
									</span>
									<span class="review">
									 <i class="glyphicon glyphicon-comment"></i>
									   评论(<a href="${ctx}/blog/articles/${blog.id}.shtml#comment">${blog.commentsCount}</a>)
									</span> 
									<span class="readfull">
									  <a href="javascript:void(0);" onclick="window.location='${ctx}/blog/articles/${blog.id}.shtml'">阅读全文>></a>
									</span>
								</div>
							</li>
							</c:forEach>
						</ul>
						</c:otherwise>
						</c:choose>
					</div>
				</div>
				<c:if test="${not empty blogList && totalCount > 8}">
				<ul class="pagination">${pageCode }</ul>
				</c:if>
			</div>
			<jsp:include page="/WEB-INF/front/common/right.jsp" />
		</div>
	</main>
	<jsp:include page="/WEB-INF/front/common/foot.jsp"/>
</body>
</html>
