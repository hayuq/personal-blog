<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false"
	trimDirectiveWhitespaces="true"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="data_list">
	<div class="easyui-calendar" style="margin: 0; width:240px; height:240px; border:none;"></div>
</div>

<div class="data_list">
	<div class="data_list_title">
		<img src="static/images/search_icon.png" />文章搜索
	</div>
	<div>
		<form action="search.shtml" role="search" method="post" onsubmit="return checkData()">
			<div class="form-group">
				<input type="text" id="q" name="q" class="form-control"
					style="width:76%;float:left;margin-right:2px;" placeholder="请输入关键字">
			</div>
			<input type="submit" class="btn btn-info" value="搜索"/>
		</form>
	</div>
</div>

<div class="data_list">
	<div class="data_list_title">
		<img src="static/images/user_icon.png" /> 个人资料
	</div>
	<div class="user_image">
		<img src="images/avatar/${blogger.imageUrl }" alt="" width="100px"
			height="100px" />
	</div>
	<div class="nickName">${blogger.nickName }</div>
	<div class="userSign">(${blogger.signature })</div>
</div>

<div class="data_list">
	<div class="data_list_title">
		<img src="static/images/byType_icon.png" />
		文章分类（${blogTypeList.size() }）
	</div>
	<div class="datas">
		<ul>
			<c:forEach var="blogType" items="${blogTypeList }" varStatus="status">
				<c:choose>
					<c:when test="${status.index < 8 }">
						<li><span> <a
								href="blog.shtml?type=${blogType.typeName}">${blogType.typeName }</a><font
								color="#337ab7">(${blogType.blogCount })</font>
						</span></li>
					</c:when>
					<c:otherwise>

						<li class="otherType" style="display: none"><span> <a
								href="blog.shtml?type=${blogType.typeName}">${blogType.typeName }</a><font
								color="#337ab7">(${blogType.blogCount })</font>
						</span></li>
					</c:otherwise>
				</c:choose>
			</c:forEach>
			<li><span> <a href="javascript:void(0)"
					onclick="showOtherType()" id="showOtherType">更多...</a>
			</span></li>
		</ul>
	</div>
</div>

<div class="data_list">
	<div class="data_list_title">
		<img src="static/images/byDate_icon.png" /> 文章存档
	</div>
	<div class="datas">
		<ul>
			<c:forEach var="blog" items="${dateRankList }">
				<li><span> <a
						href="blog.shtml?month=${blog.releaseDateStr }">${blog.releaseDateStr }</a><font
						color="#337ab7">(${blog.count })</font>
				</span></li>
			</c:forEach>
		</ul>
	</div>
</div>

<div class="data_list">
	<div class="data_list_title">
		<img src="static/images/list_icon.png" /> 热门文章
	</div>
	<div class="datas">
		<ul>
			<c:forEach var="blog" items="${readingRankList }" varStatus="index">
				<li class="rank"><span class="number bgcolor${index.count }">${index.count }</span>
					<span> <a href="blog/articles/${blog.id }.shtml"
						title="${blog.title }">${blog.title }</a>
				</span></li>
			</c:forEach>
		</ul>
	</div>
</div>

<div class="data_list">
	<div class="data_list_title">
		<img src="static/images/comment_icon.png" /> 热评文章
	</div>
	<div class="datas">
		<ul>
			<c:forEach var="blog" items="${reviewRankList }" varStatus="index">
				<li class="rank"><span class="number bgcolor${index.count }">${index.count }</span>
					<span> <a href="blog/articles/${blog.id }.shtml"
						title="${blog.title }">${blog.title }</a>
				</span></li>
			</c:forEach>
		</ul>
	</div>
</div>

<div class="data_list">
	<div class="data_list_title">
		<!-- <img src="static/images/link_icon.png" /> --> 常用网站收藏
	</div>
	<div class="datas">
		<ul>
			<c:forEach var="link" items="${linkList }">
				<li><span><a href="http://${link.linkUrl }"
						target="_blank">${link.linkName }</a></span></li>
			</c:forEach>
		</ul>
	</div>
</div>
