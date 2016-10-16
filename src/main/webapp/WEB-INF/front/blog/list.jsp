<%@ page language="java" pageEncoding="utf-8" isELIgnored="false" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page import="org.apache.commons.lang3.StringEscapeUtils" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>Promising的博客</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<script>
		/* var _hmt = _hmt || [];
		(function() {
		  var hm = document.createElement("script");
		  hm.src = "//hm.baidu.com/hm.js?aa5c701f4f646931bf78b6f40b234ef5";
		  var s = document.getElementsByTagName("script")[0];
		  s.parentNode.insertBefore(hm, s);
		})(); */
	</script>
</head>
<body>
<div class="container">
	<jsp:include page="/WEB-INF/front/common/top.jsp"/>
	<div class="row">
		<div class="col-md-9">
				<div class="data_list">
					<div class="data_list_title">
					<img src="static/images/list_icon.png"/>
					${title }</div>
					<div class="datas">
						<ul>
						  <c:forEach var="blog" items="${blogList}">
						  	  <li style="margin-bottom: 20px">
								<span class="title"><a href="blog/article/${blog.id}.shtml">${blog.title }</a></span>
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
						</ul>
					</div>
			   </div>
				<div>
					  <ul class="pagination"> ${pageCode } </ul>
				 </div>
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