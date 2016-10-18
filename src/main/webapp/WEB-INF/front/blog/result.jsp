<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>搜索结果 - Promising的博客</title>
</head>
<body>
<div class="container">
	<jsp:include page="/WEB-INF/front/common/top.jsp"/>
	<div class="row">
		<div class="col-md-9">
			<div class="data_list">
				<div class="data_list_title">
					<img src="static/images/search_icon.png"/>
					搜索&nbsp;<font color="red">${q }</font>&nbsp;的结果 &nbsp;(总共搜索到&nbsp;${resultCount}&nbsp;条记录) </div>
					<div class="datas search">
						<ul>
							<c:choose>
								<c:when test="${empty blogList }">
									<div align="center" style="padding-top: 20px">未查询到结果，请换个关键字试试看！</div>
								</c:when>
								<c:otherwise>
									<%-- <c:forEach var="blog" items="${blogList }">
								  	  <li style="margin-bottom: 20px">
									  	<span class="title"><a href="blog/article/${blog.id}.html" target="_blank">${blog.title }</a></span>
									  	<span class="summary"> ${blog.summary }...</span>
									  	<span class="link"><a href="blog/article/${blog.id}.html">http://www.juncheng1994.cn/blog/article/${blog.id}.html</a>
									  	&nbsp;&nbsp;&nbsp;&nbsp;发布日期：<fmt:formatDate value="${blog.releaseDate }" type="date" pattern="yyyy-MM-dd HH:mm"/></span>
									  </li>
									</c:forEach> --%>
									<c:forEach var="blog" items="${blogList}">
									  	  <li style="margin-bottom: 20px">
											<span class="title"><a href="blog/article/${blog.id}.shtml" style="text-decoration:none">${blog.title }</a></span>
											<div class="img"><img src="images/cover/${blog.image }" alt="图片" width="140px"/></div>
											<p style="text-indent:2em;">
												<span class="summary">${blog.summary }...</span>
											</p>
										  	<p style="clear:both;padding-top:10px;">
										  		<span class="type"><a href="index.shtml?type=${blog.blogType.typeId }" style="color:#990">${blog.blogType.typeName }</a></span>
										  		<span class="time"><fmt:formatDate value="${blog.releaseDate }" type="date" pattern="yyyy-MM-dd HH:mm"/></span>
											  	<span class="reading">浏览(<a href="javascript:void(0)">${blog.reading}</a>)</span>
											  	<span class="review">评论(<a href="javascript:void(0)">${blog.review}</a>) </span>
										  		<span class="readfull">
										  			<a title="阅读全文" href="javascript:void(0)" onclick="window.location='blog/article/${blog.id}.shtml'">阅读全文</a>
										  		</span>
										  	</p>
										  <div class="sepline"></div>
										  </li>
									  </c:forEach>
								</c:otherwise>
							</c:choose>
						</ul>
					</div>
			   </div>
			<c:if test="${blogList.size() > 8}">
			   <div>
					  <ul class="pagination"> ${pageCode } </ul>
				 </div>
			</c:if>
		</div>
		<div class="col-md-3">
			<jsp:include page="/WEB-INF/front/common/right.jsp"/>
		</div>
		<div class="return_top"><a href="javascript:void(0)"></a></div>
	<%-- <jsp:include page="/front/common/foot.jsp"/> --%>
	</div>
</div>
</body>
</html>
