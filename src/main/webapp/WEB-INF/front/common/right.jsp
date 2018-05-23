<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false" trimDirectiveWhitespaces="true"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<aside>
	<div class="data_list">
		<div class="easyui-calendar" style="margin: 0 auto; width:240px; height:240px; border:none;"></div>
	</div>
	
	<div class="data_list">
		<div class="data_list_title">
			<i class="glyphicon glyphicon-search"></i> 文章搜索
		</div>
		<div class="search_form">
			<form class="from-inline" action="search.shtml" role="search" onsubmit="return checkData()">
				<div class="input-group">
					<input type="text" id="q" name="q" class="form-control" placeholder="请输入关键字">
					<span class="input-group-btn">
					 <button class="btn btn-info" type="submit">搜索</button>
					</span>
				</div>
			</form>
		</div>
	</div>
	
	<div class="data_list">
		<div class="data_list_title">
			<i class="glyphicon glyphicon-user"></i> 个人资料
		</div>
		<div class="user_image">
			<img src="images/avatar/${blogger.imageUrl }" alt="头像" />
		</div>
		<div class="nickName">${blogger.nickName }</div>
		<div class="userSign">(${blogger.signature })</div>
	</div>
	
	<div class="data_list">
		<div class="data_list_title">
			<i class="glyphicon glyphicon-list"></i>
			文章分类（${blogTypeList.size() }）
		</div>
		<div class="datas">
			<ul>
			<c:forEach var="blogType" items="${blogTypeList }" varStatus="status">
			<c:choose>
				<c:when test="${status.index < 8 }">
				<li>
                    <a href="blog.shtml?cat=${blogType.typeId}">${blogType.typeName }</a>
                    <font color="#337ab7">(${blogType.blogCount })</font>
				</li>
				</c:when>
				<c:otherwise>
				<li class="otherType" style="display: none">
				    <a href="blog.shtml?cat=${blogType.typeId}">${blogType.typeName }</a>
				    <font color="#337ab7">(${blogType.blogCount })</font>
				</li>
				</c:otherwise>
			</c:choose>
			</c:forEach>
				<li>
                    <a href="javascript:void(0)" onclick="showOtherType()" id="showOtherType">更多...</a>
				</li>
			</ul>
		</div>
	</div>
	
	<div class="data_list">
		<div class="data_list_title">
			<i class="glyphicon glyphicon-book"></i> 文章存档
		</div>
		<div class="datas">
			<ul>
			<c:forEach var="blog" items="${dateRankList }">
				<li>
			        <a href="blog.shtml?month=<fmt:formatDate value="${blog.releaseDate }" type="date" pattern="yyyyMM"/>">${blog.releaseDateStr }</a>
			        <font color="#337ab7">(${blog.count })</font>
			    </li>
			</c:forEach>
			</ul>
		</div>
	</div>
	
	<div class="data_list">
		<div class="data_list_title">
			<i class="glyphicon glyphicon-fire"></i> 热门文章
		</div>
		<div class="datas">
			<ul>
			<c:forEach var="blog" items="${readingRankList }" varStatus="index">
				<li class="ellipsis">
				    <span class="number bgcolor${index.count }">${index.count }</span>
				    <a href="blog/articles/${blog.id }.shtml" title="${blog.title }">${blog.title }</a>
				</li>
			</c:forEach>
			</ul>
		</div>
	</div>
	
	<div class="data_list">
		<div class="data_list_title">
			<i class="glyphicon glyphicon-sort-by-attributes-alt"></i> 热评文章
		</div>
		<div class="datas">
			<ul>
			<c:forEach var="blog" items="${reviewRankList }" varStatus="index">
				<li class="ellipsis">
				    <span class="number bgcolor${index.count }">${index.count }</span>
				    <a href="blog/articles/${blog.id }.shtml" title="${blog.title }">${blog.title }</a>
			    </li>
			</c:forEach>
			</ul>
		</div>
	</div>
	
	<div class="data_list">
		<div class="data_list_title">
			<i class="glyphicon glyphicon-link"></i> 常用网站收藏
		</div>
		<div class="datas">
			<ul>
			<c:forEach var="link" items="${linkList }">
				<li>
	    		     <a href="http://${link.linkUrl }" target="_blank">${link.linkName }</a>
		        </li>
			</c:forEach>
			</ul>
		</div>
	</div>
</aside>