<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"
	trimDirectiveWhitespaces="true"%>
<!DOCTYPE html>
<html>
<head>
	<title>搜索'${q }'的结果 - ${blogger.nickName }的博客</title>
	<%@include file="/WEB-INF/front/head.jspf" %>
</head>
<body>
	<div class="container">
		<jsp:include page="/WEB-INF/front/common/top.jsp" />
		<div class="row">
			<div class="col-md-9">
				<div class="data_list">
					<div class="data_list_title">
						<img src="static/images/search_icon.png" /> 搜索&nbsp;<font
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
									<c:forEach var="blog" items="${blogList}">
										<li><span class="title"><a href="blog/articles/${blog.id}.shtml"
												style="text-decoration: none" target="_blank">${blog.title }</a></span>
											<div>
												<p>
													<span><%-- 分类：<a href="blog.shtml?type=${blog.typeName }"
														style="color: #990">${blog.typeName }</a> --%>	
													  	<font color="#929292">发布时间：</font>${blog.releaseDateStr}&nbsp;&nbsp;&nbsp;<font color="#929292">浏览</font> ${blog.reading } <font color="#929292"> 次</font></span> 
												</p>
												<p>
													<span>${blog.summary }</span>
												</p>
											<span class="link"><a href="blog/articles/${blog.id}.shtml" target="_blank">
											${basePath }blog/articles/${blog.id}.shtml</a></span>
											</div>
											<div class="sepline"></div></li>
									</c:forEach>
								</c:otherwise>
							</c:choose>
						</ul>
					</div>
				</div>
				<c:if test="${resultCount > 10}">
					<div>
						<ul class="pagination">${pageCode }
						</ul>
					</div>
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
