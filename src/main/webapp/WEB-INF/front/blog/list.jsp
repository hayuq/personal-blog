<%@ page language="java" pageEncoding="utf-8" isELIgnored="false"
	contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"%>
<!DOCTYPE html>
<html>
<head>
<title>${pageTitle }</title>
<%@include file="/WEB-INF/front/head.jspf" %>
</head>
<body>
<div class="container">
	<jsp:include page="/WEB-INF/front/common/top.jsp" />
	<div class="row">
		<div class="col-md-9">
			<div class="data_list">
				<div class="data_list_title">
					<i class="glyphicon glyphicon-th-list"></i> 
					${title }&nbsp;(共&nbsp;${totalCount}&nbsp;篇)
				</div>
				<div class="datas">
				<c:choose>
					<c:when test="${empty blogList }">
					<div align="center" style="padding-top: 20px">该类别下没有文章。</div>
					</c:when>
					<c:otherwise>
					<ul>
					<c:forEach var="blog" items="${blogList}">
						<li class="list">
						    <span class="title">
						      <a title="${blog.title }" href="blog/articles/${blog.id}.shtml">
						          ${blog.title }
						      </a>
						    </span>
							<div>
								<div class="img">
									<a href="blog/articles/${blog.id}.shtml" title="${blog.title }"> 
									   <img src="images/cover/${blog.image }" alt="图片" />
									</a>
								</div>
								<div class="content">
									<span class="type">
									  <i class="glyphicon glyphicon-tag"></i>
									  <a href="blog.shtml?cat=${blog.blogType.typeId }"	style="color: #990">${blog.blogType.typeName }</a>
									</span> 
									<span class="time">
									  <i class="glyphicon glyphicon-time"></i>
									  <fmt:formatDate value="${blog.releaseDate }" type="date" pattern="yyyy-MM-dd HH:mm" />
									</span>
									<p class="summary">
										<span>摘要：${blog.summary }</span>
									</p>
								</div>
							</div>
							<div class="info">
								<span class="reading">
								 <i class="glyphicon glyphicon-eye-open"></i>
								   浏览(<a href="blog/articles/${blog.id}.shtml">${blog.reading}</a>)
								</span>
								<span class="review">
								 <i class="glyphicon glyphicon-comment"></i>
								   评论(<a href="blog/articles/${blog.id}.shtml#comment">${blog.commentsCount}</a>)
								</span> 
								<span class="readfull">
								  <a href="javascript:void(0)" onclick="window.location='blog/articles/${blog.id}.shtml'">阅读全文>></a>
								</span>
							</div>
							<div class="sepline"></div>
						</li>
					</c:forEach>
					</ul>
					</c:otherwise>
				</c:choose>
				</div>
			</div>
			<c:if test="${totalCount > 8}">
			<ul class="pagination">${pageCode }</ul>
			</c:if>
		</div>
		<div class="col-md-3">
			<jsp:include page="/WEB-INF/front/common/right.jsp" />
		</div>
		<jsp:include page="/WEB-INF/front/common/foot.jsp"/>
	</div>
</div>
</body>
</html>
