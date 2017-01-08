<%@ page language="java" pageEncoding="utf-8" isELIgnored="false"
	contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta name="baidu-site-verification" content="eRo6fWUN2p" />
<title>Promising的博客</title>
</head>
<body>
	<div class="container">
		<jsp:include page="/WEB-INF/front/common/top.jsp" />
		<div class="row">
			<div class="col-md-9">
				<div class="data_list">
					<div class="data_list_title">
						<img src="static/images/list_icon.png" /> ${title }&nbsp;(共&nbsp;${totalCount}&nbsp;篇)
					</div>
					<div class="datas">
						<c:choose>
							<c:when test="${empty blogList }">
								<div align="center" style="padding-top: 20px">该类别下没有文章。</div>
							</c:when>
							<c:otherwise>
								<ul>
									<c:forEach var="blog" items="${blogList}">
										<li class="list"><span class="title"><a
												href="blog/articles/${blog.id}.shtml">${blog.title }</a></span>
											<div>
												<div class="img">
													<a href="blog/articles/${blog.id}.shtml"
														title="${blog.title }"> <img
														src="images/cover/${blog.image }" alt="图片" />
													</a>
												</div>
												<div class="content">
														<span class="type"><a
															href="blog.shtml?type=${blog.blogType.typeName }"
															style="color: #990">${blog.blogType.typeName }</a></span> <span
															class="time"><fmt:formatDate
																value="${blog.releaseDate }" type="date"
																pattern="yyyy-MM-dd HH:mm" /></span>
													<p class="summary">
														<span>摘要：${blog.summary }</span>
													</p>
												</div>
											</div>
												<div class="info">
													<span class="reading">浏览(<a
														href="javascript:void(0)">${blog.reading}</a>)
													</span> <span class="review">评论(<a
														href="blog/articles/${blog.id}.shtml#comment">${blog.review}</a>)
													</span> <span class="readfull"> <a title="阅读全文"
														href="javascript:void(0)"
														onclick="window.location='blog/articles/${blog.id}.shtml'">阅读全文</a>
													</span>
												</div>
											<div class="sepline"></div></li>
									</c:forEach>
								</ul>
							</c:otherwise>
						</c:choose>
					</div>
				</div>
				<c:if test="${totalCount > 8}">
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
