<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"
	trimDirectiveWhitespaces="true"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<title>搜索结果 - Promising的博客</title>
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
												<%-- <p>
													<span class="reading">浏览(<a
														href="javascript:void(0)">${blog.reading}</a>)
													</span> <span class="review">评论(<a
														href="blog/articles/${blog.id}.shtml#comment">${blog.review}</a>)
													</span> <span class="readfull"> <a title="阅读全文"
														href="javascript:void(0)"
														onclick="window.location='blog/articles/${blog.id}.shtml'">阅读全文</a>
													</span>
												</p> --%>
											<span class="link"><a href="blog/articles/${blog.id}.shtml" target="_blank">
											http://www.juncheng1994.cn/blog/articles/${blog.id}.shtml</a></span>
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
